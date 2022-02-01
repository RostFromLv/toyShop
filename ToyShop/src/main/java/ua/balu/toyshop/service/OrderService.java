package ua.balu.toyshop.service;


import ua.balu.toyshop.dto.order.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface OrderService {

    /**
     * The method for create Order
     *
     * @param createOrder
     * @param request
     * @return {@code SuccessCreatedOrder}
     */
    SuccessCreatedOrder createOrder(CreateOrder createOrder, HttpServletRequest request);

    /**
     * The method for create Order
     *
     * @param deleteOrder
     * @return {@code SuccessDeletedOrder}
     */
    SuccessDeletedOrder deleteOrder(DeleteOrder deleteOrder);

    /**
     * The method return all Orders
     * @return Set of {@code OrderResponse}
     */
    Set<OrderResponse> getAllOrder();

    /**
     * The method for get Order by user Id
     * @param userId
     * @return Set of {@code SuccessDeletedOrder}
     */
    Set<OrderResponse> getOrderByUser(long userId);

    /**
     * The method for handling status for order , return new Order status
     *
     * @param statusOrder
     * @return {@code StatusOrder}
     */
    StatusOrder handleOrder(StatusOrder statusOrder);
}
