package backend.chaTop.apiChaTop.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.chaTop.apiChaTop.dto.LoginRequest;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Votre logique d'authentification, comme l'encryptage du mot de passe
    public String authenticate(String password) {
        String encodedPassword = passwordEncoder.encode(password);
        return encodedPassword;
    }

    public String authenticateUser(LoginRequest loginRequest) throws Exception {
        if (userService.userExist(loginRequest.getEmail())) {
            return jwtUtil.generateToken(loginRequest.getEmail());
        }
        return "Login n'existe pas";
    }
}
