package ru.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chat.domain.Role;
import ru.chat.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    public Optional<Role> findById(int id) {
        return roleRepository.findById(id);
    }

    public Role create(Role role) {
        return roleRepository.save(role);
    }

    public void delete(int id) {
        roleRepository.findById(id).ifPresent(role -> roleRepository.delete(role));
    }
}
