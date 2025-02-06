package backend.chaTop.apiChaTop.mappers;

import backend.chaTop.apiChaTop.dto.RentalCreation;
import backend.chaTop.apiChaTop.models.Rental;
import backend.chaTop.apiChaTop.models.User;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {

    public Rental mapFromRentalCreationDto(RentalCreation rentalCreation) {

        Rental rental = new Rental();
        rental.setName(rentalCreation.getName());
        rental.setSurface(rentalCreation.getSurface());
        rental.setPrice(rentalCreation.getPrice());
        rental.setPicture(rentalCreation.getPicture());
        rental.setDescription(rentalCreation.getDescription());
        User owner = new User();
        owner.setId(rentalCreation.getOwner_id());
        rental.setOwner(owner);
        rental.setOwner_id(rentalCreation.getOwner_id());

        return rental;
    }

    public void updateRentalFromDto(Rental rental, RentalCreation rentalCreation) {
        rental.setName(rentalCreation.getName());
        rental.setSurface(rentalCreation.getSurface());
        rental.setPrice(rentalCreation.getPrice());
        if (rentalCreation.getPicture()!=null) {
            rental.setPicture(rentalCreation.getPicture());
        }
        rental.setDescription(rentalCreation.getDescription());
        User owner = new User();
        owner.setId(rentalCreation.getOwner_id());
        rental.setOwner(owner);
    }
}
