package ua.balu.toyshop.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ua.balu.toyshop.dto.marker.Convertible;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class SuccessCreatedOrder implements Convertible {
    private long id;
    private String address;
    private double totalPrice;
    private String note;
}
