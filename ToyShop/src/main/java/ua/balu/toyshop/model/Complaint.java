package ua.balu.toyshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@With
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "complaint")
public class Complaint implements Convertible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String text;
    @Column
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "complaint")
//    private Set<ComplaintReason> complaintReasons = new HashSet<>();
}
