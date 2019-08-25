package com.satish.central.docs.employee.web.rest.resource;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeResource.class);

	// Ideally you shall be using Service classes
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
        LOGGER.info("Retriving all employees...");

        final ResponseEntity<List<Employee>> response 
	            = ResponseEntity.ok(this.employeeRepository.findAll());

        LOGGER.info("All employees were retrieved");

	    return response;
	}
	
	@GetMapping("/{employeeId}")
	public ResponseEntity<Employee> getEmployeeByEmployeeId(
	        @PathVariable("employeeId") final int employeeId) {
	    LOGGER.info("Retriving employee with ID {}...", employeeId);

	    final Optional<Employee> personInDB = this.employeeRepository.findById(employeeId);
	    final ResponseEntity<Employee> response;

		if (personInDB.isPresent()) {
			response = ResponseEntity.ok(personInDB.get());
			LOGGER.info("Employee with ID {} was retrieved", employeeId);
		} else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            LOGGER.info("Employee with ID {} wasn't found", employeeId);
		}

		return response;
	}
	
	@PostMapping
	public ResponseEntity<Employee> createNewEmployee(
	        @RequestBody final Employee employee) {
        LOGGER.info("Creating employee...");

        final Employee personInDB = this.employeeRepository.save(employee);
        final ResponseEntity<Employee> result = new ResponseEntity<>(personInDB,HttpStatus.CREATED);

        LOGGER.info("Employee was created");

        return result;
	}
	
	@PutMapping("/{employeeId}")
	public ResponseEntity<Employee> updateEmployeeById(
	        @PathVariable("employeeId") final int employeeId, 
	        @RequestBody(required=true) final Employee employeeUpdate) {
        LOGGER.info("Updated employee with ID {}", employeeId);

	    if (employeeId != employeeUpdate.getId() ) {
		     // Just to make sure we have same person_id in path parameters and body
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		 }
		 
		final Optional<Employee> personInDB = this.employeeRepository.findById(employeeId);
		final ResponseEntity<Employee> response;

		if (personInDB.isPresent()) {
			final Employee employee = this.employeeRepository.saveAndFlush(employeeUpdate);

	        response = ResponseEntity.ok(employee);
            LOGGER.info("Employee with ID {} was created", employeeId);
		} else {
		    response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            LOGGER.info("Employee with ID {} wasn't found", employeeId);
		}

		return response;
	}

}
