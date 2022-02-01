package ua.balu.toyshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.balu.toyshop.model.City;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {
}
