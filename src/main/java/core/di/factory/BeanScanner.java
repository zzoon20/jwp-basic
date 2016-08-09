package core.di.factory;

import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;

public class BeanScanner {
	private static final Logger log = LoggerFactory.getLogger(BeanScanner.class);

	private Reflections reflections;

	public BeanScanner(Object... basePackage) {
		reflections = new Reflections(basePackage);
	}

	public Set<Class<?>> getBeans() {
		Set<Class<?>> preInitiatedBeans = reflections.getTypesAnnotatedWith(Controller.class);
		preInitiatedBeans.addAll(reflections.getTypesAnnotatedWith(Service.class));
		preInitiatedBeans.addAll(reflections.getTypesAnnotatedWith(Repository.class));
		return preInitiatedBeans;
	}
}
