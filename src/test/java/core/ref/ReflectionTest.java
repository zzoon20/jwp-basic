package core.ref;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;

public class ReflectionTest {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
	
	@Test
	public void showClass() {
		Class<Question> clazz = Question.class;
		logger.debug(clazz.getName());
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			logger.debug("field : {}", field.getName());
		}
		
		Constructor[] constructors = clazz.getDeclaredConstructors();
		for (Constructor constructor : constructors) {
			logger.debug("constructor : {}", constructor.getName());
		}
		
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			logger.debug("method : {}", method.getName());
		}
	}
}
