package ua.balu.toyshop.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Data
@With
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User implements Convertible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    @ToString.Exclude
    @JsonIgnore
    private String password;
    @Column
    private String phone;
    @Column
    private LocalDate dayOfBirth;
    @Column
    private String picture;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @JsonManagedReference(value = "statusInUser")
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "city_id",referencedColumnName = "id")
    private City city;

    @Column
    private String orderRequest;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<Order> order;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ToString.Exclude
    private Set<Post> posts ;
}
