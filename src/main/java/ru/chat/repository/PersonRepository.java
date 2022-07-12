package ru.chat.repository;

import org.springframework.data.repository.CrudRepository;
import ru.chat.domain.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    Person findPersonByLogin(String login);
}
