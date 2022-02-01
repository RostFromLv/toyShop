package ua.balu.toyshop.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.Category;
import ua.balu.toyshop.model.Complaint;
import ua.balu.toyshop.model.Feedback;
import ua.balu.toyshop.model.User;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostResponse  implements Convertible {
    private  long id;
    private String title;
    private String description;
    private double price;
    private String picture;
    private LocalDateTime localDateTime;
    private  boolean active;
    private double rate;
    private User user;
    private Set<Category> categories ;
    private Set<Feedback> feedback;
    private Set<Complaint> complaints;

}
