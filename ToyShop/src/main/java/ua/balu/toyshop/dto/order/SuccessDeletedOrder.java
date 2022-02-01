package ua.balu.toyshop.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.dto.post.PostResponse;
import ua.balu.toyshop.dto.user.UserResponse;
import ua.balu.toyshop.model.Post;
import ua.balu.toyshop.model.User;

import java.util.Set;
import java.util.List;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class SuccessDeletedOrder  implements Convertible {
    private long id;
    private List<Post> post;
    private double totalPrice;
    private String address;
    private User user;
}
