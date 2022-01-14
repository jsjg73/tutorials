package com.jsjg73.hibernate.onetoone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jsjg73.hibernate.onetoone.sharedkeybased.Address;
import com.jsjg73.hibernate.onetoone.sharedkeybased.User;

public class HibernateOneToOneAnnotationSharedPrimaryKeyBasedIntegrationTest {
private static SessionFactory sessionFactory;
	
	private Session session;
	
	@BeforeClass
	public static void beforeTests() {
		sessionFactory = HibernateUtil.getSessionFactory(Strategy.SHARED_PRIMARY_KEY);
	}
	@Before
	public void setup() {
		session = sessionFactory.openSession();
		session.beginTransaction();
	}
	@After
    public void tearDown() {
        session.close();
    }
		
	@Test
	public void givenData_whenInsert_thenCreates1to1relationship() {
		User user = new User();
    	user.setUserName("alice@gmail.com");
    	
    	Address address = new Address();
    	address.setStreet("FK street");
    	address.setCity("FK city");
    	
    	address.setUser(user);
    	user.setAddress(address);
    	
    	// Address ��ƼƼ�� �ڵ����� ����ȴ�. �ֳ��ϸ� cascade Ÿ���� ALL�� �����ߴ�.
    	session.persist(user);
    	session.getTransaction().commit();
    	
    	assert1to1InsertedData();
	}
	private void assert1to1InsertedData() {
		List<User> userList = session.createQuery("FROM User").list();
		
		assertNotNull(userList);
		assertEquals(1, userList.size());
		
		User user = userList.get(0);
		assertEquals("alice@gmail.com", user.getUserName());
		
		Address address = user.getAddress();
		assertNotNull(address);
		assertEquals("FK street", address.getStreet());
		assertEquals("FK city", address.getCity());
    	
	}
}
