package ua.balu.toyshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.balu.toyshop.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByUserId(Long id);


    List<Post> findAllByActiveFalse();

    List<Post> findAllByActiveTrue();

    List<Post> findAllByUserId(long id);


    List<Post> getAllByCategories(@Param("category") String category);

    List<Post> findAllByCategoriesId(long id);
}
