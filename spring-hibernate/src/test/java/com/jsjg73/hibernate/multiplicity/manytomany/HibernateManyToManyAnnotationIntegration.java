package com.jsjg73.hibernate.multiplicity.manytomany;

import static org.assertj.core.api.Assertions.assertThat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jsjg73.hibernate.multiplicity.HibernateUtil;
import com.jsjg73.hibernate.multiplicity.Strategy;

public class HibernateManyToManyAnnotationIntegration {
	private static SessionFactory sessionFactory;
	@BeforeClass
	public static void beforeAll() {
		sessionFactory =  HibernateUtil.getSessionFactory(Strategy.MANY_TO_MANY);
	}
	@AfterClass
	public static void afterAll() {
		sessionFactory.close();
	}
	private Session session;
	private Transaction tx;
	@Before
	public void beforeEach() {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
	}
	@After
	public void afterEach() {
		if(tx!=null && tx.isActive())
			tx.rollback();
		session.close();
	}
	
	@Test
	public void contextStarted() {
		
	}
	
	@Test
	public void whenLikedCoursesPersisted_thenCorrect() {
		Student kim = new Student();
		Student lee = new Student();
		Student choi = new Student();
		
		Course math = new Course();
		math.setName("math");
		Course eng = new Course();
		eng.setName("eng");
		Course cs = new Course();
		
		kim.like(math, eng);
		
//		math.addStudents(kim, lee);
		lee.like(eng, cs);
		choi.like(cs,math);
		session.persist(kim);
		session.persist(lee);
		session.persist(choi);
		session.persist(math);
		tx.commit();
		session.close();
		assertThat(kim.getId()).isEqualTo(1L);
		
		session = sessionFactory.openSession();

		Student _kim = session.get(Student.class, kim.getId());
		assertThat(_kim).isNotNull();
		assertThat(_kim.getLikedCourse().size()).isEqualTo(2);
		
		
		Course _math = session.get(Course.class, math.getId());
		assertThat(_math).isNotNull();
		assertThat(_math.getLikes()).size().isEqualTo(2);
		
		
	}
}
