package uta.ec.finance_manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uta.ec.finance_manager.dto.MessageDto;
import uta.ec.finance_manager.entity.Message;
import uta.ec.finance_manager.entity.User;
import uta.ec.finance_manager.repository.MessageRepository;
import uta.ec.finance_manager.repository.UserRepository;
import uta.ec.finance_manager.service.MessageService;
import uta.ec.finance_manager.util.UserUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final UserUtil userUtil;

    @Override
    public void create(String type, String content, Integer userId) {
        Message message = new Message();
        message.setType(type);
        message.setContent(content);
        message.setTimestamp(new Date());

        User user = userRepository.findById(userId).orElse(null);
        message.setUser(user);

        messageRepository.save(message);
    }

    @Override
    public List<MessageDto> getMessagesForCurrentUser() {
        Integer userId = userUtil.getUserId();
        return messageRepository.findByUserIdOrderByTimestampDesc(userId)
                .stream()
                .map(m -> new MessageDto(m.getType(), m.getContent(), m.getTimestamp()))
                .collect(Collectors.toList());
    }
}