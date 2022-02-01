package ua.balu.toyshop.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@With
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "post")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id",scope = Long.class)
public class Post implements Convertible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Double price;
    @Column
    private String picture;
    @Column
    private LocalDateTime localDateTime;
    @Column
    private Boolean active;
    @Column
    private Double rate;

    @JsonBackReference(value = "UserInPost")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "post_category",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    @ToString.Exclude
    private Set<Category> categories ;

    @OneToMany(mappedBy = "post")
    @ToString.Exclude
    private Set<Feedback> feedback;

    @OneToMany(mappedBy = "post")
    @ToString.Exclude
    private Set<Complaint> complaints;


    @ManyToMany(mappedBy = "post")
    @JsonManagedReference(value = "orderInPost")
    @ToString.Exclude
    private Set<Order> orders ;

}
