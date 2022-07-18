package ru.chat.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.chat.handlers.Operation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must be non null",
            groups = {Operation.OnUpdate.class, Operation.OnDelete.class})
    private int id;
    @NotBlank(message = "Text must be not empty")
    private String text;
    private Timestamp created;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @NotNull(message = "You must set the person")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @NotNull(message = "You must set the room")
    private Room room;

    public static Message of(int id, String text, Person person, Room room) {
        var message = new Message();
        message.id = id;
        message.text = text;
        message.person = person;
        message.room = room;
        message.created = new Timestamp(System.currentTimeMillis());
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return id == message.id
                && Objects.equals(person, message.person)
                && Objects.equals(room, message.room)
                && Objects.equals(created, message.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, room, created);
    }
}
