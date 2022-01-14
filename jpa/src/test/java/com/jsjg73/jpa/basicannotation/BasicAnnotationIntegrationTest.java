package com.jsjg73.jpa.basicannotation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BasicAnnotationIntegrationTest {
	
	static EntityManagerFactory emf;
	static EntityManager entityManager;
	static EntityTransaction tx;
	@BeforeClass
	public static void setup() {
		emf = Persistence.createEntityManagerFactory("java-jpa-scheduled-day");
		entityManager = emf.createEntityManager();
	}
	
	@AfterClass
	public static void destroy() {
		if(entityManager != null)
			entityManager.close();
		
		if(emf != null)
			emf.close();
			
	}
	@Before
	public void before() {
		tx = entityManager.getTransaction();
		tx.begin();
	}
	@After
	public void clear() {
		if(tx != null || tx.isActive())
			tx.rollback();
	}
	@Test
	public void givenACourse_whenCourseNamePresent_shoudPersist() {
		Course course = new Course();
		course.setName("Computers");
		
		entityManager.persist(course);
		entityManager.flush();
		entityManager.clear();
	}
	
	@Test(expected = PersistenceException.class)
	public void givenACourse_whenCourseNameAbsent_shoudFail() {
		Course course = new Course();
		
		entityManager.persist(course);
		entityManager.flush();
		entityManager.clear();
	}
	
}
