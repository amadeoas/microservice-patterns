package com.satish.central.docs.person.web.rest.resource;

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

import com.satish.central.docs.person.db.entities.Person;
import com.satish.central.docs.person.db.repository.PersonRepository;


@RestController
@RequestMapping(value="/person")
public class PersonResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonResource.class);
	
	// Ideally you shall be using Service classes
	@Autowired
	private PersonRepository personRepository;
	
	
	@GetMapping
	public ResponseEntity< List<Person>> getAllPersons() {
        LOGGER.info("Retriving all people...");

        final ResponseEntity<List<Person>> response 
                = ResponseEntity.ok(this.personRepository.findAll());

        LOGGER.info("All people were retrieved");

        return response;
	}
	
	@GetMapping("/{personId}")
	public ResponseEntity<Person> getPersonById(
	        @PathVariable("personId") final int personId) {
        LOGGER.info("Retriving person with ID {}...", personId);

        final Optional<Person> personInDB = this.personRepository.findById(personId);
        final ResponseEntity<Person> response;

        if (personInDB.isPresent()) {
            response = ResponseEntity.ok(personInDB.get());
            LOGGER.info("Person with ID {} was retrieved", personId);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            LOGGER.info("Person with ID {} wasn't found", personId);
        }

        return response;
	}
	
	@PostMapping
	public ResponseEntity<Person> storePerson(
	        @RequestBody final Person person) {
        LOGGER.info("Creating person...");

        final Person personInDB = this.personRepository.save(person);
        final ResponseEntity<Person> result = new ResponseEntity<>(personInDB,HttpStatus.CREATED);

        LOGGER.info("Person was created");

        return result;
	}
	
	@PutMapping("/{personId}")
	public ResponseEntity<Person> updatePersonDetails(
	        @PathVariable("personId") final int personId,
	        @RequestBody(required=true) final Person personUpdate) {
        LOGGER.info("Updated person with ID {}", personId);

        if (personId != personUpdate.getId() ) {
             // Just to make sure we have same person_id in path parameters and body
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
         
        final Optional<Person> personInDB = this.personRepository.findById(personId);
        final ResponseEntity<Person> response;

        if (personInDB.isPresent()) {
            final Person employee = this.personRepository.saveAndFlush(personUpdate);

            response = ResponseEntity.ok(employee);
            LOGGER.info("Person with ID {} was created", personId);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            LOGGER.info("Person with ID {} wasn't found", personId);
        }

        return response;
	}

}
