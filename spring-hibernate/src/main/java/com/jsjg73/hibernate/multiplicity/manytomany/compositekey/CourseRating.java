package com.jsjg73.hibernate.multiplicity.manytomany.compositekey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class CourseRating {
	
	@EmbeddedId
	private CourseRatingKey id;
	
	@ManyToOne
	@MapsId("studentId")
	@JoinColumn(name = "student_id")
	private Student student;

	@ManyToOne
	@MapsId("courseId")
	@JoinColumn(name = "course_id")
	private Course course;
	
	private int rating;
	
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public CourseRatingKey getId() {
		return id;
	}

	public void setId(CourseRatingKey id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
}
