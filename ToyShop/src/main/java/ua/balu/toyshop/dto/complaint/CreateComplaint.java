package ua.balu.toyshop.dto.complaint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateComplaint  implements Convertible {
    @NotBlank(message = "Complaint cannot be null or empty")
    @Size(min = 10,max = 500,message = "Complaint must be between 10 and 500 chars")
    private String text;
    @NotNull(message = "User is null")
    private Long userId;
    @NotNull(message = "Post is null")
    private Long postId;
//    @NotNull(message = "Complaint reasons is null")
//    private Set<ComplaintReason> complaintReasons;
}
