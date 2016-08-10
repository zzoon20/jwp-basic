package core.di.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetterInjector extends AbstractInjector {
	private static final Logger logger = LoggerFactory.getLogger(SetterInjector.class);
	
	private BeanFactory beanFactory;
	
	@Override
	public void inject(Class<?> clazz) {
		
	}

}
