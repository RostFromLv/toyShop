package ua.balu.toyshop.dto.feedback;

import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.Post;
import ua.balu.toyshop.model.User;

import java.time.LocalDateTime;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackProfile implements Convertible {

    private String text;
    private byte rate;
    private LocalDateTime dateTime;
    private User user;
    private Post post;
}
