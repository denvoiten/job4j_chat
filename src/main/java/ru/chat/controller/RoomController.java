package ru.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.chat.domain.Room;
import ru.chat.handlers.Operation;
import ru.chat.service.RoomService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> findAll() {

        return ResponseEntity.of(Optional.of(roomService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        return roomService.findById(id)
                .map(room -> new ResponseEntity<>(room, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room with id: " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<Room> create(@Valid @RequestBody Room room) {
        if (room.getName() == null) {
            throw new NullPointerException("roomName cannot be empty.");
        }
        return new ResponseEntity<>(
                roomService.create(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Room room) {
        if (room.getName() == null) {
            throw new NullPointerException("roomName cannot be empty.");
        }
        roomService.create(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Void> delete(@Valid @PathVariable int id) {
        roomService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Room> patch(@Valid @RequestBody Room room) {
        var current = roomService.findById(room.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Room with id = %s, not found", room.getId())));
        String name = room.getName();
        if (name != null) {
            current.setName(name);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roomService.create(current));
    }
}
