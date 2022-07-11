package ru.chat.repository;

import org.springframework.data.repository.CrudRepository;
import ru.chat.domain.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer> {
    List<Message> findMessageByRoomId(int id);
}
