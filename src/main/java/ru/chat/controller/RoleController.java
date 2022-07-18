package ru.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.chat.domain.Role;
import ru.chat.handlers.Operation;
import ru.chat.service.RoleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Role> findById(@PathVariable int id) {
        return roleService.findById(id)
                .map(role -> new ResponseEntity<>(role, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with id: " + id + " not found"));
    }

    @PostMapping
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Role> create(@Valid @RequestBody Role role) {
        if (role.getName() == null) {
            throw new NullPointerException("roleName cannot be empty");
        }
        return new ResponseEntity<>(
                roleService.create(role),
                HttpStatus.CREATED
        );
    }

    @PutMapping
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Role role) {
        if (role.getName() == null) {
            throw new NullPointerException("roleName cannot be empty");
        }
        roleService.create(role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Void> delete(@Valid @PathVariable int id) {
        roleService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Role> patch(@Valid @RequestBody Role role) {
        var current = roleService.findById(role.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Role with id = %s, not found", role.getId())));
        String name = role.getName();
        if (name != null) {
            current.setName(name);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleService.create(current));
    }
}
