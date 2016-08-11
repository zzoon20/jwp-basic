package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConstructorInjector extends AbstractInjector {
	private static final Logger logger = LoggerFactory.getLogger(ConstructorInjector.class);
	
	public ConstructorInjector(BeanFactory beanFactory) {
		super(beanFactory);
	}

	@Override
	public void inject(Class<?> clazz) {
		Object bean = beanFactory.getBean(clazz);
        if (bean != null) {
        	return;
        }
        
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if (injectedConstructor != null) {
        	logger.debug("Constructor : {}", injectedConstructor);
            bean = instantiateConstructor(injectedConstructor);
            beanFactory.registerBean(clazz, bean);
        }
	}
	
	@Override
	public Set<?> getInjectedBeans(Class<?> clazz){
		return null;
	}
	
	@Override
	public Class<?> getBeanClass(Object bean) {
		return null;
	}
}
