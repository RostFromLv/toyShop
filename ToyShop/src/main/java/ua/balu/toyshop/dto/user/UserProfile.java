package ua.balu.toyshop.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile implements Convertible {
    private long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dayOfBirth;
    private String city;
    private String picture;
    private String role;
}
