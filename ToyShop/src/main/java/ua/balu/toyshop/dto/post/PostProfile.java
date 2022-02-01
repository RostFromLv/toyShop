package ua.balu.toyshop.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;

@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostProfile implements Convertible {
    private long postId;
    private String title;
    private long userId;

}
