package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BeanFactory {
	private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

	private Set<Class<?>> preInstanticateBeans;

	private Map<Class<?>, Object> beans = Maps.newHashMap();

	public BeanFactory(Set<Class<?>> preInstanticateBeans) {
		this.preInstanticateBeans = preInstanticateBeans;
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType) {
		return (T) beans.get(requiredType);
	}

	public void initialize() {
		for (Class<?> clazz : preInstanticateBeans) {
			Object obj = getBean(clazz);
			if (obj == null) {
				beans.put(clazz, instantiateClass(clazz));
			}
		}
	}

	private Object instantiateClass(Class<?> clazz) {
		Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(clazz);
		if(constructor!=null){
			return instantiateConstructor(constructor);
		}
		return BeanUtils.instantiate(BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans));
	}

	private Object instantiateConstructor(Constructor<?> constructor) {
		Class<?>[] parameterTypes = constructor.getParameterTypes();
		List<Object> args = Lists.newArrayList();
		for (Class<?> clazz : parameterTypes) {
			Object bean = getBean(clazz);
			if (bean == null){
				Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
				logger.debug("concreteClass = {}", concreteClass.getName());
				bean = instantiateClass(concreteClass);
			}
			args.add(bean);
		}
		return BeanUtils.instantiateClass(constructor, args.toArray());
	}
}
