package com.czl.service.common;

import com.czl.entity.ResultEntity;

public interface ComPermissionService {

	/**
	 * 取得指定角色、area、controller、action的权限数据
	 * @param areaId
	 * @param controllerId
	 * @param actionId
	 * @return 符合条件的权限列表
	 */
	ResultEntity getAccessFunction(String controllerId, String actionId);
	
	/**
	 * 取得指定userId的用户信息，含角色编号
	 * @param userId
	 * @return 用户信息，含角色编号
	 */
	ResultEntity getUser(String userId);
}
