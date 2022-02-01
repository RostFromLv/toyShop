package ua.balu.toyshop.dto.complaintReason;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.Complaint;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintReasonResponse implements Convertible {

    private long id;
    private String description;
    private Long complaintId;

}
