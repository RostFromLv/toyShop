package ua.balu.toyshop.dto.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.Post;
import ua.balu.toyshop.model.User;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessDeletedFeedback implements Convertible {
    private long id;
    private String text;
    private byte rate;
    private LocalDateTime dateTime;
}
