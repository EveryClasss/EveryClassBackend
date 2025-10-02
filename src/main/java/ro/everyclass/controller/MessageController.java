package com.everyclass.controller;

import com.everyclass.dto.MessageDto;
import com.everyclass.service.MessageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDto> send(@RequestBody SendMessage req){
        return ResponseEntity.ok(messageService.send(req.senderId, req.receiverId, req.content));
    }

    @GetMapping("/with")
    public ResponseEntity<List<MessageDto>> with(@RequestParam Long a, @RequestParam Long b){
        return ResponseEntity.ok(messageService.conversation(a,b));
    }

    @Data
    public static class SendMessage {
        public Long senderId;
        public Long receiverId;
        public String content;
    }
}
