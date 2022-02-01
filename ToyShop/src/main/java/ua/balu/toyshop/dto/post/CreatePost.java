package ua.balu.toyshop.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.validation.constraints.*;
import java.util.Set;

@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatePost implements Convertible {
    private long id;
    @NotBlank
    @Size(min = 5, max = 25, message = "Title should have MIN 5 chars and MAX 25")
    private String title;
    @NotBlank
    @Size(min = 25, max = 1500, message = "Description should be between 25 and 1500 chars")
    private String description;
    @Min(value = 1, message = "Price should be more than 1")
    private double price;
    private String picture;
    private boolean active;
    @NotNull
    private Long userId;
    @NotNull
    private Set<String> categories;
}
