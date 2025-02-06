package backend.chaTop.apiChaTop.controllers;

import backend.chaTop.apiChaTop.dto.RentalCreation;
import backend.chaTop.apiChaTop.models.Rental;
import backend.chaTop.apiChaTop.services.RentalService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rentals")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") int surface,
            @RequestParam("price") float price,
            @RequestParam("description") String description,
            @RequestPart(value = "picture", required = false) MultipartFile picture) {

        // Construire l'objet RentalCreation
        RentalCreation rental = new RentalCreation();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);

        // Gérer le fichier si nécessaire
        if (picture != null) {
            Path path = Paths.get("images");
            if (!Files.exists(path)) {
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            Path filepath = path.resolve(Objects.requireNonNull(picture.getOriginalFilename()));
            try {
                Files.copy(picture.getInputStream(),filepath, StandardCopyOption.REPLACE_EXISTING);
                rental.setPicture(String.valueOf(filepath).replaceAll("\\\\","/"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Appeler le service avec l'objet créé
        return ResponseEntity.ok(rentalService.createRental(rental));
    }

    @GetMapping("")
    public ResponseEntity<ObjectNode> getAllRental() {
        ObjectMapper obj = new ObjectMapper();
        ObjectNode result = obj.createObjectNode();
        ArrayNode rentals = obj.valueToTree(rentalService.getAll());
        result.put("rentals",rentals);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        return rental.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateRental(@PathVariable Long id, @RequestParam("name") String name,
    @RequestParam("surface") int surface,
    @RequestParam("price") float price,
    @RequestParam("description") String description)
    {

        // Construire l'objet RentalCreation
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
