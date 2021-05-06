package com.htl.microservice.aspectj;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * 
 * @author he.tianlong
 * @since 20180913
 *
 */
@Component
public class ValidateUtils {
	
	@Resource
	private Environment env;

	private static Logger logger = LoggerFactory.getLogger(ValidateUtils.class);
    private final static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final static ExecutableValidator validator = factory.getValidator().forExecutables();

	public String validate(ProceedingJoinPoint pjp) {
		try {
			Object[] parameters = pjp.getArgs();
			if (parameters != null && parameters.length > 0) {
				Integer index = 0;
				//校验javabean
				for (Object parameter : parameters) {
					if (parameter instanceof BindingResult) {
						StringBuilder sBuilder = new StringBuilder();
						BindingResult result = (BindingResult) parameter;
						if (result.hasErrors()) {
							List<ObjectError> list = result.getAllErrors();
							for (ObjectError error : list) {
								logger.info("error info:" + error.getDefaultMessage());
								sBuilder.append(error.getDefaultMessage());
								sBuilder.append("/");
							}
							if (sBuilder.length() > 0 && sBuilder.lastIndexOf("/") == sBuilder.length() - 1) {
								sBuilder.deleteCharAt(sBuilder.length() - 1);
							}
							if (sBuilder.length() > 0) {
								return new String(sBuilder.toString());
							}
						}
					}

					index++;
				}
				
				
		        //校验单个参数
		        Object target = pjp.getThis();
		        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		        Set<ConstraintViolation<Object>> validResult = validMethodParams(target, method, parameters);
		        if (validResult != null && !validResult.isEmpty()) {
		        	
		        	StringBuilder sb = new StringBuilder("参数错误:");
		        	Iterator<ConstraintViolation<Object>> itr = validResult.iterator();
		        	while(itr.hasNext()) {
		        		
			        	ConstraintViolation<?> violation = itr.next();
			        	Path propertyPath = violation.getPropertyPath();
			        	String currentPath = propertyPath.toString();
			        	String path = currentPath.substring(currentPath.indexOf(".") + 1, currentPath.length());
			        	Annotation annotation = violation.getConstraintDescriptor().getAnnotation();
			        	String annotationAlert = env.getProperty("valid.alert." + annotation.annotationType().getName());
			        	String r = annotationAlert.replaceAll("\\$\\(paramname\\)", path);
			        	while(findRegion(r) != null) {
			        		r = r.replaceAll("\\$\\(" + findRegion(r) + "\\)", getValueFromAnnotation(annotation, findRegion(r)));
			        	}
			        	sb.append(r);
			        	if(itr.hasNext()) {
			        		sb.append(",");
			        	}
		        	}
		        	return sb.toString();
		        }
		        return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String findRegion(String origionChars){
		String pattern = "[$]\\((.*?)\\)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(origionChars);
		if (m.find()) {
			return m.group(1);
		} else {
			return null;
		}
	}
	
	public static String getValueFromAnnotation(Annotation annotation, String key) {
		try {
	        InvocationHandler h = Proxy.getInvocationHandler(annotation);
	        Field hField = h.getClass().getDeclaredField("memberValues");
	        hField.setAccessible(true);
	        @SuppressWarnings("unchecked")
			Map<String, Object> memberValues = (Map<String, Object>) hField.get(h);
	        if(memberValues.get(key) == null) {
	        	return "";
	        }
	        return memberValues.get(key).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

    private static <T> Set<ConstraintViolation<T>> validMethodParams(T obj, Method method, Object[] params) {
        return validator.validateParameters(obj, method, params);
    }
}
