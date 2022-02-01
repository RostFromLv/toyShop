package ua.balu.toyshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role implements Convertible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    private List<User> users;

}
