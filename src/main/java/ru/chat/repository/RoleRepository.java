package ru.chat.repository;

import org.springframework.data.repository.CrudRepository;
import ru.chat.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
