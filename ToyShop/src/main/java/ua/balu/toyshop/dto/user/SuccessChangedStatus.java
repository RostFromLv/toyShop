package ua.balu.toyshop.dto.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;

@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuccessChangedStatus implements Convertible {
    private String name;
    private String email;
    private String status;
}
