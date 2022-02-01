package ua.balu.toyshop.dto.user;

import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.Role;
import ua.balu.toyshop.model.Status;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserStatus implements Convertible {
    private String email;
    private Long roleId;
    private Long statusId;
}
