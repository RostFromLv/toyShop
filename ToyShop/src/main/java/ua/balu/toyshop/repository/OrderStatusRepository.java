package ua.balu.toyshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.balu.toyshop.model.OrderStatus;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus,Long> {
    OrderStatus findByOrderStatus(ua.balu.toyshop.constant.OrderStatus orderStatus);
}
