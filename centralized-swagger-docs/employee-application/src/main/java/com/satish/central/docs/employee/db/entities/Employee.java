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
	private int id;
	private String firstName;
	private String lastName;
	private String email;

	
	public int getId() {
        return this.id;
    }

	public void setId(final int id) {
        this.id = id;
    }

	public String getEmployeeFirstName() {
        return this.firstName;
    }

	public void setEmployeeFirstName(final String firstName) {
        this.firstName = firstName;
    }

	public String getLastName() {
        return this.lastName;
    }

	public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

	public String getEmail() {
        return this.email;
    }

	public void setEmail(final String email) {
        this.email = email;
    }

}
