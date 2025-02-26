package backend.chaTop.apiChaTop.services;

import backend.chaTop.apiChaTop.dto.RegisterRequest;
import backend.chaTop.apiChaTop.mappers.UserMapper;
import backend.chaTop.apiChaTop.models.User;
import backend.chaTop.apiChaTop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service pour gérer les utilisateurs.
 * Permet de s'occuper des opérations liées aux utilisateurs, comme l'enregistrement, la récupération des informations
 * et la validation de l'existence d'un utilisateur.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper mapper;

    /**
     * Enregistre un nouvel utilisateur.
     * Vérifie d'abord si l'email n'est pas déjà utilisé, puis crée l'utilisateur.
     *
     * @param registerRequest L'objet {@link RegisterRequest} contenant les informations nécessaires à l'enregistrement.
     * @return Un message de confirmation.
     * @throws RuntimeException Si l'email est déjà utilisé par un autre utilisateur.
     */
    public String registerUser(RegisterRequest registerRequest) throws Exception {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }
        // Sauvegarder l'utilisateur
        userRepository.save(mapper.mapFromRegisterDto(registerRequest));
        return "Utilisateur crée avec succes";
    }

    /**
     * Vérifie si un utilisateur existe avec un email donné.
     *
     * @param email L'email à vérifier.
     * @return true si l'utilisateur existe, false sinon.
     */
    public boolean userExist(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Récupère un utilisateur à partir de son email.
     *
     * @param email L'email de l'utilisateur à rechercher.
     * @return Un objet {@link Optional} contenant l'utilisateur trouvé, ou vide si l'utilisateur n'existe pas.
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Récupère l'utilisateur actuellement authentifié via son email.
     *
     * @param email L'email de l'utilisateur.
     * @return L'utilisateur correspondant à l'email donné.
     * @throws UsernameNotFoundException Si l'utilisateur n'est pas trouvé.
     */
    public User getAuthenticatedUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable avec l'email : " + email));
    }

    /**
     * Récupère un utilisateur par son ID.
     *
     * @param id L'ID de l'utilisateur à rechercher.
     * @return L'utilisateur correspondant à l'ID donné.
     * @throws UsernameNotFoundException Si l'utilisateur n'est pas trouvé avec l'ID donné.
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable avec l'ID : " + id));
    }
}
