package ru.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.chat.domain.Person;
import ru.chat.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll() {
        return (List<Person>) personRepository.findAll();
    }

    public Optional<Person> findById(@PathVariable int id) {
        return personRepository.findById(id);
    }

    public Person findByLogin(String login) {
        return personRepository.findPersonByLogin(login);
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }

    public void delete(int id) {
        personRepository.findById(id).ifPresent(person -> personRepository.delete(person));
    }
}
