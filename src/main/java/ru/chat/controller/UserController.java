package ru.chat.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.chat.domain.Person;
import ru.chat.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private PersonService users;
    private BCryptPasswordEncoder encoder;

    public UserController(PersonService users,
                          BCryptPasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person user) {
        if (user.getLogin().isEmpty() || user.getPassword().isEmpty()) {
            throw new NullPointerException("Empty login or password");
        }
        var person = Person.of(user.getId(), user.getLogin(),
                encoder.encode(user.getPassword()), user.getRole());
        users.create(person);
    }

    @GetMapping("/all")
    public List<Person> findAll() {
        return users.findAll();
    }
}
