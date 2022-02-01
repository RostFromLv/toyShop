package ua.balu.toyshop.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.Category;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdatePost implements Convertible {

    @NotNull(message = "Id cannot be null")
    @Min(value = 0, message = "Id cannot be less than 0")
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
    private Set<String> categorySet ;

}
