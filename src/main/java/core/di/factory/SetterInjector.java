package core.di.factory;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetterInjector extends AbstractInjector {
	private static final Logger logger = LoggerFactory.getLogger(SetterInjector.class);

	public SetterInjector(BeanFactory beanFactory) {
		super(beanFactory);
	}

	@Override
	public void inject(Class<?> clazz) {
		instantiateClass(clazz);
	}

	@Override
	public Set<?> getInjectedBeans(Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getBeanClass(Object bean) {
		// TODO Auto-generated method stub
		return null;
	}

}
