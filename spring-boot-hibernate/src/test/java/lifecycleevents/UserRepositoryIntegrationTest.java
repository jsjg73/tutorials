package lifecycleevents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lifecycleevents.model.User;
import lifecycleevents.repository.UserRepository;

@SpringBootTest(classes = SpringBootLifecycleEventApplication.class)
public class UserRepositoryIntegrationTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeEach
	public void setup() {
		User user = new User();
		user.setFirstName("Jane");
		user.setLastName("Smith");
		user.setUserName("jsmith123");
		userRepository.save(user);
	}
	
	@AfterEach
	public void cleanup() {
		userRepository.deleteAll();
	}
	
	@Test
	public void wheNewUserProvided_userIsAdded() {
		//TODO : 테스트 코드 작성
		User user = new User();
		user.setFirstName("JS");
		user.setLastName("Kim");
		user.setUserName("teveloper73");
		userRepository.save(user);
		assertThat(user.getId()).isGreaterThan(0);
	}
	
	@Test
	public void whenUserNameProvided_userIsLoaded() {
		User user = userRepository.findByUserName("jsmith123");
		assertNotNull(user);
		assertEquals("jsmith123", user.getUserName());
	}
	
	@Test
	public void whenExistingUserProvided_userIsUpdated() {
		
		User user = userRepository.findByUserName("jsmith123");
		user.setFirstName("Joe");
		User ret_user = userRepository.save(user);
		
		assertNotEquals(user, ret_user);
		assertEquals("Joe", ret_user.getFirstName());
	}
	
	@Test
	public void whenExistingUserDeleted_userIsDeleted() {
		User user = userRepository.findByUserName("jsmith123");
		userRepository.delete(user);
		user = userRepository.findByUserName("jsmith123");
		assertNull(user);
	}
	
	@Test
	public void whenExistingUserLoaded_fullNameIsAvailable() {
		User user = userRepository.findByUserName("jsmith123");
		assertEquals("Jane Smith", user.getFullName());
	}
	
}
