package com.czl.config.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Aspect
@Component
@Order(-1)
@Log4j2
public class DataSourceTypeHandle {

	@Around("execution(* com.czl.service..impl.*.*(..))")
	@Order(1)
	public Object handleType(ProceedingJoinPoint jp) throws Throwable {

		// 同时支持类和方法上的注解，以方法上为主
		DataSourceType typeInClass = jp.getTarget().getClass().getAnnotation(DataSourceType.class);
		MethodSignature methodSignature = (MethodSignature) jp.getSignature();
		DataSourceType typeInMethod = methodSignature.getMethod().getDeclaredAnnotation(DataSourceType.class);

		String name;

		if (typeInMethod != null) {
			name = typeInMethod.name();
		} else if (typeInClass != null) {
			name = typeInClass.name();
		} else {
			return jp.proceed();
		}

		return dealWithTargetDS(jp, name);
	}

	private Object dealWithTargetDS(ProceedingJoinPoint jp, String name) {
		if (DataSourceTypeConstant.ALL_DATASOURCE.contains(name)) {
			log.info("开始切换数据源，方法{}，数据源{}", jp.getSignature().getName(), name);
			DynamicDataSourceHolder.set(name);
		} else {
			log.error("方法{}，切换非法数据源{}", jp.getSignature().getName(), name);
		}

		Object proceed;
		try {
			proceed = jp.proceed();
		} catch (Throwable throwable) {
			throw new ApiException(throwable.getMessage(), throwable);
		} finally {
			DynamicDataSourceHolder.clear();
		}

		return proceed;
	}
}
