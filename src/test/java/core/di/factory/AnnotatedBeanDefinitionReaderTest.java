package core.di.factory;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Test;

public class AnnotatedBeanDefinitionReaderTest {

	@Test
	public void register_simple() {
		BeanFactory beanFactory = new BeanFactory();
		AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
		abdr.register(ExampleConfig.class);
		beanFactory.initialize();
		assertNotNull(beanFactory.getBean(DataSource.class));
	}

}
