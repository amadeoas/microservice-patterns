package com.satish.central.docs.employee.db.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;


/**
 * Representation of an employee.
 */
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEE")
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private String email;

	
	public int getEmployeeId() {
        return employeeId;
    }

	public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

	public String getEmployeeFirstName() {
        return employeeFirstName;
    }

	public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

	public String getEmployeeLastName() {
        return employeeLastName;
    }

	public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

	public String getEmail() {
        return email;
    }

	public void setEmail(String email) {
        this.email = email;
    }

}
