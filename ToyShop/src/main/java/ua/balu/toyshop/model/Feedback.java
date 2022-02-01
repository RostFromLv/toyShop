package ua.balu.toyshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@With
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feedback")
public class Feedback implements Convertible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @Column
    private String text;
    @Column
    private byte rate;
    @Column
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @ToString.Exclude
    private User user;
    @ManyToOne
//    @JsonBackReference
    @JoinColumn(name = "post_id",referencedColumnName = "id")
    private Post post;
}
