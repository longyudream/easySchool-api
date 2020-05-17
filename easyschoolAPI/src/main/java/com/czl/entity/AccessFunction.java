package com.czl.entity;

/**
 * 权限容器
 * @author Justin
 *
 */
public class AccessFunction {

	private String jobId;
	
	private String roleId;
	
	private String functionId;
	
	private String clientId;
	
	private String frontPath;
	
	private String apiController;
	
	private String apiAction;

	private String needAuth;
	
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getFrontPath() {
		return frontPath;
	}

	public void setFrontPath(String frontPath) {
		this.frontPath = frontPath;
	}

	public String getApiController() {
		return apiController;
	}

	public void setApiController(String apiController) {
		this.apiController = apiController;
	}

	public String getApiAction() {
		return apiAction;
	}

	public void setApiAction(String apiAction) {
		this.apiAction = apiAction;
	}

	public String getNeedAuth() {
		return needAuth;
	}

	public void setNeedAuth(String needAuth) {
		this.needAuth = needAuth;
	}	
}
