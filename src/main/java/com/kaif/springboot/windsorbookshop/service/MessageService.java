package com.kaif.springboot.windsorbookshop.service;

import com.kaif.springboot.windsorbookshop.dto.MessageDTO;
import com.kaif.springboot.windsorbookshop.entitis.Message;
import com.kaif.springboot.windsorbookshop.repo.MessageRepository;
import com.kaif.springboot.windsorbookshop.repo.UserRepository;
import com.kaif.springboot.windsorbookshop.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveMessage(int user, String content) {
       User user1= userRepository.findById(user)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Message message = Message.builder()
                .user(user1)
                .content(content)
                .sentAt(LocalDateTime.now())
                .build();
        messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();  // Fetch all messages from the database
    }

    public List<MessageDTO> getAllMessagesForStaff() {
        List<Message> allMessages = messageRepository.findAll();
        return allMessages.stream()
                .map(MessageDTO::new) // Convert each Message to MessageDTO
                .toList(); // Collect the results into a List

    }


    public boolean deleteMessageById(int id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public String getEmailByMessageId(int id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with id: " + id));
        return message.getUser().getEmail();  // assuming your Message entity has `email` field
    }
}
