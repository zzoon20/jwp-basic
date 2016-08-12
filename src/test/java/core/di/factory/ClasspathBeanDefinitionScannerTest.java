package core.di.factory;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClasspathBeanDefinitionScannerTest {
	private Logger log = LoggerFactory.getLogger(ClasspathBeanDefinitionScannerTest.class);
	
	@Test
	public void scan() throws Exception {
		BeanFactory beanFactory = new BeanFactory();
		ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
		scanner.doScan();
		for (Class<?> clazz : beanFactory.getBeanClasses()) {
			log.debug("Bean : {}", clazz);
		}
	}
}
