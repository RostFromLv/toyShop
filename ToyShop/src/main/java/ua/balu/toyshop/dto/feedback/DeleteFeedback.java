package ua.balu.toyshop.dto.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteFeedback  implements Convertible {
    private long id;
}
