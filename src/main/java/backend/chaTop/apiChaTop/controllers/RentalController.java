package backend.chaTop.apiChaTop.controllers;

import backend.chaTop.apiChaTop.dto.RentalCreation;
import backend.chaTop.apiChaTop.models.Rental;
import backend.chaTop.apiChaTop.services.RentalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    //creation d'un rental
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") int surface,
            @RequestParam("price") float price,
            @RequestParam("description") String description,
            @RequestPart(value = "picture", required = false) MultipartFile picture) {

        RentalCreation rental = new RentalCreation();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);

        try {
            Rental createdRental = rentalService.createRental(rental, picture);
            return ResponseEntity.ok(createdRental);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'upload de l'image.");
        }
    }

    //renvoi la liste de toutes les rentals
    @GetMapping("")
    public ResponseEntity<ObjectNode> getAllRental() {
        ObjectMapper obj = new ObjectMapper();
        ObjectNode result = obj.createObjectNode();
        ArrayNode rentals = obj.valueToTree(rentalService.getAll());
        result.put("rentals", rentals);
        return ResponseEntity.ok(result);
    }

    //renvoi un rental suivant l id
    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //modifie un rental
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateRental(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("surface") int surface,
            @RequestParam("price") float price,
            @RequestParam("description") String description) {

        RentalCreation rental = new RentalCreation();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);

        try {
            Rental updatedRental = rentalService.updateRental(id, rental);
            return ResponseEntity.ok(updatedRental);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
