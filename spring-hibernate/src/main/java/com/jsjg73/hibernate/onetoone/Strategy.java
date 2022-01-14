package com.jsjg73.hibernate.onetoone;

import java.util.Arrays;
import java.util.List;

public enum Strategy {
	FOREIGN_KEY(Arrays.asList(
			com.jsjg73.hibernate.onetoone.foreignkeybased.User.class,
			com.jsjg73.hibernate.onetoone.foreignkeybased.Address.class));
	
	private List<Class<?>> entityClasses;
	
	private Strategy(List<Class<?>> entityClasses) {
		this.entityClasses = entityClasses;
	}
	
	public List<Class<?>> getEntityClasses() {
		return entityClasses;
	}

}
