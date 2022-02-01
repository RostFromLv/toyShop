package ua.balu.toyshop.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ua.balu.toyshop.model.Status;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.City;
import ua.balu.toyshop.model.Role;

import java.time.LocalDate;

@Builder
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuccessRegisteredUser implements Convertible {
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dayOfBirth;
    private City city;
    private String picture;
    private Role role;
    private Status status;
}
