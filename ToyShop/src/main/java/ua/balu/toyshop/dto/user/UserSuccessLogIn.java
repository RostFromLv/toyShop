package ua.balu.toyshop.dto.user;

import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;

@Builder
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class UserSuccessLogIn implements Convertible {
    private  long id;
    private String email;
    private String accessToken;
}
