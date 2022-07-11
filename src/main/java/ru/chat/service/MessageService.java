package ru.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chat.domain.Message;
import ru.chat.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RoomService roomService;

    public List<Message> findAll() {
        return (List<Message>) messageRepository.findAll();
    }

    public Optional<Message> findById(int id) {
        return messageRepository.findById(id);
    }

    public List<Message> findAllByRoom(int id) {
        return messageRepository.findMessageByRoomId(id);
    }

    public Message create(Message message, int roomId) {
        return roomService.findById(roomId)
                .map(room -> messageRepository.save(Message.of(
                        message.getId(),
                        message.getText(),
                        message.getPerson(),
                        room)))
                .orElse(null);
    }

    public void delete(int id) {
        messageRepository.findById(id).ifPresent(message -> messageRepository.delete(message));
    }
}
