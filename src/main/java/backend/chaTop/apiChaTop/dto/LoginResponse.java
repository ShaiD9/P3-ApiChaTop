package backend.chaTop.apiChaTop.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;

    // Constructeur sans argument nécessaire pour la désérialisation et l'instanciation
    public LoginResponse() {
    }

    // Constructeur avec token pour simplifier l'instanciation
    public LoginResponse(String token) {
        this.token = token;
    }
}
