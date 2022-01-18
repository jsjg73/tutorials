package com.jsjg73.hibernate.multiplicity.manytomany.n_ary;

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

public class HibernateManyToManyNaryIntegrationTest {
	private static SessionFactory sessionFactory;
	@BeforeClass
	public static void beforeAll() {
		sessionFactory =  HibernateUtil.getSessionFactory(Strategy.MANY_TO_MANY_NARY);
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
	public void context() {
		Student s = new Student();
		Course c = new Course();
		Teacher t = new Teacher();
		t.setName("John");
		
		CourseRating rating = new CourseRating();
		rating.setId(new CourseRatingKey());
		rating.setCourse(c);
		rating.setStudent(s);
		rating.setTeacher(t);
		
		session.persist(rating);
		tx.commit();
		session.clear();

//		s = session.get(Student.class, s.getId());
		CourseRating _rating = session.get(CourseRating.class, new CourseRatingKey(s.getId(), c.getId(), t.getId()));
		assertThat(_rating).isNotSameAs(rating);
		assertThat(_rating).isNotNull();
		assertThat(_rating.getTeacher().getName()).isEqualTo(t.getName());
	}
}
