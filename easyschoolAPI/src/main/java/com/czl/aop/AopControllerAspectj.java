package com.czl.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.czl.entity.ResultEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * @author czl
 * @since 2018-09-20
 */
@Component
@Aspect
@Slf4j
public class AopControllerAspectj {

	// "execution(* com..mapper..*(..))" //mapper层中的任意方法
	// "execution(* com..controller..*.*(..))"//controller层中的任意方法
	// "execution(* com..service..*(..))"//service层中的任一方法
	@Pointcut("execution(* com..controller..*(..)) || execution(* com..service..*(..)) || execution(* com..mapper..*(..))")
	public void cutPoint() {
	}

	@Around("cutPoint()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

		// 预处理
		Object resHandle = preHandle(joinPoint);

		if (resHandle == null) {

			// 预处理成功，可以继续处理
			// 取得处理结果
			Object result = joinPoint.proceed();

			// 后处理
			postHandle(joinPoint, result);

			return result;

		} else {

			// 预处理失败，返回错误信息。
			log.error("Error:" + ((ResultEntity) resHandle).toJson());
			return resHandle;
		}
	}

	@Before("cutPoint()")
	public void beforeCut(JoinPoint joinPoint) {

	}

	@AfterReturning("cutPoint()")
	public void afterCut(JoinPoint joinPoint) throws Throwable {
	}

	private Object postHandle(JoinPoint joinPoint, Object target) throws Throwable {

		try {
			return handle(joinPoint, "end", target);
		} catch (Exception ex) {
			log.debug(ex.getMessage());
		}

		return null;
	}

	private Object preHandle(JoinPoint joinPoint) {

		try {
			Object[] args = joinPoint.getArgs();

			Object target = null;

			if (args != null && args.length > 0) {
				target = args[0];
			}

			return handle(joinPoint, "start", target);
		} catch (Exception ex) {
			log.debug(ex.getMessage());
		}

		return null;
	}

	private Object handle(JoinPoint joinPoint, String comment, Object target) {

		Class<?> clazz = joinPoint.getTarget().getClass();
		String methodName = joinPoint.getSignature().getName(); // 获取方法名称

		String logmsg = "============ aop => " + clazz.getName() + ":" + methodName + "() " + comment
				+ ". ============";

		if (target != null) {

			if ((target instanceof String) == false) {
				target = JSONObject.toJSONString(target);
			}

			logmsg += "\nParameter = " + target;
		}

		log.debug(logmsg);

		return null;
	}
}
