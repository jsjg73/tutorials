package com.jsjg73.hibernate.multiplicity.manytomany.n_ary;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CourseRatingKey implements Serializable {

	@Column
	private Long studentId;

	@Column
	private Long courseId;
	
	@Column
	private Long teacherId;

	public CourseRatingKey() {
	}

	public CourseRatingKey(Long studentId, Long courseId, Long teacherId) {
		super();
		this.studentId = studentId;
		this.courseId = courseId;
		this.teacherId = teacherId;
	}


	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseId == null) ? 0 : courseId.hashCode());
		result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseRatingKey other = (CourseRatingKey) obj;
		if (courseId == null) {
			if (other.courseId != null)
				return false;
		} else if (!courseId.equals(other.courseId))
			return false;
		if (studentId == null) {
			if (other.studentId != null)
				return false;
		} else if (!studentId.equals(other.studentId))
			return false;
		return true;
	}
}
