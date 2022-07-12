package ru.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/rooms/{id}")
    public List<Message> findAllByRoomId(@PathVariable int id) {
        return messageService.findAllByRoom(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        return messageService.findById(id)
                .map(message -> new ResponseEntity<>(message, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new Message(), HttpStatus.NOT_FOUND));
    }

    @PostMapping("/rooms/{id}")
    public ResponseEntity<Message> create(@RequestBody Message message,
                                          @PathVariable int id) {
        return new ResponseEntity<>(
                messageService.create(message, id),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Void> update(@RequestBody Message message,
                                       @PathVariable int id) {
        messageService.create(message, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        messageService.delete(id);
        return ResponseEntity.ok().build();
    }
}
