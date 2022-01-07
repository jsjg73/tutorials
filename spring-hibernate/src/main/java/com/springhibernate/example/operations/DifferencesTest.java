package com.springhibernate.example.operations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransientObjectException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springhibernate.example.conf.HibernateConf;
import com.springhibernate.example.operations.model.Person;

public class DifferencesTest {
	ApplicationContext ac;
	SessionFactory factory;
	@Before
	public void setUp() {
		ac = new AnnotationConfigApplicationContext(HibernateConf.class);
		factory = ac.getBean(SessionFactory.class);
	}
	@After
	public void tearDown() {
		factory.close();	
	}
	
	@Test(expected = PersistenceException.class)
	public void failToPersistDetachedObject() {
		
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			Person person = new Person();
			person.setName("jaesung");
			
			assertFalse(session.contains(person));
			
			session.persist(person);
			
			assertTrue(session.contains(person));
			
			session.evict(person);
			
			assertFalse(session.contains(person));
			
			session.persist(person);
		}catch (HibernateException e) {
			if(tx!=null)tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
//		assertTrue("The Tartar Steppe".equals(person.getName()));
	}
	
	@Test
	public void saveMethod() {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Person person = new Person();
			person.setName("John");
			
			Long id1 = (Long) session.save(person);

			session.evict(person);
			
			Long id2 = (Long) session.save(person);
			
			assertFalse(id1.equals(id2));
			
		}catch(HibernateException e) {
			if(tx!= null)tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	@Test
	public void mergeMethod() {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Person person = new Person();

			person.setName("John");
			session.save(person);
			
			session.evict(person);
			person.setName("JJ");
			
			Person mergedPerson = (Person) session.merge(person);
			
			assertFalse(session.contains(person));
			assertTrue(session.contains(mergedPerson));
		}catch(HibernateException e) {
			if(tx!= null)tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	@Test
	public void updateMethod() {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Person person = new Person();
			
			person.setName("John");
			session.save(person);
			
			session.evict(person);
			person.setName("JJ");
			
			session.update(person);
			
			assertTrue(session.contains(person));
		}catch(HibernateException e) {
			if(tx!= null)tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	
	@Test(expected = TransientObjectException.class)
	public void failUpdateMethod() {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Person person = new Person();
			
			person.setName("John");
			session.update(person);
			
		}catch(HibernateException e) {
			if(tx!= null)tx.rollback();
			throw e;
		}finally {
			session.close();
		}
	}
}
