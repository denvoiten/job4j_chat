package ru.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.chat.domain.Message;
import ru.chat.service.MessageService;

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
    public ResponseEntity<Message> create(@RequestBody Message message,
                                          @PathVariable int id) {
        validate(message);
        return new ResponseEntity<>(
                messageService.create(message, id),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/room/{id}")
    public ResponseEntity<Void> update(@RequestBody Message message,
                                       @PathVariable int id) {
        validate(message);
        messageService.create(message, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        messageService.delete(id);
        return ResponseEntity.ok().build();
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
