package ua.balu.toyshop.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.persistence.Convert;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class PostRateResponse  implements Convertible {
    private long id;
    private double rate;
}
