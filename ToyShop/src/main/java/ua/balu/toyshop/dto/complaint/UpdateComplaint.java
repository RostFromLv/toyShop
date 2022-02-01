package ua.balu.toyshop.dto.complaint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.Post;
import ua.balu.toyshop.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateComplaint implements Convertible {
    private long id;
    @NotBlank(message = "text is blank")
    @Size(min = 10, max = 500, message = "Complaint must be between 10 and 500 chars")
    private String text;
    @NotNull(message = "DateTime is null")
    private LocalDateTime dateTime;
    @NotNull(message = "User is null")
    private Long userId;
    @NotNull(message = "Post is null")
    private Long postId;
//    @NotNull(message = "Complaint reason is null")
//    private Set<ComplaintReason> complaintReasonSet;

}
