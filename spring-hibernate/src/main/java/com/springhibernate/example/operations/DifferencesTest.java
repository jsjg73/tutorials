package com.springhibernate.example.operations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springhibernate.example.conf.HibernateConf;
import com.springhibernate.example.operations.model.Person;

public class DifferencesTest {
	
	@Test(expected = PersistenceException.class)
	public void failToPersistDetachedObject() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(HibernateConf.class);
		SessionFactory factory = ac.getBean(SessionFactory.class);
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
		factory.close();
//		assertTrue("The Tartar Steppe".equals(person.getName()));
	}
}
