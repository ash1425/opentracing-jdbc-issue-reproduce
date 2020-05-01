package com.ashay.opentracing.jdbctest.controller;

import com.ashay.opentracing.jdbctest.domain.Person;
import com.ashay.opentracing.jdbctest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PersonRepository personRepository;

    @Autowired
    public PeopleController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> getPeople() {
        return personRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Person person) {
        personRepository.save(person);
        return ResponseEntity.accepted().build();
    }
}
