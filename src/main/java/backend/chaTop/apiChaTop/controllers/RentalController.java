package backend.chaTop.apiChaTop.controllers;

import backend.chaTop.apiChaTop.dto.RentalCreation;
import backend.chaTop.apiChaTop.models.Rental;
import backend.chaTop.apiChaTop.services.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rentals")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @PostMapping("/create")
    public ResponseEntity<?>createRental(@RequestBody RentalCreation rental) {
        return ResponseEntity.ok(rentalService.createRental(rental));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List <Rental>>getAllRental() {
        return ResponseEntity.ok(rentalService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        return rental.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateRental(@PathVariable Long id, @RequestBody RentalCreation rentalCreation) {
        try {
            Rental updatedRental = rentalService.updateRental(id, rentalCreation);
            return ResponseEntity.ok(updatedRental);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
