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

/**
 * Service pour gérer les messages associés aux locations.
 * Permet de créer de nouveaux messages et d'interagir avec les entités {@link Message}, {@link Rental} et {@link User}.
 */
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

    /**
     * Crée un message lié à une location et à un utilisateur.
     * Le message est ensuite sauvegardé dans la base de données.
     *
     * @param messageDTO L'objet {@link MessageDTO} contenant les informations du message.
     * @return L'objet {@link Message} créé et sauvegardé.
     * @throws IllegalArgumentException Si la location avec l'ID fourni n'existe pas.
     */
    public Message createMessage(MessageDTO messageDTO) {
        // Récupérer la location à partir de l'ID
        Rental rental = rentalService.getRentalById(messageDTO.getRental_id())
                .orElseThrow(() -> new IllegalArgumentException("Le bien (rental) avec l'ID fourni n'existe pas"));

        // Récupérer l'utilisateur
        User user = userService.getUserById(messageDTO.getUser_id());

        // Mapper l'objet MessageDTO en objet Message
        Message message = messageMapper.mapFromMessageDTO(messageDTO, rental, user);

        // Sauvegarder et retourner le message
        return messageRepository.save(message);
    }
}
