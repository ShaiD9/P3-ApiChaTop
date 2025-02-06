package backend.chaTop.apiChaTop.services;

import backend.chaTop.apiChaTop.dto.MessageDTO;
import backend.chaTop.apiChaTop.mappers.MessageMapper;
import backend.chaTop.apiChaTop.models.Message;
import backend.chaTop.apiChaTop.models.Rental;
import backend.chaTop.apiChaTop.models.User;
import backend.chaTop.apiChaTop.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageMapper messageMapper;

    public Message createMessage(MessageDTO messageDTO) {
        Rental rental = rentalService.getRentalById(messageDTO.getRental_id())
                .orElseThrow(() -> new IllegalArgumentException("Le bien (rental) avec l'ID fourni n'existe pas"));
        User user = userService.getUserById(messageDTO.getUser_id());
        Message message = messageMapper.mapFromMessageDTO(messageDTO, rental, user);
        return messageRepository.save(message);
    }
}
