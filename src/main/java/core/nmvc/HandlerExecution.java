package core.nmvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.ModelAndView;

public class HandlerExecution {
	private Object declaredObject;
	private Method method;
	private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

	public HandlerExecution(Object declaredObject, Method method){
		this.declaredObject = declaredObject;
		this.method = method;
		
		logger.debug("declaredObj = {}", declaredObject);
		logger.debug("method = {}", method.getName());
	}
	
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return (ModelAndView) method.invoke(declaredObject, request, response);
	}
}
