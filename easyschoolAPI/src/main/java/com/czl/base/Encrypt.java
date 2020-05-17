package com.czl.base;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt
{

	public static final String ALGORITHM_MD5 = "MD5";
	public static final String ALGORITHM_SHA1 = "SHA1";
 
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/** 
		''' 数字校验函数
		''' 
		''' @return 
		''' 
	*/
	public static int CalcChecksum(String input)
	{
		//int result = 0;
		if (input.length() <= 0)
		{
			return -1;
		}
		return Damm(input);
	}

	/** 
		 ''' Damm算法(数字校验)
		 ''' 
		 ''' @return 
		 ''' 
	*/
	public static int Damm(String input)
	{
		int row = 0;
		int sig = 0;
		int[][] patternArray = new int[][]
		{
			{0, 3, 1, 7, 5, 9, 8, 6, 4, 2},
			{7, 0, 9, 2, 1, 5, 4, 8, 6, 3},
			{4, 2, 0, 6, 8, 7, 1, 3, 5, 9},
			{1, 7, 5, 0, 9, 8, 3, 4, 2, 6},
			{6, 1, 2, 3, 0, 4, 5, 9, 7, 8},
			{3, 6, 7, 4, 2, 0, 9, 5, 8, 1},
			{5, 8, 6, 9, 7, 2, 0, 1, 3, 4},
			{8, 9, 4, 5, 3, 6, 2, 0, 1, 7},
			{9, 4, 3, 8, 6, 1, 7, 2, 0, 5},
			{2, 5, 8, 1, 4, 3, 6, 7, 9, 0}
		};
		for (int i = 0; i <= input.length() - 1; i += 1)
		{
			sig = (int)(input.charAt(i)) - 48;
			if(sig > 9 || sig < 0)
				return -1;
			row = patternArray[row][sig];
		}
		return row;
	}
	
	

    private static SecretKeySpec secretKey;
    private static byte[] key;
 
    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    public static String AES128Encrypt(String plainText, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return "";
    }
 
    public static String AES128Decrypt(String cipherText, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return "";
	}
	
	/**
	 * encode string
	 *
	 * @param algorithm
	 * @param str
	 * @return String
	 */
	public static String HashEncode(String algorithm, String str) 
	{
		if (str == null) 
		{
			return null;
		}
		try 
		{
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		}
		catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
	}
 
	/**
	 * Takes the raw bytes from the digest and formats them correct.
	 *
	 * @param bytes
	 *            the raw bytes from the digest.
	 * @return the formatted bytes.
	 */
	private static String getFormattedText(byte[] bytes) 
	{
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) 
		{
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		
		return buf.toString();
	}
}
