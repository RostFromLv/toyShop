package ua.balu.toyshop.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.utils.annotations.PhoneNumber;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUser implements Convertible {
    @NotNull
    long Id;
    @NotBlank(message = "Name is blank")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 chars")
    private String name;
    @NotBlank(message = "LastName is blank")
    @Size(min = 2, max = 50, message = "LastName should be between 2 and 50 chars")
    private String lastName;
    @NotBlank(message = "Email is blank")
    @Email
    private String email;
    @NotBlank(message = "Phone number is blank")
    @PhoneNumber
    private String phone;
    @NotNull
    private LocalDate dayOfBirth;
    @NotNull
    private Long cityId;

    private String picture;
}
