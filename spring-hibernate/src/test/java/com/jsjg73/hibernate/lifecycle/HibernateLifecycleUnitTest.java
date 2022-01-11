package com.jsjg73.hibernate.lifecycle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static com.jsjg73.hibernate.lifecycle.HibernateLifecycleUtil.getManagedEntities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.Status;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.Rollback;

import com.springhibernate.example.conf.HibernateConf;
import com.springhibernate.example.operations.model.Person;

//insert into person values(1, 'The Tartar Steppe');
//insert into person values(2, 'Poem Strip');
//insert into person values(3, 'Restless Nights: Selected Stories of Dino Buzzati');

public class HibernateLifecycleUnitTest {
	SessionFactory sf;
	DirtyDataInspector inspector;
		
	private Session session;
	private Transaction tx;
	
	@Before
	public void beforeMethod() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(HibernateConf.class);
		sf = ac.getBean(SessionFactory.class);
		inspector = ac.getBean(DirtyDataInspector.class);
		
		session = sf.openSession();
		inspector.clearDirtyEntities();
		tx = session.beginTransaction();
	}
	
	@After
	public void afterMethod() {
		if(tx.isActive())
			tx.rollback();
		session.close();
		sf.close();
	}
	
	@Test
	public void whenEntityLoaded_thenEntityManaged() {
		assertThat(getManagedEntities(session)).isEmpty();
		
		List<Person> person = session.createQuery("FROM Person").getResultList();
		assertThat(getManagedEntities(session)).size().isEqualTo(3);
		
		assertThat(inspector.getDirtyEntities().size()).isZero();
		
		person.stream().filter(e->e.getId()==3).findFirst().get().setName("jsjg");
		tx.commit();

		assertThat(inspector.size()).isEqualTo(1);
		assertThat( ((Person)inspector.getDirtyEntities().get(0)).getId() ).isEqualTo(3);
		assertThat( ((Person)inspector.getDirtyEntities().get(0)).getName() ).isEqualTo("jsjg");
		
	}
	
	@Test
    public void whenDetached_thenNotTracked() {
		Person steppe = session.get(Person.class, 1L);
		assertThat(getManagedEntities(session)).size().isEqualTo(1);
		assertThat( getManagedEntities(session).get(0).getId() ).isEqualTo(steppe.getId());
		
		session.evict(steppe);
		assertThat(getManagedEntities(session)).size().isEqualTo(0);
		
		steppe.setName("stepepepepe");
		tx.commit();
		assertThat(inspector.getDirtyEntities()).isEmpty();
	}

	
    @Test
    public void whenReattached_thenTrackedAgain() {
    	Person steppe = session.get(Person.class, 1L);
    	   	
    	session.evict(steppe);
    	steppe.setName("stepepepepe");
    	tx.commit();

    	assertThat(inspector.getDirtyEntities()).isEmpty();
    	
    	tx = session.beginTransaction();
    	session.update(steppe);
    	tx.commit();
    	
    	assertThat(inspector.getDirtyEntities()).size().isEqualTo(1);
    	assertThat(((Person)inspector.getDirtyEntities().get(0)).getName()).isEqualTo("stepepepepe");
    	
    }
    
    @Test
    public void givenNewEntityWithID_whenReattached_thenManaged() {
    	Person person = new Person();
    	person.setId(1L);
    	person.setName("stttteeee");
    	
    	session.update(person);
    	assertThat(getManagedEntities(session)).size().isEqualTo(1);
    	tx.commit();
    	
    	assertThat(inspector.getDirtyEntities()).size().isEqualTo(1);
    }
    
    @Test
    public void givenTransientEntity_whenSave_thenManaged() {
    	Person person = new Person();
    	person.setName("jaesung");
    	
    	assertThat(getManagedEntities(session)).size().isEqualTo(0);
    	session.save(person);
    	assertThat(getManagedEntities(session)).size().isEqualTo(1);
    	assertThat(person.getId()).isNotNull();
    	
    	assertThat(person.getId()).isGreaterThan(3);
    	
    	//"SELECT COUNT(*) FROM Person"
    	
    	long count = ((BigInteger)session.createSQLQuery("SELECT COUNT(*) FROM Person").uniqueResult()).longValue();
    	assertThat(count).isEqualTo(4);
    	
    	tx.commit();
    	count = ((BigInteger)session.createSQLQuery("SELECT COUNT(*) FROM Person").uniqueResult()).longValue();
    	assertThat(count).isEqualTo(4);
    }
    
    @Test
    public void whenDelete_thenMarkDeleted() {
    	Person p = new Person();
    	p.setName("managed");
    	
    	long id = (long) session.save(p);
    	
    	tx.commit();
    	tx = session.beginTransaction();
    	session.delete(p);
    	assertThat(getManagedEntities(session).get(0).getStatus()).isEqualTo(Status.DELETED);
    	tx.commit();
    }
    
    @Test
    public void givenTransientEntity_whenManaged_thenDetectedbyHql() {
    	Person p = new Person();
    	p.setName("managed");
    	
    	long id = (long) session.save(p);
    	
    	String countQuery = "SELECT COUNT(*) FROM Person";
    	
    	
    	BigInteger cnt_native = (BigInteger) session.createSQLQuery(countQuery).uniqueResult();
    	assertThat(cnt_native).isEqualTo(3);

    	long cnt_hql =(long) session.createQuery(countQuery).uniqueResult();
    	assertThat(cnt_hql).isEqualTo(4);
    	
    	BigInteger cnt_native_after_hql =(BigInteger) session.createSQLQuery(countQuery).uniqueResult();
    	assertThat(cnt_native_after_hql).isEqualTo(4);
    	tx.rollback();
    	
    	BigInteger cnt_hql_then_rollback_then_native =(BigInteger) session.createSQLQuery(countQuery).uniqueResult();
    	assertThat(cnt_hql_then_rollback_then_native).isEqualTo(3);
    }
}
