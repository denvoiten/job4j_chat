package ru.chat.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.chat.handlers.Operation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must be non null",
            groups = {ru.chat.handlers.Operation.OnUpdate.class, Operation.OnDelete.class})
    private int id;

    @NotBlank(message = "Role name must be not empty")
    @NotNull(message = "Room name must be non null")
    private String name;

    public static Role of(int id, String name) {
        var role = new Role();
        role.id = id;
        role.name = name;
        return role;
    }

    public static Role of(int id) {
        var role = new Role();
        role.id = id;
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return id == role.id && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
