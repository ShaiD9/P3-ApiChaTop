package backend.chaTop.apiChaTop.services;

import backend.chaTop.apiChaTop.models.User;
import io.jsonwebtoken.Jwt;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UtilService {
    public static User getUser() {
        Jwt jwt = null;
        try {
            jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }

        if (jwt == null || jwt.getBody() == null)
            return null;

        return (User) jwt.getBody();
    }
}
