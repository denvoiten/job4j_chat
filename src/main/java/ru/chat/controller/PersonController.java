package ru.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.chat.domain.Person;
import ru.chat.domain.Role;
import ru.chat.handlers.Operation;
import ru.chat.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class.getSimpleName());
    private final PersonService personService;

    private final ObjectMapper objectMapper;

    public PersonController(PersonService personService, ObjectMapper objectMapper) {
        this.personService = personService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public List<Person> findAll() {
        return this.personService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return personService.findById(id)
                .map(person -> new ResponseEntity<>(person, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person with id: " + id + " not found"));
    }

    @PostMapping
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        validate(person);
        if (person.getLogin().length() < 5) {
            throw new IllegalArgumentException("Invalid login. Login length must be more than 4 characters.");
        }
        return new ResponseEntity<>(
                personService.create(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) {
        validate(person);
        if (person.getLogin().length() < 5) {
            throw new IllegalArgumentException("Invalid login. Login length must be more than 4 characters.");
        }
        personService.create(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Void> delete(@Valid @PathVariable int id) {
        personService.delete(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
        LOGGER.error(e.getLocalizedMessage());
    }

    @PatchMapping
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Person> patch(@Valid @RequestBody Person person) {
        var current = personService.findById(person.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Person with id = %s, not found", person.getId())));
        String login = person.getLogin();
        String password = person.getPassword();
        Role role = person.getRole();
        if (login != null) {
            current.setLogin(login);
        }
        if (password != null) {
            current.setPassword(password);
        }
        if (role != null) {
            current.setRole(role);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personService.create(current));
    }

    private void validate(Person person) {
        if (person.getLogin() == null) {
            throw new NullPointerException("Login cannot be empty.");
        }
        if (person.getPassword() == null) {
            throw new NullPointerException("Password cannot be empty.");
        }
        if (person.getRole() == null) {
            throw new NullPointerException("Role cannot be empty.");
        }
    }
}
