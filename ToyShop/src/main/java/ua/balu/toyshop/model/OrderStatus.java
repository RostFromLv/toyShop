package ua.balu.toyshop.model;

import lombok.*;

import javax.persistence.*;

@With
@Builder
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orderStatus")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @Enumerated(value = EnumType.STRING)
    private ua.balu.toyshop.constant.OrderStatus orderStatus;
}
