package com.jsjg73.hibernate.fetching;


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
				
	}
	
	@AfterClass
	public static void afterAll() {
		sessionFactory.close();
	}
	
	@Before
	public void beforeEach() {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		session.createQuery("DELETE User").executeUpdate();
		session.createQuery("DELETE UserDetail").executeUpdate();
		session.createQuery("DELETE UserFamily").executeUpdate();
		User user = new User();
		UserDetail detail1 = new UserDetail();
		UserDetail detail2 = new UserDetail();
		UserDetail detail3 = new UserDetail();
		
		UserFamily family1 = new UserFamily();
		UserFamily family2 = new UserFamily();
		UserFamily family3 = new UserFamily();
		user.appendOrders(detail1, detail2, detail3);
		user.appendFamily(family1, family2,family3);
		
		session.persist(user);
		session.flush();
		session.clear();
	}
	@After
	public void afterEach() {
		tx.rollback();
		session.close();
	}
	
	@Test
	public void context() {
		
	}
}
