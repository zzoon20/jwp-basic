package core.ref;

import java.lang.reflect.Field;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentTest {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
	
	@Test
	public void test() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		Student student = new Student();

		Class<Student> clazz = Student.class;
		Field name = clazz.getDeclaredField("name");
		name.setAccessible(true);
		name.set(student, "jun");
		
		logger.debug("student's name = {}", student.getName());
	}

}
