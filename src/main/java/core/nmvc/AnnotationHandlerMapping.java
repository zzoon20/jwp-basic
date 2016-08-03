package core.nmvc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;

public class AnnotationHandlerMapping {
	private Object[] basePackage;
	
	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

	private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);


	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
	}
	
	public void initialize() {
		ControllerScanner ctrlScanner = new ControllerScanner();
		Map<Class<?>, Object> ctrlMap = ctrlScanner.getControllers(basePackage);
		
		Set<Class<?>> ctrlSet = ctrlMap.keySet();
		
		for (Class<?> clazz : ctrlSet) {
			Set<Method> methodSet = ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class));
			for (Method method : methodSet) {
				logger.debug(method.getName());
				try {
					handlerExecutions.put(createHandlerKey(method.getAnnotation(RequestMapping.class)), new HandlerExecution(clazz.newInstance(), method));
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private HandlerKey createHandlerKey(RequestMapping rm) {
		return new HandlerKey(rm.value(), rm.method());
	}

	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}
}
