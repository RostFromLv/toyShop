package ua.balu.toyshop.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.balu.toyshop.converter.DtoConverter;
import ua.balu.toyshop.dto.order.*;
import ua.balu.toyshop.exception.DatabaseRepositoryException;
import ua.balu.toyshop.exception.IncorrectActionException;
import ua.balu.toyshop.exception.NotExistException;
import ua.balu.toyshop.model.Order;
import ua.balu.toyshop.model.OrderStatus;
import ua.balu.toyshop.model.Post;
import ua.balu.toyshop.model.User;
import ua.balu.toyshop.repository.OrderRepository;
import ua.balu.toyshop.repository.OrderStatusRepository;
import ua.balu.toyshop.repository.PostRepository;
import ua.balu.toyshop.repository.UserRepository;
import ua.balu.toyshop.service.OrderService;
import ua.balu.toyshop.service.PostService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {
    private static final String EMPTY_POSTS = "List of post is empty";
    private static final String SELF_ORDERING = "You cant create order for yourself";
    private static final String ORDER_NOT_EXIST_BY_ID = "Order by id %s doesnt exist";
    private static final String CANT_DELETE_ORDER = "Cant delete order with id=%s,because of relationship";
    private static final String USER_DOESNT_EXIST = "User with id %s doesnt exist";
    private static final  String POST_DOESNT_EXIST_BY_ID = "Post with id %s doesnt exist";

    private static final ua.balu.toyshop.constant.OrderStatus CONSIDERATION = ua.balu.toyshop.constant.OrderStatus.CONSIDERATION;

    private final DtoConverter dtoConverter;
    private final UserServiceImpl userService;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public OrderServiceImpl(DtoConverter dtoConverter,UserServiceImpl userService, OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, UserRepository userRepository, PostRepository postRepository) {
        this.dtoConverter = dtoConverter;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public SuccessCreatedOrder createOrder(CreateOrder createOrder, HttpServletRequest request) {

        List<Post> posts = new LinkedList<>();
        for (Long  postId: createOrder.getPostsId()) {
            posts.add(postRepository
                        .findById(postId)
                        .orElseThrow(()-> new NotExistException(String.format(POST_DOESNT_EXIST_BY_ID,postId))));
        }

        if (posts.isEmpty()) {
            throw new IncorrectActionException(EMPTY_POSTS);
        }
        List<User> postsUsers = posts.stream().map(Post::getUser).collect(Collectors.toList());
        User requestUser = userService.getUserFromRequest(request);
        postsUsers.forEach(user ->
        {
            if (user.equals(requestUser)) {
                throw new IncorrectActionException(SELF_ORDERING);
            }
        });
        System.out.println("-------------------------------");
        OrderStatus defaultStatus = orderStatusRepository.findByOrderStatus(CONSIDERATION);
        Double orderPrice = posts.stream()
                .map(Post::getPrice)
                .reduce(0.0,Double::sum);
        Order order = orderRepository.save(dtoConverter.convertToEntity(createOrder, new Order())
                .withOrderStatus(defaultStatus)
                .withTotalPrice(orderPrice)
                .withUser(requestUser)
                .withDateTime(LocalDateTime.now())
                .withPost(posts));

        return dtoConverter.convertToDto(order, SuccessCreatedOrder.class);
    }

    @Override
    public SuccessDeletedOrder deleteOrder(DeleteOrder deleteOrder) {
        Order order = orderRepository
                .findById(deleteOrder.getId())
                .orElseThrow(() -> new NotExistException(String.format(ORDER_NOT_EXIST_BY_ID, deleteOrder.getId())));
        try {
            orderRepository.delete(order);
        } catch (DatabaseRepositoryException e) {
            throw new DatabaseRepositoryException(String.format(CANT_DELETE_ORDER, order.getId()));
        }
        System.out.println(order);
        return dtoConverter.convertToDto(order, SuccessDeletedOrder.class);
    }

    @Override
    public Set<OrderResponse> getAllOrder() {
        return orderRepository.findAll().stream()
                .map(order -> (OrderResponse) dtoConverter.convertToDto(order, OrderResponse.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderResponse> getOrderByUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotExistException(String.format(USER_DOESNT_EXIST, userId)));

        return orderRepository.findAllByUser(user)
                .stream()
                .map(order -> (OrderResponse) dtoConverter.convertToDto(order, OrderResponse.class))
                .collect(Collectors.toSet());
    }

    //-
    @Override
    public StatusOrder handleOrder(StatusOrder statusOrder) {
        Order orderToChange = orderRepository.findById(statusOrder.getOrderId())
                .orElseThrow(() -> new NotExistException(String.format(ORDER_NOT_EXIST_BY_ID, statusOrder.getOrderId())));
        User user = orderToChange.getUser();
        OrderStatus status = orderStatusRepository.findById(statusOrder.getOrderStatusId()).orElseThrow(NotExistException::new);
        orderRepository.save(orderToChange.withOrderStatus(status));
        userRepository.save(user.withOrderRequest(user.getOrderRequest() + status.getOrderStatus().getStatus()));

        return statusOrder;
    }
}
