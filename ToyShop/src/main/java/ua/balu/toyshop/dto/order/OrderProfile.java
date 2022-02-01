package ua.balu.toyshop.dto.order;

import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.dto.post.PostResponse;
import ua.balu.toyshop.model.OrderStatus;

import java.util.Set;

@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProfile implements Convertible {
    private  long id;
    private Set<PostResponse> posts;
    private OrderStatus status;
    private String address;
    private String note;

}
