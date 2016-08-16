package core.di.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Bean;

public class AnnotatedBeanDefinitionReader {
	private static final Logger log = LoggerFactory.getLogger(AnnotatedBeanDefinitionReader.class);

	private BeanFactory beanFactory;

	public AnnotatedBeanDefinitionReader(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public void register(Class<?>... clazz) {
		for (Class<?> configClazz : clazz) {
			registerBean(configClazz);
		}
	}

	private void registerBean(Class<?> annotatedClass) {
		beanFactory.registerBeanDefinition(annotatedClass, new BeanDefinition(annotatedClass));
		Set<Method> beanMethods = BeanFactoryUtils.getBeanMethods(annotatedClass, Bean.class);
		for (Method beanMethod : beanMethods) {
			log.debug("@Bean method : {}", beanMethod);
			AnnotatedBeanDefinition abd = new AnnotatedBeanDefinition(beanMethod.getReturnType(), beanMethod);
			beanFactory.registerBeanDefinition(beanMethod.getReturnType(), abd);
		}
	}

}
