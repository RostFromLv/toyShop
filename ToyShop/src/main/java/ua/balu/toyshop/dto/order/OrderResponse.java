package ua.balu.toyshop.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.dto.post.PostProfile;
import ua.balu.toyshop.dto.user.UserResponse;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse implements Convertible {
    private long id;
    private Set<PostProfile> post;
    protected double totalPrice;
    private String address;
    private LocalDateTime dateTime;
    private String note;
    private UserResponse user;
}
