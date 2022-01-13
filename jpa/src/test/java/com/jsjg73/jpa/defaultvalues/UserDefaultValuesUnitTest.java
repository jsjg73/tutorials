package com.jsjg73.jpa.defaultvalues;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class UserDefaultValuesUnitTest {
	
	private static UserRepository userRepository = null;
	
	@BeforeClass
	public static void once() {
		userRepository=new UserRepository();
	}
	
	@AfterClass
	public static void destroy() {
		userRepository.clean();
	}
	
	@Test
	public void saveUser_shouldSaveWithDefaultFieldValues() {
		User user = new User("John Snow", 25, false);
		userRepository.save(user, 1L);
		
		user = userRepository.find(1L);
		assertEquals(user.getFirstName(), "John Snow");
		assertEquals((int)user.getAge(), 25);
		assertFalse(user.getLocked());
	}
	
	@Test
	@Ignore // entity 클래스에 @DynamicInsert가 없으면 성공함
	public void saveUser_shouldSaveWithNullName() {
		
		User user = new User();
		userRepository.save(user,2L);
		
		user = userRepository.find(2L);
		assertNull(user.getFirstName());
		assertNull(user.getAge());
		assertNull(user.getLocked());
	}
	
	@Test
	public void saveUser_shouldSaveWithDefaultSqlValues() {
		User user = new User();
		userRepository.save(user,3L);
		
		user = userRepository.find(3L);
		assertEquals(user.getFirstName(), "John Snow");
		assertEquals((int)user.getAge(), 25);
		assertFalse(user.getLocked());
	}
}
