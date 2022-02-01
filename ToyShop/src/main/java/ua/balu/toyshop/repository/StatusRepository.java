package ua.balu.toyshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.balu.toyshop.model.Status;

import java.util.Optional;


@Repository
public interface StatusRepository extends JpaRepository<Status,Long> {
    Optional<Status> findByName(String name);
}
