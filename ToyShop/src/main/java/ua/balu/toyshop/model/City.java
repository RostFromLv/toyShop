package ua.balu.toyshop.model;

import lombok.*;
import ua.balu.toyshop.dto.marker.Convertible;

import javax.persistence.*;

@Entity
@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "city")
public class City implements Convertible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
}



