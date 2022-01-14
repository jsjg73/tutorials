package com.jsjg73.hibernate.onetoone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jsjg73.hibernate.onetoone.foreignkeybased.Address;
import com.jsjg73.hibernate.onetoone.foreignkeybased.User;

public class HibernateOneToOneAnnotationForeignKeyBasedIntegrationTest {
	
	private static SessionFactory sessionFactory;
	
	private Session session;
	
	@BeforeClass
	public static void beforeTests() {
		sessionFactory = HibernateUtil.getSessionFactory(Strategy.FOREIGN_KEY);
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

    @AfterClass
    public static void afterTests() {
        sessionFactory.close();
    }
    
    @Test
    public void giventData_whenInsert_thenCreates1to1relationship() {
    	User user = new User();
    	user.setUserName("alice@gmail.com");
    	
    	Address address = new Address();
    	address.setStreet("FK street");
    	address.setCity("FK city");
    	
    	address.setUser(user);
    	user.setAddress(address);
    	
    	// Address 엔티티도 자동으로 저장된다. 왜냐하면 cascade 타입을 ALL로 설정했다.
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
