package ua.balu.toyshop.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.Category;
import ua.balu.toyshop.model.Complaint;
import ua.balu.toyshop.model.Feedback;
import ua.balu.toyshop.model.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuccessCreatedPost implements Convertible {
    private String title;
    private String description;
    private double price;
    private String picture;
    private LocalDateTime localDateTime;
    private boolean active;
    private double rate;
    private User user;
    private Set<Category> categories = new HashSet<>();
    private Set<Feedback> feedback;
    private Set<Complaint> complaints;
}
