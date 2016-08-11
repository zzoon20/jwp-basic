package core.di.factory;

import java.lang.reflect.Field;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldInjector extends AbstractInjector {
	private static final Logger logger = LoggerFactory.getLogger(FieldInjector.class);

	public FieldInjector(BeanFactory beanFactory) {
		super(beanFactory);
	}

	@Override
	public void inject(Class<?> clazz) {
		instantiateClass(clazz);
		Set<Field> injectedFiedls = BeanFactoryUtils.getInjectedFields(clazz);
		for (Field field : injectedFiedls) {
			Class<?> concreateClazz = BeanFactoryUtils.findConcreteClass(clazz, beanFactory.getPreInstanticateBeans());
			Object bean = beanFactory.getBean(concreateClazz);
			if(bean == null) {
				bean = instantiateClass(concreateClazz);
			}
				try {
					field.setAccessible(true);
					field.set(beanFactory.getBean(field.getDeclaringClass()), bean);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logger.error(e.getMessage());
				}
		}
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
