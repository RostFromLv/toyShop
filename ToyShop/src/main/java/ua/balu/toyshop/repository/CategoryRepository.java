package ua.balu.toyshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.balu.toyshop.model.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByType(String type);
    boolean existsByType(String type);
}
