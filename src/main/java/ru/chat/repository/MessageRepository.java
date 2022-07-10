package ru.chat.repository;

import org.springframework.data.repository.CrudRepository;
import ru.chat.domain.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {
}
