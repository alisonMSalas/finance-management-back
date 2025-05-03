package uta.ec.finance_manager.service;

import uta.ec.finance_manager.dto.MessageDto;

import java.util.List;

public interface MessageService {
    void create(String type, String content, Integer userId);
    List<MessageDto> getMessagesForCurrentUser();
}
