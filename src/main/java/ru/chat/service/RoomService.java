package ru.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chat.domain.Room;
import ru.chat.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> findAll() {
        return (List<Room>) roomRepository.findAll();
    }

    public Optional<Room> findById(int id) {
        return roomRepository.findById(id);
    }

    public Room create(Room room) {
        return roomRepository.save(room);
    }

    public void delete(int id) {
        roomRepository.findById(id).ifPresent(room -> roomRepository.delete(room));
    }
}
