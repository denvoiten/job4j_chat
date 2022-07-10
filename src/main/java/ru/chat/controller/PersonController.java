package ru.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chat.domain.Person;
import ru.chat.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> findAll() {
        return this.personService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return personService.findById(id).isPresent()
                ? new ResponseEntity<>(personService.findById(id).get(), HttpStatus.OK)
                : new ResponseEntity<>(new Person(), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<>(
                personService.create(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Person person) {
        this.personService.create(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        personService.delete(id);
        return ResponseEntity.ok().build();
    }
}
