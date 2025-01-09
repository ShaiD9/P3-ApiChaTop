package backend.chaTop.apiChaTop.controllers;

import backend.chaTop.apiChaTop.dto.LoginRequest;
import backend.chaTop.apiChaTop.dto.LoginResponse;
import backend.chaTop.apiChaTop.dto.RegisterRequest;
import backend.chaTop.apiChaTop.dto.UserDTO;
import backend.chaTop.apiChaTop.models.User;
import backend.chaTop.apiChaTop.services.AuthService;
import backend.chaTop.apiChaTop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;
    @Autowired
    private final UserService userService;

    // Route pour login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        String token = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(new LoginResponse(token));  // Utilisation du constructeur avec token
    }

    // Route pour register
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.registerUser(registerRequest));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getAuthenticatedUser(email);
        UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getName());
        return ResponseEntity.ok(userDTO);
    }
}
