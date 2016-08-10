package core.di.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConstructorInjector extends AbstractInjector {
	private static final Logger logger = LoggerFactory.getLogger(ConstructorInjector.class);
	
	private BeanFactory beanFactory;
	
	@Override
	public void inject(Class<?> clazz) {
		
	}

}
