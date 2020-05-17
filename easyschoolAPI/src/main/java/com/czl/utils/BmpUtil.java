package com.czl.utils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import lombok.extern.slf4j.Slf4j;

import sun.misc.BASE64Encoder;

@Slf4j
public class BmpUtil {

	private final static BigDecimal PIXEL = new BigDecimal(100000);

	private final static float QUALITY = 0.5f;

	private final static BigDecimal PIXEL_BUFFER = new BigDecimal(1.10);

	/**
	 * 将指定的文件按JPG格式压缩，返回文件流 如果无法被压缩，返回空
	 */
	public static String imagePathToStream(String path) throws Exception {

		try {

			BufferedImage bufferedImage = ImageIO.read(new File(path));

			BigDecimal width = new BigDecimal(bufferedImage.getWidth());

			BigDecimal height = new BigDecimal(bufferedImage.getHeight());

			BigDecimal pixel = width.multiply(height);

			// 判读指定的图形文件是否需要缩放
			if ((pixel.multiply(PIXEL_BUFFER).compareTo(PIXEL)) <= 0) {
				return FileUtil.encodeBase64File(path);
			}

			BigDecimal tmp = pixel.divide(PIXEL);

			BigDecimal sqrt = new BigDecimal(Math.sqrt(tmp.doubleValue()));

			// 计算重画后的长宽
			int resizedWidth = width.divideToIntegralValue(sqrt).intValue();

			int resizedHeight = height.divideToIntegralValue(sqrt).intValue();

			byte[] imageByte = bufferedImageTobytes(bufferedImage, QUALITY, resizedWidth, resizedHeight);

			return new BASE64Encoder().encode(imageByte);

		} catch (Exception e) {
			log.debug("imagePathToStream错误：" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 自己设置压缩质量来把图片压缩成byte[]
	 * 
	 * @param image   压缩源图片
	 * @param quality 压缩质量，在0-1之间，
	 * @return 返回的字节数组
	 */
	private static byte[] bufferedImageTobytes(BufferedImage image, float quality, int resizedWidth,
			int resizedHeight) {

		// 如果图片空，返回空
		/*
		 * if (image == null) { return null; }
		 */
		// 得到指定Format图片的writer
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");// 得到迭代器
		ImageWriter writer = (ImageWriter) iter.next(); // 得到writer

		// 得到指定writer的输出参数设置(ImageWriteParam )
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // 设置可否压缩

		// quality > 0.0F && quality < 1.0F
		iwp.setCompressionQuality(quality); // 设置压缩质量参数

		iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED);

		ColorModel colorModel = image.getColorModel();

		// 指定压缩时使用的色彩模式
		iwp.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel,
				colorModel.createCompatibleSampleModel(resizedWidth, resizedHeight)));

		// 开始打包图片，写入byte[]
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 取得内存输出流

		IIOImage iIamge = new IIOImage(image, null, null);

		try {
			// 此处因为ImageWriter中用来接收write信息的output要求必须是ImageOutput
			// 通过ImageIo中的静态方法，得到byteArrayOutputStream的ImageOutput
			writer.setOutput(ImageIO.createImageOutputStream(byteArrayOutputStream));

			writer.write(null, iIamge, iwp);

		} catch (IOException e) {
			log.debug("bufferedImageTobytes错误：" + e.getMessage());
			e.printStackTrace();
		}

		return byteArrayOutputStream.toByteArray();
	}
}
