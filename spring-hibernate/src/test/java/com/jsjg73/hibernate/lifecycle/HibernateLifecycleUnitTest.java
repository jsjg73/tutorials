package com.jsjg73.hibernate.lifecycle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hibernate.learningtest.model.Employee;
import com.springhibernate.example.conf.HibernateConf;
import com.springhibernate.example.operations.model.Person;

public class HibernateLifecycleUnitTest {
	static SessionFactory sf;
	static DirtyDataInspector inspector;
	@BeforeClass
	public static void setup() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(HibernateConf.class);
		sf = ac.getBean(SessionFactory.class);
		inspector = ac.getBean(DirtyDataInspector.class);
	}
	
	@AfterClass
	public static void teardown() {
		sf.close();
	}
	private Session session;
	private Transaction tx;
	
	@Before
	public void beforeMethod() {
		session = sf.openSession();
		tx = session.beginTransaction();
	}
	
	@After
	public void afterMethod() {
		if(tx.isActive())
			tx.rollback();
		session.close();
	}
	
	@Test
	public void whenEntityLoaded_thenEntityManaged() {
		assertThat(HibernateLifecycleUtil.getManagedEntities(session)).isEmpty();
		
		List<Person> person = session.createQuery("FROM Person").getResultList();
		assertThat(HibernateLifecycleUtil.getManagedEntities(session)).size().isEqualTo(3);
		
		assertThat(inspector.size()).isZero();
		
		person.stream().filter(e->e.getId()==3).findFirst().get().setName("jsjg");
		tx.commit();

		assertThat(inspector.size()).isEqualTo(1);
		assertThat( ((Person)inspector.getDirtyEntities().get(0)).getId() ).isEqualTo(3);
		assertThat( ((Person)inspector.getDirtyEntities().get(0)).getName() ).isEqualTo("jsjg");
		
	}
	
	@Test
    public void whenDetached_thenNotTracked() {
		fail("");
	}

	
    @Test
    public void whenReattached_thenTrackedAgain() {
    	fail("");
    }
    
    @Test
    public void givenNewEntityWithID_whenReattached_thenManaged() {
    	fail("");
    }
    
    @Test
    public void givenTransientEntity_whenSave_thenManaged() {
    	fail("");
    }
    
    @Test
    public void whenDelete_thenMarkDeleted() {
    	fail("");
    }
}
