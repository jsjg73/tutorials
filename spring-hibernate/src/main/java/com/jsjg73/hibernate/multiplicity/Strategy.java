package com.jsjg73.hibernate.multiplicity;

import java.util.Arrays;
import java.util.List;

public enum Strategy {
	FOREIGN_KEY(Arrays.asList(
			com.jsjg73.hibernate.multiplicity.onetoone.foreignkeybased.User.class,
			com.jsjg73.hibernate.multiplicity.onetoone.foreignkeybased.Address.class)), 
	SHARED_PRIMARY_KEY(Arrays.asList(
			com.jsjg73.hibernate.multiplicity.onetoone.sharedkeybased.User.class,
			com.jsjg73.hibernate.multiplicity.onetoone.sharedkeybased.Address.class
			)), 
	JOIN_TABLE_BASED(Arrays.asList(
			com.jsjg73.hibernate.multiplicity.onetoone.jointablebased.Employee.class,
			com.jsjg73.hibernate.multiplicity.onetoone.jointablebased.WorkStation.class
			)),
	ONE_TO_MANY(Arrays.asList(
			com.jsjg73.hibernate.multiplicity.onetomany.Cart.class,
			com.jsjg73.hibernate.multiplicity.onetomany.Items.class
			));
	
	private List<Class<?>> entityClasses;
	
	private Strategy(List<Class<?>> entityClasses) {
		this.entityClasses = entityClasses;
	}
	
	public List<Class<?>> getEntityClasses() {
		return entityClasses;
	}

}
