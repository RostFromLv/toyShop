package ua.balu.toyshop.dto.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFeedback implements Convertible {

    @NotBlank
    @Size(min = 10, max = 1500, message = "Feedback must be between 10 and 1500 chars")
    private String text;
    @Min(value = 1, message = "Rate cannot have rate less than 1 ")
    @Max(value = 10, message = "Rate cannot have rate more than 10")
    private byte rate;

    @NotNull(message = "User is null")
    private Long userId;
    @NotNull(message = "Post is null")
    private Long postId;

}
