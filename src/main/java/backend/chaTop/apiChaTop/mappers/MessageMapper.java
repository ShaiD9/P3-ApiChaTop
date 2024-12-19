package backend.chaTop.apiChaTop.mappers;

import backend.chaTop.apiChaTop.dto.MessageDTO;
import backend.chaTop.apiChaTop.models.Message;
import backend.chaTop.apiChaTop.models.Rental;
import backend.chaTop.apiChaTop.models.User;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public Message mapFromMessageDTO(MessageDTO messageDTO, Rental rental, User user) {
        Message message = new Message();
        message.setRental(rental);
        message.setUser(user);
        message.setMessage(messageDTO.getMessage());
        return message;
    }
}
