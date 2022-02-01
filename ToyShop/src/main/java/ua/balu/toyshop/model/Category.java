package ua.balu.toyshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category implements Convertible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column
    private String type;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    @ToString.Exclude
    private Set<Post> post ;

}
