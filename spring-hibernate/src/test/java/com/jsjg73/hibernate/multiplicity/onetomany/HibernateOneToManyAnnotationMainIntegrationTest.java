package com.jsjg73.hibernate.multiplicity.onetomany;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jsjg73.hibernate.multiplicity.HibernateUtil;
import com.jsjg73.hibernate.multiplicity.Strategy;

public class HibernateOneToManyAnnotationMainIntegrationTest {
	private static SessionFactory factory;
	private Session session;
	
	@BeforeClass
	public static void boforeAll() {
		factory= HibernateUtil.getSessionFactory(Strategy.ONE_TO_MANY);
	}
	@AfterClass
	public static void afterAll() {
		factory.close();
	}
	@Before
	public void beforeEach() {
		session = factory.openSession();
		session.beginTransaction();
	}
	@After
	public void afterEach() {
		if(session.getTransaction().isActive())
			session.getTransaction().rollback();
		session.close();
	}
	
	@Test
	public void givenSession_checkIfDatabaseIsEmpty() {
		Cart cart = (Cart) session.get(Cart.class, 1L);
		assertNull(cart);
	}
	
	@Test
	public void givenSession_checkIfDatabaseIsPopulated_afterCommit() {
		Cart cart = new Cart();
		Items cookie = new Items();
		Items meat = new Items();
		cart.add(cookie);
		cart.add(meat);
		
		cookie.setCart(cart);
		meat.setCart(cart);
		
		session.persist(cart);
		session.getTransaction().commit();
		
		Cart storedCart = session.get(Cart.class, cart.getId());
		assertThat(storedCart).isNotNull();
		assertEquals(storedCart.size(), 2);
		
		Items storedCookie = session.get(Items.class, cookie.getId());
		Items storedMeat = session.get(Items.class, meat.getId());
		assertThat(storedCookie).isNotNull();
		assertThat(storedMeat).isNotNull();
	}
}
