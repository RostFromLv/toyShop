package ua.balu.toyshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "status")
public class Status implements Convertible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @JsonBackReference("userInStatus")
    @OneToMany(mappedBy = "status")
    @ToString.Exclude
    private List<User> users;

}
