package ua.balu.toyshop.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserEntity implements Convertible {
    private Long id;
    private String email;
    private String password;
    private String roleName;
    private boolean status;
}
