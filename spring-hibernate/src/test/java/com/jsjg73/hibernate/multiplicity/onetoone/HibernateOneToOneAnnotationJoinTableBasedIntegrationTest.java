package com.jsjg73.hibernate.multiplicity.onetoone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jsjg73.hibernate.multiplicity.HibernateUtil;
import com.jsjg73.hibernate.multiplicity.Strategy;
import com.jsjg73.hibernate.multiplicity.onetoone.jointablebased.Employee;
import com.jsjg73.hibernate.multiplicity.onetoone.jointablebased.WorkStation;

public class HibernateOneToOneAnnotationJoinTableBasedIntegrationTest {
private static SessionFactory sessionFactory;
	
	private Session session;
	
	@BeforeClass
	public static void beforeTests() {
		sessionFactory = HibernateUtil.getSessionFactory(Strategy.JOIN_TABLE_BASED);
	}
	@Before
	public void setup() {
		session = sessionFactory.openSession();
		session.beginTransaction();
	}
	@After
    public void tearDown() {
        session.close();
    }

    @AfterClass
    public static void afterTests() {
        sessionFactory.close();
    }
    
    @Test
    public void givenData_whenInsert_thenCreates1to1relationship() {
    	Employee employee = new Employee();
        employee.setName("bob@gmail.com");

        WorkStation workStation = new WorkStation();
        workStation.setWorkStationNumber(626);
        workStation.setFloor("Sixth Floor");
        
        employee.setWorkStation(workStation);
        workStation.setEmployee(employee);

        session.persist(employee);
        session.getTransaction().commit();

        assert1to1InsertedData();
    }

    private void assert1to1InsertedData() {
    	@SuppressWarnings("unchecked")
        List<Employee> employeeList = session.createQuery("FROM Employee").list();
        assertNotNull(employeeList);
        assertEquals(1, employeeList.size());

        Employee employee = employeeList.get(0);
        assertEquals("bob@gmail.com", employee.getName());

        WorkStation workStation = employee.getWorkStation();

        assertNotNull(workStation);
        assertEquals((long) 626, (long) workStation.getWorkStationNumber());
        assertEquals("Sixth Floor", workStation.getFloor());
    }
}
