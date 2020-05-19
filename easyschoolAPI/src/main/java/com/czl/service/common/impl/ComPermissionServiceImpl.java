package com.czl.service.common.impl;

import com.alibaba.fastjson.JSONObject;
import com.czl.entity.ResultEntity;
import com.czl.entity.UserEntity;
import com.czl.service.common.ComPermissionService;
import com.czl.utils.RedisUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
//import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ComPermissionServiceImpl implements ComPermissionService {

	@Value("${spring.redis.database.access}")
	private String DB_ACCESS;

	@Value("${spring.redis.database.user}")
	private String DB_USER;

	@Autowired
	private Environment env;

	/**
	 * 取得指定userId的用户信息，含角色编号
	 * 
	 * @param userId
	 * @return 用户信息，含角色编号
	 */
	@Override
	public ResultEntity getUser(String userId) {

		ResultEntity result = new ResultEntity();

		try {

			String config = env.getProperty(DB_USER);

			int userDb = Integer.parseInt(config);

			RedisUtil redisUtil = new RedisUtil();

			String value = redisUtil.get(userId, userDb);

			JSONObject json = JSONObject.parseObject(value);

			UserEntity userInfo = JSONObject.toJavaObject(json, UserEntity.class);

			result.setStatus(ResultEntity.RESULT_CODE_OK);

			result.setEntityList(userInfo);

		} catch (Exception ex) {
			log.debug(ex.getMessage());
			ex.printStackTrace();
			result.setStatus(ResultEntity.RESULT_CODE_ERROR);
			result.setStatus(null);
		}

		return result;

	}

	/**
	 * 取得指定controller、action的权限数据
	 * 
	 * @param controllerId
	 * @param actionId
	 * @return 符合条件的权限列表
	 */
	@Override
	public ResultEntity getAccessFunction(String controllerId, String actionId) {
		// TODO Auto-generated method stub
		return null;
	}
}
