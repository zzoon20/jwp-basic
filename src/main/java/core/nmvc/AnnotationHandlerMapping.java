package core.nmvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

import core.annotation.RequestMethod;

public class AnnotationHandlerMapping {
	private Object[] basePackage;
	
	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
	}
	
	public void initialize() {
		ControllerScanner ctrlScanner = new ControllerScanner();
		Map<Class<?>, Object> ctrlMap = ctrlScanner.getControllers(basePackage);
	}
	
	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}
}
