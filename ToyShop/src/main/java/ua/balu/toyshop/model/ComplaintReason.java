//package ua.balu.toyshop.model;
//
//import lombok.*;
//import ua.balu.toyshop.dto.marker.Convertible;
//
//import javax.persistence.*;
//
//
//@With
//@Data
//@Entity
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "complaintReason")
//public class ComplaintReason implements Convertible {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private  long id;
//    @Column
//    private String description;
//    @ManyToOne
//    @JoinColumn(name = "complaint_id",referencedColumnName = "id")
//    @ToString.Exclude
//    private Complaint complaint;
//}
