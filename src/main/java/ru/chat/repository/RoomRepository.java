package ru.chat.repository;

import org.springframework.data.repository.CrudRepository;
import ru.chat.domain.Room;

public interface RoomRepository extends CrudRepository<Room, Integer> {
}
