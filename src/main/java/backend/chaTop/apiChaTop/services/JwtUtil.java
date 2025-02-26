package backend.chaTop.apiChaTop.services;

import backend.chaTop.apiChaTop.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Utilitaire pour la gestion des JSON Web Tokens (JWT).
 * Fournit des méthodes pour générer, valider et extraire des informations d'un token JWT.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Génère un token JWT basé sur l'email de l'utilisateur.
     *
     * @param email L'email de l'utilisateur pour lequel le token est généré.
     * @return Le token JWT généré.
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expiration après 10 heures
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    /**
     * Extrait le nom d'utilisateur (email) à partir du token JWT.
     *
     * @param token Le token JWT.
     * @return L'email de l'utilisateur extrait du token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait la date d'expiration du token JWT.
     *
     * @param token Le token JWT.
     * @return La date d'expiration du token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrait une réclamation spécifique du token JWT.
     *
     * @param token           Le token JWT.
     * @param claimsResolver  Une fonction qui résout une réclamation spécifique.
     * @param <T>             Le type de la réclamation extraite.
     * @return La réclamation extraite du token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrait toutes les réclamations du token JWT.
     *
     * @param token Le token JWT.
     * @return Les réclamations du token sous forme d'objet {@link Claims}.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Vérifie si le token JWT est expiré.
     *
     * @param token Le token JWT.
     * @return {@code true} si le token est expiré, {@code false} sinon.
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Valide un token JWT par rapport à un utilisateur.
     *
     * @param token       Le token JWT à valider.
     * @param userDetails Les détails de l'utilisateur (principal).
     * @return {@code true} si le token est valide, {@code false} sinon.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Récupère la clé de signature utilisée pour signer les tokens JWT.
     *
     * @return La clé de signature utilisée pour générer et valider les tokens.
     */
    private SecretKey getSignInKey() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secret);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error decoding the secret key", e);
        }
    }
}
