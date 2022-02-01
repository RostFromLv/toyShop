package ua.balu.toyshop.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.Role;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeUserRole implements Convertible {
    private Long userId;
    private Long roleId;
}
