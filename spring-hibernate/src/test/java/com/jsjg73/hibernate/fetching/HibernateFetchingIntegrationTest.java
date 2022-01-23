package com.jsjg73.hibernate.fetching;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jsjg73.hibernate.fetching.model.User;
import com.jsjg73.hibernate.fetching.model.UserDetail;
import com.jsjg73.hibernate.fetching.model.UserFamily;

public class HibernateFetchingIntegrationTest {
	static SessionFactory sessionFactory;
	Session session;
	Transaction tx;
	
	@BeforeClass
	public static void beforeAll(){
		sessionFactory = new Configuration().configure("fetching/fetching.cfg.xml").buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		User user = new User();
		UserDetail detail1 = new UserDetail("d1", user);
		UserDetail detail2 = new UserDetail("d2", user);
		UserDetail detail3 = new UserDetail("d3", user);
		
		UserFamily family1 = new UserFamily("f1", user);
		UserFamily family2 = new UserFamily("f2", user);
		UserFamily family3 = new UserFamily("f3", user);
		
		user.appendOrders(detail1, detail2, detail3);
		user.appendFamily(family1, family2,family3);
		
		session.persist(user);
		
		tx.commit();
		session.close();
	}
	
	@AfterClass
	public static void afterAll() {
		sessionFactory.close();
	}
	@Before
	public void beforeEach() {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
	}
	@After
	public void afterEach() {
		tx.rollback();
		session.close();
	}
	
	@Test
	public void context() {
	}
	
	@Test
	public void whenFetchingLazy_thenInitializedFalse() {
		List<User> users = session.createQuery("FROM User").list();
		User user = users.get(0);
		
		Set<UserDetail> details = user.getDetails();
		assertFalse(Hibernate.isInitialized(details));
		assertEquals(details.size(), 3);
		assertTrue(Hibernate.isInitialized(details));
		
	}
	@Test
	public void whenFetchingEager_thenInitializedTrue() {
		List<User> users = session.createQuery("FROM User").list();
		User user = users.get(0);
		
		Set<UserFamily> familys = user.getFamily();
		assertTrue(Hibernate.isInitialized(familys));
		assertEquals(familys.size(), 3);
	}
	
}
