package com.satish.central.docs.employee.web.rest.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satish.central.docs.employee.db.entities.Employee;
import com.satish.central.docs.employee.db.repository.EmployeeRepository;


@RestController
@RequestMapping(value="/employee")
public class EmployeeResource {

	//Ideally you shall be using Service classes
	@Autowired
	EmployeeRepository employeeRepository;
	
	
	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees(){
		return ResponseEntity.ok(employeeRepository.findAll());
	}
	
	@GetMapping("/{employeeid}")
	public ResponseEntity<Employee> getEmployeeByEmployeeId(
	        @PathVariable("employeeid") final int employeeid) {
		final Optional<Employee> personInDB = this.employeeRepository.findById(employeeid);

		if (personInDB.isPresent()) {
			return ResponseEntity.ok(personInDB.get());
		} else {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<Employee> createNewEmployee(@RequestBody Employee person ){
		final Employee personInDB = this.employeeRepository.save(person);

		return new ResponseEntity<>(personInDB,HttpStatus.CREATED);
	}
	
	@PutMapping("/{employeeid}")
	public ResponseEntity<Employee> updateEmployeeById(
	        @PathVariable("employeeid") final int employeeid, 
	        @RequestBody(required=true) final Employee employeeDataToBeUpdated ) {
		 if (employeeid != employeeDataToBeUpdated.getEmployeeId() ){	//Just to make sure we have same person_id in path param and body.
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		 }
		 
		final Optional<Employee> personInDB = employeeRepository.findById(employeeid);

		if (personInDB.isPresent()) {
			final Employee person = employeeRepository.saveAndFlush(employeeDataToBeUpdated);

			return ResponseEntity.ok(person);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
