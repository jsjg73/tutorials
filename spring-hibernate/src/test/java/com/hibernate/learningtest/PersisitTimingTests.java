package com.hibernate.learningtest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import com.hibernate.learningtest.model.Employee;

public class PersisitTimingTests {
	SessionFactory factory;
	@Before
	public void setup() {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	@Test
	public void storedWhenFlushOrClose() {
		Session session = factory.openSession();
		Transaction tx = null;
		
		Employee empID1 = new Employee("jsjg73","jaesung",1000);
		Integer id = -1;
		try {
			tx = session.beginTransaction();
			id = (Integer) session.save(empID1);
			
			empID1.setSalary(999);
			tx.commit();
		}catch(HibernateException e) {
			if(tx!=null)tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		assertFalse(id==-1);
		
		session = factory.openSession();
		int salary = 0;
		try {
			tx = session.beginTransaction();
			salary = session.get(Employee.class, id).getSalary();
			tx.commit();
		}catch(HibernateException e) {
			if(tx!=null)tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		assertTrue(salary == 999);
		
		session = factory.openSession();
		try {
			tx = session.beginTransaction();
			Query<Employee> query = session.createQuery("DELETE FROM Employee WHERE id = :employee_id").setParameter("employee_id", id);
			query.executeUpdate();
			tx.commit();
		}catch(HibernateException e) {
			if(tx!=null)tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	
}
