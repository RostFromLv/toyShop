package ua.balu.toyshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

@Table
@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order implements Convertible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToMany
    @JoinTable(name = "post_orders",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "post_id")})
    @ToString.Include
    private List<Post> post ;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column
    private Double totalPrice;
    @Column
    private String address;
    @Column
    private LocalDateTime dateTime;
    @Column
    private String note;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;
}
