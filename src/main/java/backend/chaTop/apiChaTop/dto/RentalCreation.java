package backend.chaTop.apiChaTop.dto;

import lombok.Data;

@Data
public class RentalCreation {
    private String name;
    private float surface;
    private float price;
    private String picture;
    private String description;
    private Integer owner_id;
}
