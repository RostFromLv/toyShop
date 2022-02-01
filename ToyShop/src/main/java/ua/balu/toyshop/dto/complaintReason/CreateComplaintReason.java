package ua.balu.toyshop.dto.complaintReason;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.Complaint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateComplaintReason  implements Convertible {
    @NotBlank(message = "Description is blank")
    @Size(min = 5,max = 100,message = "Description must be between 5 and 100 chars")
    private String description;
    private Long complaintId;
}
