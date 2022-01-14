package com.jsjg73.hibernate.onetoone.jointablebased;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue
	@Column(name="emp_id")
	private Long id;
	
	@Column
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name="emp_workstation",
			joinColumns = {
					@JoinColumn(name="employee_id", referencedColumnName = "emp_id")
			},
			inverseJoinColumns = {
					@JoinColumn(name="workstation_id", referencedColumnName = "ws_id")
			})
	private WorkStation workStation;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public WorkStation getWorkStation() {
		return workStation;
	}
	public void setWorkStation(WorkStation workStation) {
		this.workStation = workStation;
	}
	
}
