package com.springhibernate.example.operations;

import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springhibernate.example.conf.HibernateConf;
import com.springhibernate.example.operations.model.Person;

public class DifferencesTest {
	
	@Test
	public void initiateHibernate() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(HibernateConf.class);
		SessionFactory factory = ac.getBean(SessionFactory.class);
		Session session = factory.openSession();
		Person person = session.find(Person.class, 1l);
		
		assertTrue("The Tartar Steppe".equals(person.getName()));
	}
}
