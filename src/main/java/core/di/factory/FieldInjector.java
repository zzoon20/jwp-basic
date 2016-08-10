package core.di.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldInjector extends AbstractInjector {
	private static final Logger logger = LoggerFactory.getLogger(FieldInjector.class);
	
	private BeanFactory beanFactory;
	
	@Override
	public void inject(Class<?> clazz) {
		
	}

}
