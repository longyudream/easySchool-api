package com.czl.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.czl.base.constants.SystemConstants;
import com.czl.entity.AccessFunction;
import com.czl.entity.ResultEntity;
import com.czl.entity.UserEntity;
import com.czl.exception.ServiceException;
import com.czl.service.common.ComPermissionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthHandlerInterceptor implements HandlerInterceptor {
	
	@Value("${access.running-mode}")
    private String RUN_MODE;
	
	@Autowired
	private Environment env;
	
    @Autowired
    private ComPermissionService permissionService;

    /**
     * API执行前,通过该处理,验证用户是否有权限调用API
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(handler instanceof HandlerMethod) {
			
			 //只拦截API
			 HandlerMethod methodHandle = (HandlerMethod)handler;
			 String apiName = methodHandle.getMethod().getName();//getUserInfo
			 String controllerName = methodHandle.getBeanType().getSimpleName();//USR14Controller
			 String accountId = request.getHeader(SystemConstants.REQUEST_HEAD_ACCOUNT);//accountId
			
			 String runMode = env.getProperty(RUN_MODE);
			 
			 if(SystemConstants.RUN_MODE_NEED_ACCESS.equals(runMode)) {
				
				 boolean isAccess = isAccess(accountId, controllerName, apiName);
				 
				 if(!isAccess) {
					 String logmsg = "============ auth => " + controllerName + ":" + apiName + "访问被拒绝，用户编号：" + accountId + "============";
					 log.error(logmsg);
					 ResultEntity result = new ResultEntity("1111", "access deny");
			         throw new ServiceException(result);
				 }
			 }
			
			 return true;
		}
		
		return true;
	}
	
	/**
	 * 判断当前用户是有权限访问指定的API
	 */
	public boolean isAccess(String userId, String apiController, String apiAction) {
		
		ResultEntity resultAccess = permissionService.getAccessFunction(apiController, apiAction);
		
		if(ResultEntity.RESULT_CODE_OK.equals(resultAccess.getResCode()) == false) {
			log.error("验证失败,非法调用, 被调用的API未被配置, Controller：" + apiController + ";API:" + apiAction);
			return false;
		}
		
		@SuppressWarnings("unchecked")
		List<AccessFunction> tmpList = (List<AccessFunction>)resultAccess.getResInfo();
		
		if(tmpList.size() == 0) {
			
			log.error("验证失败,非法调用, 被调用的API未被配置, Controller：" + apiController + ";API:" + apiAction);
			
			log.debug("验证失败,非法调用, 被调用的API未被配置, Controller：" + apiController + ";API:" + apiAction);
			
			return false;
		} 
		
		AccessFunction functionItem = tmpList.get(0);
		
		if(SystemConstants.FUNCTION_AUTH_FLAG.endsWith(functionItem.getNeedAuth()) == false) {
			
			log.debug("验证成功,被调用的API不需要授权, Controller：" + apiController + ";API:" + apiAction);
			
			return true;
		}
		
		ResultEntity resultUser  = permissionService.getUser(userId);
		
		if(ResultEntity.RESULT_CODE_OK.equals(resultUser.getResCode()) == false) {
		
			log.error("验证失败, 非法调用, 用户未登录,用户编号：" + userId);
			 
			return false;
		}
		
		UserEntity user = (UserEntity)(resultUser.getResInfo());
		
		AccessFunction item = findAccessFunction(tmpList, user.getRoleId());
		
		return (item != null);
	}
	
	/**
	 * 查找角色是否被授权
	 * @param apiList
	 * @param roleId
	 * @return
	 */
	private AccessFunction findAccessFunction(List<AccessFunction> apiList, String roleId) {
		
		for(int i = 0; i < apiList.size(); i++) {
			
			AccessFunction item = apiList.get(i);
			
			if(item.getRoleId().equals(roleId)) {
				return item;
			}
		}
		
		return null;
	}
}
