package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;

public class ConstructorInjector extends AbstractInjector {
	private static final Logger logger = LoggerFactory.getLogger(ConstructorInjector.class);
	
	private BeanFactory beanFactory;
	
	public ConstructorInjector(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
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
	
	public Set<?> getInjectedBeans(Class<?> clazz){
		return null;
	}
	
	public Class<?> getBeanClass(Object bean) {
		return null;
	}
	
	private Object instantiateClass(Class<?> clazz) {
        Object bean = beanFactory.getBean(clazz);
        if (bean != null) {
            return bean;
        }
        
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if (injectedConstructor != null) {
        	logger.debug("Constructor : {}", injectedConstructor);
            bean = instantiateConstructor(injectedConstructor);
            beanFactory.registerBean(clazz, bean);
        }

        return bean;
    }

    private Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] pTypes = constructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz : pTypes) {
            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, beanFactory.getPreInstanticateBeans());
            if (!beanFactory.getPreInstanticateBeans().contains(concreteClazz)) {
                throw new IllegalStateException(clazz + "는 Bean이 아니다.");
            }
            
            Object bean = beanFactory.getBean(concreteClazz);
            if (bean == null) {
                bean = instantiateClass(concreteClazz);
            }
            args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

}
