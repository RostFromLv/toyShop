package ua.balu.toyshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.balu.toyshop.model.Feedback;

import java.util.Optional;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
      Optional<Feedback> findByUserId(long id);

      List<Feedback> findAllByPostId(long id);
}
