package ua.balu.toyshop.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.City;
import ua.balu.toyshop.utils.annotations.Name;
import ua.balu.toyshop.utils.annotations.Password;
import ua.balu.toyshop.utils.annotations.PhoneNumber;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationUser implements Convertible {
    private long id;
    @NotBlank
    @Email(message = "Wrong email format")
    private String email;
    @NotBlank
    @Password
    private String password;
    @NotBlank
    @Name
    private String name;
    @NotBlank
    @Name
    private String lastName;
    @PhoneNumber
    private String phone;
    private String picture;
    @NotNull
    private Long cityId;
    @NotNull
    private LocalDate dayOfBirth;

}
