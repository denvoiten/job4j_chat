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
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must be non null",
            groups = {Operation.OnUpdate.class, Operation.OnDelete.class})
    private int id;
    @NotBlank(message = "Room name must be not empty")
    private String name;

    public static Room of(String name) {
        var room = new Room();
        room.name = name;
        return room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return id == room.id && Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
