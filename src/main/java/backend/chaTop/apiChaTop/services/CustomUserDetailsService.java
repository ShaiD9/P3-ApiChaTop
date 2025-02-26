package backend.chaTop.apiChaTop.services;

import backend.chaTop.apiChaTop.models.User;
import backend.chaTop.apiChaTop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Implémentation de {@link UserDetailsService} pour charger les détails d'un utilisateur
 * à partir de son adresse email.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Charge un utilisateur par son email.
     *
     * @param email L'adresse email de l'utilisateur.
     * @return Un objet {@link UserDetails} contenant les informations de l'utilisateur.
     * @throws UsernameNotFoundException Si aucun utilisateur n'est trouvé avec cet email.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
