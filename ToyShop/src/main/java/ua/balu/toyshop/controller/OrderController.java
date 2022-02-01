package ua.balu.toyshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.balu.toyshop.controller.marker.Api;
import ua.balu.toyshop.dto.order.*;
import ua.balu.toyshop.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;
import java.util.List;

/**
 * @Author RostFromLv
 * Controller for managing orders
 */
@RestController
public class OrderController implements Api {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /** +
     * Use this endpoint to create order
     *
     * @param createOrder
     * @param request
     *
     * @return {@code SuccessCreatedOrder}
     */
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/order")
    public SuccessCreatedOrder createdOrder(@Valid @RequestBody CreateOrder createOrder, HttpServletRequest request) {
        return orderService.createOrder(createOrder, request);
    }

    /** +
     * Use endpoint to delete Order can use only User and Manager
     *
     * @param order
     * @return {@code SuccessDeletedOrder}
     */
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    @DeleteMapping("/order")
    public SuccessDeletedOrder deletedOrder(@RequestBody DeleteOrder order) {
        return orderService.deleteOrder(order);
    }

    /** +
     * The endpoint for getting all exist Orders
     *
     * @return {@code Set<OrderResponse>}
     */
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @GetMapping("/orders")
    public Set<OrderResponse> getAllOrders() {
        return orderService.getAllOrder();
    }

    /** +
     * Use this endpoint to get Orders by User id
     *
     * @param userId
     * @return {@code Set<OrderResponse>}
     */
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @GetMapping("/orders/user")
    public Set<OrderResponse> getOrdersByUser(@RequestParam long userId) {
        return orderService.getOrderByUser(userId);
    }

    /** +
     * Use this endpoint for handle order status(reject or approve)
     *
     * @param statusOrder
     * @return {@code StatusOrder}
     */
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PatchMapping("/order")
    public StatusOrder handleOrder(@Valid @RequestBody StatusOrder statusOrder) {
        return orderService.handleOrder(statusOrder);
    }

}

//TODO обчислити рейт поста