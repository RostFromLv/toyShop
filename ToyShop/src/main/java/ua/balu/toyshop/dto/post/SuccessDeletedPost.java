package ua.balu.toyshop.dto.post;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.balu.toyshop.dto.marker.Convertible;
import ua.balu.toyshop.model.Category;
import ua.balu.toyshop.model.Complaint;
import ua.balu.toyshop.model.Feedback;
import ua.balu.toyshop.model.User;

import java.util.Set;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessDeletedPost implements Convertible {

    private long id;
    private String title;
    private String description;
    private double price;
    private double rate;
    private User user;
    private Set<Feedback> feedbacks;
    private Set<Complaint> complaints;
    private Set<Category> categories;
}
