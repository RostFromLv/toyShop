package ua.balu.toyshop.dto.complaint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.Post;
import ua.balu.toyshop.model.User;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessUpdatedComplaint implements Convertible {
    private String text;
    private LocalDateTime dateTime;
    private User user;
    private Post post;
//    private Set<ComplaintReason> complaintReasons;
}
