package com.hibernate.learningtest;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hibernate.learningtest.model.Employee;

public class ManageEmployeeTests {
	static SessionFactory factory;
	@BeforeClass
	public static void setUp() {
		factory = new Configuration().configure().buildSessionFactory();
	}
	
	@Test
	public void operations() {
		deleteAll();
		Integer empID1 = addEmployee("Zara", "Ali", 1000);
		Integer empID2 = addEmployee("Daisy", "Das", 5000);
		Integer empID3 = addEmployee("John", "Paul", 10000);
		
		List<Employee> employees = listEmployees();
		assertTrue(employees.size()==3);
		
		updateEmployee(empID1, 0);
		
	}
	
	private int addEmployee(String fname, String lname, int salary) {
		Session session = factory.openSession();
	      Transaction tx = null;
	      Integer employeeID = null;
	      
	      try {
	         tx = session.beginTransaction();
	         Employee employee = new Employee(fname, lname, salary);
	         employeeID = (Integer) session.save(employee); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      
	      return employeeID;
	}
	
	private List<Employee> listEmployees() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Employee> employees = null;
		try {
			tx = session.beginTransaction();
			employees = session.createQuery("FROM Employee").list();
			
			tx.commit();
		}catch(HibernateException e) {
			if(tx!=null)tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return employees; 
	}
	public void deleteAll(){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         session.createQuery("delete from Employee").executeUpdate();
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	   }
	
	public void updateEmployee(Integer EmployeeID, int salary ){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      
	      try {
	         tx = session.beginTransaction();
	         Employee employee = (Employee)session.get(Employee.class, EmployeeID); 
	         employee.setSalary( salary );
			 session.update(employee); 
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	   }
}
