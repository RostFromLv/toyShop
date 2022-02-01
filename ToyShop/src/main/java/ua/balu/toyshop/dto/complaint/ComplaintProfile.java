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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintProfile implements Convertible {
    private long id;
    @NotBlank
    @Size(min = 10,max = 500,message = "Complaint must be between 10 and 500 chars ")
    private String text;
    @NotBlank(message = "Date is blank")
    private LocalDateTime dateTime;
    @NotBlank(message = "User is blank")
    private Long userId;
    @NotBlank(message = "Post is blank")
    private Long postId;
//    @NotNull(message = "Complaint reason is null")
//    private Set<ComplaintReason> complaintReasons;
}
