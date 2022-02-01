package ua.balu.toyshop.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.persistence.Column;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Convertible {
    private long id;
    private String name;
    private String lastName;
    private LocalDate dayOfBirth;
    private String city;
    private String picture;
    private String role;
}
