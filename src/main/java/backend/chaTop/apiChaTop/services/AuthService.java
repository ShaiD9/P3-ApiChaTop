package backend.chaTop.apiChaTop.services;

import backend.chaTop.apiChaTop.dto.LoginRequest;
import backend.chaTop.apiChaTop.dto.RegisterRequest;
import backend.chaTop.apiChaTop.dto.UserDTO;
import backend.chaTop.apiChaTop.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service gérant l'authentification et l'inscription des utilisateurs.
 */
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Constructeur du service AuthService.
     *
     * @param passwordEncoder Encodeur de mot de passe utilisé pour la sécurité.
     * @param userService Service de gestion des utilisateurs.
     * @param jwtUtil Utilitaire pour la génération des tokens JWT.
     */
    public AuthService(PasswordEncoder passwordEncoder, UserService userService, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Permet à un utilisateur de se connecter.
     *
     * @param loginRequest Objet contenant les informations de connexion (email).
     * @return Un token JWT si l'utilisateur existe.
     * @throws Exception Si l'utilisateur n'existe pas.
     */
    public String login(LoginRequest loginRequest) throws Exception {
        if (userService.userExist(loginRequest.getEmail())) {
            return jwtUtil.generateToken(loginRequest.getEmail());
        }
        throw new Exception("Login n'existe pas");
    }

    /**
     * Enregistre un nouvel utilisateur et génère un token JWT.
     *
     * @param registerRequest Objet contenant les informations d'inscription (nom, email, mot de passe).
     * @return Un token JWT pour l'utilisateur nouvellement enregistré.
     * @throws Exception En cas d'erreur lors de l'enregistrement.
     */
    public String register(RegisterRequest registerRequest) throws Exception {
        userService.registerUser(registerRequest);
        return jwtUtil.generateToken(registerRequest.getEmail());
    }

    /**
     * Récupère les informations de l'utilisateur actuellement connecté.
     *
     * @param email Email de l'utilisateur.
     * @return Un objet UserDTO contenant les informations de l'utilisateur.
     */
    public UserDTO getAuthenticatedUser(String email) {
        User user = userService.getAuthenticatedUser(email);
        return new UserDTO(user.getId(), user.getEmail(), user.getName());
    }
}
