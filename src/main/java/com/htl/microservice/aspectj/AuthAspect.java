package com.htl.microservice.aspectj;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.htl.microservice.controller.vo.Result;

@Aspect
@Component
@Order(1)
public class AuthAspect {

	@Resource
	private ValidateUtils validateUtils;

	@Around("execution(* com.htl.microservice.controller..*.*(..))")
	public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
		Signature s = pjp.getSignature();
		MethodSignature ms = (MethodSignature) s;
		Method m = ms.getMethod();

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		if (m.getDeclaredAnnotation(LoginRequired.class) != null) {

		} else {

		}

		Object result = null;

		try {
			if (result == null) {

				// 进行参数校验
				String validateR = null;
				if ((validateR = validateUtils.validate(pjp)) != null) {

					HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
							.getResponse();

					resp.setCharacterEncoding("utf-8");
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter writer = resp.getWriter();

					writer.write(JSONObject.toJSONString(new Result<Object>("1", validateR, null),
							SerializerFeature.WriteMapNullValue));
					writer.close();
					return null;
				}

				// 如果是已知异常,则直接向前端返回
				try {
					result = pjp.proceed();
				} catch (Exception e) {
					HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder
							.getRequestAttributes()).getResponse();
					resp.setCharacterEncoding("utf-8");
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter writer = resp.getWriter();
					
					if (e instanceof SystemException) {
						SystemException si = (SystemException) e;

						writer.write(JSONObject.toJSONString(new Result<Object>(si.getCode(), si.getMessage(), null),
								SerializerFeature.WriteMapNullValue));
						writer.close();
						return null;
					} else {
						e.printStackTrace();
						writer.write(JSONObject.toJSONString(new Result<Object>("-1", "unexpected error: " + e.toString() + ", please contact the administrator.", null),
								SerializerFeature.WriteMapNullValue));
						writer.close();
						return null;
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
			return e;
		}

		Map<String, String[]> map = request.getParameterMap();
		Set<Map.Entry<String, String[]>> keys = map.entrySet();
		Iterator<Map.Entry<String, String[]>> i = keys.iterator();
		String paramStr = "";
		while (i.hasNext()) {
			Map.Entry<String, String[]> it = i.next();
			paramStr = paramStr + it.getKey() + ":" + Arrays.toString(it.getValue()) + ";";
		}
		LoggerFactory.getLogger(m.getDeclaringClass()).info("request method: " + m.getName() + "; parameters: " + paramStr + "; response return: " + JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue));

		return result;
	}
}
