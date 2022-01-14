package com.jsjg73.hibernate.onetoone;

import java.util.Arrays;
import java.util.List;

public enum Strategy {
	FOREIGN_KEY(Arrays.asList(
			com.jsjg73.hibernate.onetoone.foreignkeybased.User.class,
			com.jsjg73.hibernate.onetoone.foreignkeybased.Address.class)), 
	SHARED_PRIMARY_KEY(Arrays.asList(
			com.jsjg73.hibernate.onetoone.sharedkeybased.User.class,
			com.jsjg73.hibernate.onetoone.sharedkeybased.Address.class
			)), 
	JOIN_TABLE_BASED(Arrays.asList(
			com.jsjg73.hibernate.onetoone.jointablebased.Employee.class,
			com.jsjg73.hibernate.onetoone.jointablebased.WorkStation.class
			));
	
	private List<Class<?>> entityClasses;
	
	private Strategy(List<Class<?>> entityClasses) {
		this.entityClasses = entityClasses;
	}
	
	public List<Class<?>> getEntityClasses() {
		return entityClasses;
	}

}
