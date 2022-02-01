package ua.balu.toyshop.dto.order;

import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.OrderStatus;

@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
public class StatusOrder implements Convertible {
    private long orderId;
    private long orderStatusId;
    private String statusReason;
}
