package ru.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.chat.domain.Message;
import ru.chat.domain.Person;
import ru.chat.domain.Room;
import ru.chat.handlers.Operation;
import ru.chat.service.MessageService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Message> findAll() {
        return messageService.findAll();
    }

    @GetMapping("/room/{id}")
    public List<Message> findAllByRoomId(@PathVariable int id) {
        return messageService.findAllByRoom(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        return messageService.findById(id)
                .map(message -> new ResponseEntity<>(message, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message with id: " + id + " not found"));
    }

    @PostMapping("/room/{id}")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Message> create(@Valid@RequestBody Message message,
                                          @PathVariable int id) {
        validate(message);
        return new ResponseEntity<>(
                messageService.create(message, id),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/room/{id}")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid@RequestBody Message message,
                                       @PathVariable int id) {
        validate(message);
        messageService.create(message, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Void> delete(@Valid @PathVariable int id) {
        messageService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/room/{id}")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Message> patch(@Valid @RequestBody Message message,
                                         @PathVariable int id) {
        var current = messageService.findById(message.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Message with id = %s, not found", message.getId())));
        String text = message.getText();
        Person person = message.getPerson();
        Room room = message.getRoom();
        if (text != null) {
            current.setText(text);
        }
        if (person != null) {
            current.setPerson(person);
        }
        if (room != null) {
            current.setRoom(room);
        }
        messageService.create(current, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(current);
    }

    private void validate(Message message) {
        if (message.getRoom() == null) {
            throw new NullPointerException("RoomID cannot be empty.");
        }
        if (message.getPerson() == null) {
            throw new NullPointerException("PersonID cannot be empty.");
        }
        if (message.getText() == null) {
            throw new NullPointerException("Text cannot be empty.");
        }
    }
}
