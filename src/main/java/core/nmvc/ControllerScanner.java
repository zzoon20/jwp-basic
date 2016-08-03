package core.nmvc;

import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import core.annotation.Controller;

public class ControllerScanner {
	private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);
	
	private Reflections reflections;
	
	public Map<Class<?>, Object> getControllers(Object[] basePackage){
		reflections = new Reflections(basePackage);
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
logger.debug("annotated count : {}", annotated.size());
		return instantiateControllers(annotated);
	}

	private Map<Class<?>, Object> instantiateControllers(Set<Class<?>> annotated) {
		Map<Class<?>, Object> ctrlMap = Maps.newHashMap();
		for (Class<?> clazz : annotated) {
			try {
				ctrlMap.put(clazz, clazz.newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		logger.debug("ctrlMap = {}", ctrlMap.toString());
		return ctrlMap;
	}
}
