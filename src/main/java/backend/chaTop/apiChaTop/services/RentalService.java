package backend.chaTop.apiChaTop.services;

import backend.chaTop.apiChaTop.dto.RentalCreation;
import backend.chaTop.apiChaTop.mappers.RentalMapper;
import backend.chaTop.apiChaTop.models.Rental;
import backend.chaTop.apiChaTop.models.User;
import backend.chaTop.apiChaTop.repositories.RentalRepository;
import backend.chaTop.apiChaTop.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Service gérant les opérations sur les locations.
 */
@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper;
    private final UserRepository userRepository;
    private final ImageService imageService;

    /**
     * Constructeur du service RentalService.
     * @param rentalRepository Repository des locations.
     * @param rentalMapper Mapper pour convertir les objets.
     * @param userRepository Repository des utilisateurs.
     * @param imageService Service de gestion des images.
     */
    public RentalService(RentalRepository rentalRepository, RentalMapper rentalMapper,
                         UserRepository userRepository, ImageService imageService) {
        this.rentalRepository = rentalRepository;
        this.rentalMapper = rentalMapper;
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    /**
     * Crée une nouvelle location.
     * @param rentalCreation Les détails de la location.
     * @param picture L'image associée (optionnelle).
     * @return La location créée.
     * @throws IOException Si une erreur survient lors de la sauvegarde de l'image.
     */
    public Rental createRental(RentalCreation rentalCreation, MultipartFile picture) throws IOException {
        User user = getAuthenticatedUser();
        rentalCreation.setOwner_id(user.getId());

        if (picture != null) {
            String imagePath = imageService.saveImage(picture);
            rentalCreation.setPicture(imagePath);
        }

        return rentalRepository.save(rentalMapper.mapFromRentalCreationDto(rentalCreation));
    }

    /**
     * Récupère toutes les locations.
     * @return Liste des locations.
     */
    public List<Rental> getAll() {
        return rentalRepository.findAll();
    }

    /**
     * Récupère une location par son ID.
     * @param id L'ID de la location.
     * @return Une location, si elle existe.
     */
    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(Math.toIntExact(id));
    }

    /**
     * Met à jour une location existante.
     * @param id L'ID de la location à mettre à jour.
     * @param rentalCreation Les nouvelles informations de la location.
     * @return La location mise à jour.
     * @throws IllegalArgumentException Si la location n'est pas trouvée.
     */
    public Rental updateRental(Long id, RentalCreation rentalCreation) {
        User user = getAuthenticatedUser();
        rentalCreation.setOwner_id(user.getId());

        Rental existingRental = rentalRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new IllegalArgumentException("Rental not found with id: " + id));

        rentalMapper.updateRentalFromDto(existingRental, rentalCreation);
        return rentalRepository.save(existingRental);
    }

    /**
     * Récupère l'utilisateur actuellement connecté.
     * @return L'utilisateur authentifié.
     * @throws UsernameNotFoundException Si l'utilisateur n'est pas trouvé.
     */
    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
