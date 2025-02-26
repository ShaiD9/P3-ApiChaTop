package backend.chaTop.apiChaTop.services;

import backend.chaTop.apiChaTop.models.User;
import io.jsonwebtoken.Jwt;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service utilitaire pour la gestion des informations liées à l'utilisateur authentifié.
 * Fournit des méthodes pour obtenir des informations sur l'utilisateur à partir du contexte de sécurité.
 */
@Service
public class UtilService {

    /**
     * Récupère l'utilisateur actuellement authentifié à partir du contexte de sécurité.
     * Cette méthode extrait l'utilisateur à partir du JWT (JSON Web Token) stocké dans le contexte de sécurité.
     * Si une erreur se produit ou si l'utilisateur n'est pas trouvé, la méthode retourne {@code null}.
     *
     * @return L'utilisateur authentifié, ou {@code null} si l'utilisateur ne peut pas être récupéré.
     */
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
