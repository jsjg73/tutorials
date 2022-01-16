package com.jsjg73.hibernate.multiplicity.manytomany;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "course_like",
			joinColumns = @JoinColumn(name = "student_id"),
			inverseJoinColumns = @JoinColumn(name="course_id"))
	private Set<Course> likedCourses = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Course> getLikedCourse() {
		return likedCourses;
	}

	public void setLikedCourse(Set<Course> likedCourse) {
		this.likedCourses = likedCourse;
	}

	public void like(Course... courses) {
		likedCourses.addAll(Arrays.stream(courses).collect(Collectors.toList()));
	}
	
}
