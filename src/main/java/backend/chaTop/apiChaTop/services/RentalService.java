package backend.chaTop.apiChaTop.services;

import backend.chaTop.apiChaTop.dto.RentalCreation;
import backend.chaTop.apiChaTop.mappers.RentalMapper;
import backend.chaTop.apiChaTop.models.Rental;
import backend.chaTop.apiChaTop.models.User;
import backend.chaTop.apiChaTop.repositories.RentalRepository;
import backend.chaTop.apiChaTop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private RentalMapper rentalMapper;

    @Autowired
    private UserRepository userRepository;

    public Rental createRental(RentalCreation rentalCreation) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        rentalCreation.setOwner_id(user.getId());
        return rentalRepository.save(rentalMapper.mapFromRentalCreationDto(rentalCreation));
    }

    public List<Rental> getAll() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(Math.toIntExact(id));
    }

    public Rental updateRental(Long id, RentalCreation rentalCreation) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        rentalCreation.setOwner_id(user.getId());
        Rental existingRental = rentalRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new IllegalArgumentException("Rental not found with id: " + id));
        rentalMapper.updateRentalFromDto(existingRental, rentalCreation);
        return rentalRepository.save(existingRental);
    }
}
