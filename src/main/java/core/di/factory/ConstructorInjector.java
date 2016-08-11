package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

public class ConstructorInjector extends AbstractInjector {
	private static final Logger logger = LoggerFactory.getLogger(ConstructorInjector.class);
	
	public ConstructorInjector(BeanFactory beanFactory) {
		super(beanFactory);
	}

	@Override
	public Set<?> getInjectedBeans(Class<?> clazz){
		return Sets.newHashSet();
	}
	
	@Override
	public Class<?> getBeanClass(Object injectedBean) {
		return null;
	}

	@Override
	public void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {
	}
}
