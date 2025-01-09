package backend.chaTop.apiChaTop.controllers;

import backend.chaTop.apiChaTop.dto.LoginRequest;
import backend.chaTop.apiChaTop.dto.LoginResponse;
import backend.chaTop.apiChaTop.dto.MessageDTO;
import backend.chaTop.apiChaTop.models.Message;
import backend.chaTop.apiChaTop.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("")
    public ResponseEntity<?> createMessage(@RequestBody MessageDTO messageDTO) {
        Message message = messageService.createMessage(messageDTO);
        return ResponseEntity.status(201).body(message);
    }
}
