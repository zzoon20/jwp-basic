package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;

public abstract class AbstractInjector implements Injector {
	private static final Logger logger = LoggerFactory.getLogger(AbstractInjector.class);
	protected BeanFactory beanFactory;
	
	public AbstractInjector(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	public void inject(Class<?> clazz) {
		instantiateClass(clazz);
		Set<?> injectedBeans = getInjectedBeans(clazz);
		for (Object injectedBean : injectedBeans) {
			Class<?> beanClass = getBeanClass(injectedBean);
			inject(injectedBean, instantiateClass(beanClass), beanFactory);
		}
	}
	
	abstract public Set<?> getInjectedBeans(Class<?> clazz);
	
	abstract public Class<?> getBeanClass(Object injectedBean);

	abstract public void inject(Object injectedBean, Object bean, BeanFactory beanFactory);

	private Object instantiateClass(Class<?> clazz) {
		Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(clazz, beanFactory.getPreInstanticateBeans());
        Object bean = beanFactory.getBean(concreteClass);
        if (bean != null) {
            return bean;
        }
        
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(concreteClass);
        if (injectedConstructor == null) {
            bean = BeanUtils.instantiate(concreteClass);
            beanFactory.registerBean(concreteClass, bean);
            return bean;
        }
        
        logger.debug("Constructor : {}", injectedConstructor);
        bean = instantiateConstructor(injectedConstructor);
        beanFactory.registerBean(concreteClass, bean);
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
