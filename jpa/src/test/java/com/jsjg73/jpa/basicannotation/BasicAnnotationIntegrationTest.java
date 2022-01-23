package com.jsjg73.jpa.basicannotation;

import static org.junit.Assert.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BasicAnnotationIntegrationTest {
	
	static EntityManagerFactory emf;
	static EntityManager entityManager;
	static EntityTransaction tx;
	@BeforeClass
	public static void setup() {
		emf = Persistence.createEntityManagerFactory("java-jpa-scheduled-day");
		
	}
	
	@AfterClass
	public static void destroy() {
		
		if(emf != null)
			emf.close();
			
	}
	@Before
	public void before() {
		entityManager = emf.createEntityManager();
		tx = entityManager.getTransaction();
		tx.begin();
	}
	@After
	public void clear() {
		if(tx != null || tx.isActive())
			tx.rollback();
		if(entityManager.isOpen())
			entityManager.close();
	}
	@Test
	public void givenACourse_whenBothPresent_shoudPersist() {
		Course course = new Course();
		course.setNotNullByBasic("@Basic Annotation");
		course.setNotNullByDB("@Column Annotation");
		
		entityManager.persist(course);
		entityManager.flush();
	}
	
	@Test 
	public void givenACourse_whenBasicAnnotatedFieldAbsent_shoudFail() {
		Course course = new Course();
//		course.setNotNullByBasic("@Basic Annotation");
		course.setNotNullByDB("@Column Annotation");
		
		assertThrows("not-null property references a null or transient value", PersistenceException.class, ()->{
			entityManager.persist(course);
		});
		entityManager.detach(course);
		entityManager.flush();
	}
	
	@Test 
	public void givenACourse_whenColumnAnnotationedFieldAbsent_shoudFail() {
		Course course = new Course();
		course.setNotNullByBasic("@Basic Annotation");
//		course.setNotNullByDB("@Column Annotation");

		entityManager.persist(course);
		assertThrows("could not execute statement", PersistenceException.class, ()->{
			entityManager.flush();
		});
	}
}
