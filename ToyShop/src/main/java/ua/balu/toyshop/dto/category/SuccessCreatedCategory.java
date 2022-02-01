package ua.balu.toyshop.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuccessCreatedCategory implements Convertible {

    @NotBlank(message = "Id cannot be null or empty")
    private  long id;
    @NotBlank
    @Size(min = 5,max = 50,message = "Type must be between 5 and 50 chars")
    private String type;
}
