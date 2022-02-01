package ua.balu.toyshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.balu.toyshop.model.Complaint;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
    Optional<Complaint> findByUserId(long id);

    Set<Complaint> findAllByPostId(long id);
}
