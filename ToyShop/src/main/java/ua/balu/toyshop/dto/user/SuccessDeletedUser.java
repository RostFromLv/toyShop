package ua.balu.toyshop.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessDeletedUser implements Convertible {
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String city;
    private String role;
}
