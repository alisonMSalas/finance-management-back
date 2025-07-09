package uta.ec.finance_manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uta.ec.finance_manager.dto.MessageDto;
import uta.ec.finance_manager.service.AutomationService;
import uta.ec.finance_manager.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final AutomationService automationService;

    @GetMapping
    public List<MessageDto> getMessagesForCurrentUser() {
        return messageService.getMessagesForCurrentUser();
    }

    @PostMapping()
    public ResponseEntity<String> processAutomationsManually() {
        automationService.processAutomationsDaily();
        return ResponseEntity.ok("Automatizaciones procesadas manualmente");
    }

}