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
			)),
	MANY_TO_MANY(Arrays.asList(
			com.jsjg73.hibernate.multiplicity.manytomany.Student.class,
			com.jsjg73.hibernate.multiplicity.manytomany.Course.class
			)), 
	MANY_TO_MANY_COMPOSITEKEY(Arrays.asList(
			com.jsjg73.hibernate.multiplicity.manytomany.compositekey.Student.class,
			com.jsjg73.hibernate.multiplicity.manytomany.compositekey.Course.class,
			com.jsjg73.hibernate.multiplicity.manytomany.compositekey.CourseRating.class,
			com.jsjg73.hibernate.multiplicity.manytomany.compositekey.CourseRatingKey.class
			)), 
	MANY_TO_MANY_NARY(Arrays.asList(
			com.jsjg73.hibernate.multiplicity.manytomany.n_ary.Student.class,
			com.jsjg73.hibernate.multiplicity.manytomany.n_ary.Course.class,
			com.jsjg73.hibernate.multiplicity.manytomany.n_ary.CourseRating.class,
			com.jsjg73.hibernate.multiplicity.manytomany.n_ary.CourseRatingKey.class,
			com.jsjg73.hibernate.multiplicity.manytomany.n_ary.Teacher.class
			));
	
	private List<Class<?>> entityClasses;
	
	private Strategy(List<Class<?>> entityClasses) {
		this.entityClasses = entityClasses;
	}
	
	public List<Class<?>> getEntityClasses() {
		return entityClasses;
	}

}
