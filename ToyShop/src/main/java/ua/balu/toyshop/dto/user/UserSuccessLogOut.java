package ua.balu.toyshop.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSuccessLogOut implements Convertible {
    private  long id;
    private String email;
}
