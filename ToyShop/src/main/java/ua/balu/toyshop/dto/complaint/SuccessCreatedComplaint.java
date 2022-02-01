package ua.balu.toyshop.dto.complaint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuccessCreatedComplaint implements Convertible {
    private long id;
    private String text;
    private LocalDateTime dateTime;
//    private Set<ComplaintReason> complaintReasons;

}
