package com.jsjg73.hibernate.multiplicity.manytomany.compositekey;

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

public class HibernateManyToManyCompsiteKeyIntegerationTest {
	private static SessionFactory sessionFactory;
	@BeforeClass
	public static void beforeAll() {
		sessionFactory =  HibernateUtil.getSessionFactory(Strategy.MANY_TO_MANY_COMPOSITEKEY);
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
		Student kim = new Student();
		session.persist(kim);
		Course math = new Course();
		session.persist(math);
		
		CourseRating rating = new CourseRating();
		rating.setId(new CourseRatingKey());
		rating.setStudent(kim);
		rating.setCourse(math);
		rating.setRating(99);
		
		session.persist(rating);
		
		CourseRating kim_math_rating = session.get(CourseRating.class, new CourseRatingKey(kim.getId(), math.getId()));
		assertThat(kim_math_rating).isNotNull();
		assertThat(kim_math_rating.getStudent().getId()).isEqualTo(kim.getId());
		assertThat(kim_math_rating.getCourse().getId()).isEqualTo(math.getId());
		tx.commit();
		session.clear();

		kim = session.get(Student.class, kim.getId());
		assertThat(kim.getRatings()).isNotNull();
		assertThat(kim.getRatings()).size().isEqualTo(1);
		
		math = session.get(Course.class, math.getId());
		assertThat(math).isNotNull();
		assertThat(math.getRatings()).size().isEqualTo(1);
	}
}
