package core.di.factory;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import core.annotation.Controller;

public class BeanFactory {
	private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

	private Set<Class<?>> preInstanticateBeans;

	private Map<Class<?>, Object> beans = Maps.newHashMap();

	private List<Injector> injectors = Lists.newArrayList();

	public BeanFactory(Set<Class<?>> preInstanticateBeans) {
		this.preInstanticateBeans = preInstanticateBeans;
	}

	public Set<Class<?>> getPreInstanticateBeans() {
		return this.preInstanticateBeans;
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType) {
		return (T) beans.get(requiredType);
	}

	public void initialize() {
		injectors.add(new ConstructorInjector(this));
		injectors.add(new FieldInjector());
		injectors.add(new SetterInjector());

		for (Class<?> clazz : preInstanticateBeans) {
			if (beans.get(clazz) == null) {
				inject(clazz);
			}
		}
	}

	public void inject(Class<?> clazz) {
		for (Injector injector : injectors) {
			injector.inject(clazz);
		}
	}

	public void registerBean(Class<?> clazz, Object bean) {
		logger.debug("class registered : {}", clazz.getName());
    	beans.put(clazz, bean);
    }

	public Map<Class<?>, Object> getControllers() {
		Map<Class<?>, Object> controllers = Maps.newHashMap();
		for (Class<?> clazz : preInstanticateBeans) {
			Annotation annotation = clazz.getAnnotation(Controller.class);
			if (annotation != null) {
				controllers.put(clazz, beans.get(clazz));
			}
		}
		return controllers;
	}
}
