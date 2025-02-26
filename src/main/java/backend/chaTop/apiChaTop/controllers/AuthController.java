package backend.chaTop.apiChaTop.controllers;

import backend.chaTop.apiChaTop.dto.LoginRequest;
import backend.chaTop.apiChaTop.dto.LoginResponse;
import backend.chaTop.apiChaTop.dto.RegisterRequest;
import backend.chaTop.apiChaTop.dto.UserDTO;
import backend.chaTop.apiChaTop.models.User;
import backend.chaTop.apiChaTop.services.AuthService;
import backend.chaTop.apiChaTop.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
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

    //route login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.login(loginRequest);
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LoginResponse(e.getMessage()));
        }
    }

    //route register
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest registerRequest) {
        try {
            String token = authService.register(registerRequest);
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LoginResponse(e.getMessage()));
        }
    }

    //route me
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(authService.getAuthenticatedUser(email));
    }
}
