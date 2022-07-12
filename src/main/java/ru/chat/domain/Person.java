package ru.chat.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public static Person of(int id, String login, String password, Role role) {
        var person = new Person();
        person.id = id;
        person.login = login;
        person.password = password;
        person.role = Role.of(role.getId());
        return person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
