package ru.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chat.domain.Room;
import ru.chat.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<Room> findAll() {
        return roomService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        return roomService.findById(id).isPresent()
                ? new ResponseEntity<>(roomService.findById(id).get(), HttpStatus.OK)
                : new ResponseEntity<>(new Room(), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Room> create(@RequestBody Room room) {
        return new ResponseEntity<>(
                roomService.create(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Room room) {
        roomService.create(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        roomService.delete(id);
        return ResponseEntity.ok().build();
    }
}
