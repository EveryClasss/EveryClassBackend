package com.everyclass.service;

import com.everyclass.dto.MessageDto;
import com.everyclass.entity.Message;
import com.everyclass.entity.User;
import com.everyclass.exception.ResourceNotFoundException;
import com.everyclass.repository.MessageRepository;
import com.everyclass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepo;
    private final UserRepository userRepo;

    public MessageDto send(Long senderId, Long receiverId, String content){
        User s = userRepo.findById(senderId).orElseThrow(() -> new ResourceNotFoundException("Sender not found"));
        User r = userRepo.findById(receiverId).orElseThrow(() -> new ResourceNotFoundException("Receiver not found"));
        Message m = messageRepo.save(Message.builder().sender(s).receiver(r).content(content).build());
        return MessageDto.builder()
                .id(m.getId()).senderId(s.getId()).receiverId(r.getId())
                .senderName(s.getName()).receiverName(r.getName())
                .content(m.getContent()).createdAt(m.getCreatedAt())
                .build();
    }

    public List<MessageDto> conversation(Long userA, Long userB){
        User a = userRepo.findById(userA).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User b = userRepo.findById(userB).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return messageRepo.findBySenderAndReceiver(a,b).stream().map(m -> MessageDto.builder()
                .id(m.getId()).senderId(m.getSender().getId()).receiverId(m.getReceiver().getId())
                .senderName(m.getSender().getName()).receiverName(m.getReceiver().getName())
                .content(m.getContent()).createdAt(m.getCreatedAt())
                .build()).toList();
    }
}
