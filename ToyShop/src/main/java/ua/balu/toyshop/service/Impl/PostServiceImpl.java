package ua.balu.toyshop.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ua.balu.toyshop.constant.Role;
import ua.balu.toyshop.constant.Status;
import ua.balu.toyshop.converter.DtoConverter;
import ua.balu.toyshop.dto.category.CategoryProfile;
import ua.balu.toyshop.dto.post.*;
import ua.balu.toyshop.exception.AlreadyExistException;
import ua.balu.toyshop.exception.DatabaseRepositoryException;
import ua.balu.toyshop.exception.IncorrectActionException;
import ua.balu.toyshop.exception.NotExistException;
import ua.balu.toyshop.model.Category;
import ua.balu.toyshop.model.Feedback;
import ua.balu.toyshop.model.Post;
import ua.balu.toyshop.model.User;
import ua.balu.toyshop.repository.*;
import ua.balu.toyshop.service.PostService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

//@Slf4j
@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final static String WRONG_PERSON = "You cant  post for other person";
    private final static String WRONG_ROLE_CREATING = "Admin and manager cant create posts";
    private final static String WRONG_USER_STATUS = "Your status is incorrect to create post";
    private final static String POST_TITLE_ALREADY_EXIST = "Post with title %s  already exist";
    private final static String POST_DOESNT_EXIST_BY_ID = "Post with id %s doesnt exist";
    private final static String CANT_CREATE_POST = "Cant create post because of relationship";
    private final static String CATEGORY_IS_EMPTY = "Category list is empty";
    private final static String USER_NOT_EXIST_BY_ID = "User doesnt exist by id %s";
    private final static String ADMIN = Role.ADMIN.getName();
    private final static String MANAGER = Role.MANAGER.getName();


    private final PostRepository postRepository;
    private final UserServiceImpl userService;
    private final DtoConverter dtoConverter;
    private final CategoryRepository categoryRepository;
    private final FeedbackRepository feedbackRepository;
    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final CategoryServiceImpl categoryService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserServiceImpl userService, DtoConverter dtoConverter, CategoryRepository categoryRepository, FeedbackRepository feedbackRepository, ComplaintRepository complaintRepository, UserRepository userRepository, CategoryServiceImpl categoryService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.dtoConverter = dtoConverter;
        this.categoryRepository = categoryRepository;
        this.feedbackRepository = feedbackRepository;
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
    }

    //TODO +
    @Override
    public SuccessCreatedPost addPost(CreatePost createPost, HttpServletRequest request) {
        User postUser = userRepository.findById(createPost.getUserId()).orElseThrow(() -> new NotExistException(String.format(USER_NOT_EXIST_BY_ID, createPost.getUserId())));
        User requestUser = userService.getUserFromRequest(request);
        String userPostTitle = createPost.getTitle();

        if (!postUser.equals(requestUser)) {
            throw new IncorrectActionException(String.format(WRONG_PERSON, "create"));
        }
        if (!postUser.getRole().getName().equals(ADMIN)) {
            throw new IncorrectActionException(WRONG_ROLE_CREATING);
        }
        if (!postUser.getStatus().getName().equals(Status.ACTIVE.getName())) {
            throw new IncorrectActionException(WRONG_USER_STATUS);
        }
        if (postRepository.findAll().stream().map(Post::getTitle).anyMatch(post -> post.equals(userPostTitle))) {
            throw new AlreadyExistException(String.format(POST_TITLE_ALREADY_EXIST, userPostTitle));
        }

        Set<Category> categories = createPost
                .getCategories()
                .stream()
                .map(categoryService::getCategoryByType)
                .collect(Collectors.toSet());

        Post post;
        try {
            post = postRepository.save(dtoConverter.convertToEntity(createPost, new Post())
                    .withLocalDateTime(LocalDateTime.now())
                    .withRate(0.0)
                    .withCategories(categories)
                    .withUser(requestUser));
        } catch (DatabaseRepositoryException e) {
            throw new DatabaseRepositoryException(CANT_CREATE_POST);
        }

        return dtoConverter.convertToDto(post, SuccessCreatedPost.class);
    }

    //TODO +
    @Override
    public SuccessDeletedPost deletePost(DeletePost deletePost, HttpServletRequest request) {
        Post post = postRepository.findById(deletePost.getId())
                .orElseThrow(() -> new NotExistException(String.format(POST_DOESNT_EXIST_BY_ID, deletePost.getId())));

        User userFromRequest = userService.getUserFromRequest(request);
        User postUser = post.getUser();

        if (!userFromRequest.equals(postUser) && !userFromRequest.getRole().getName().equals(ADMIN)) {
            throw new IncorrectActionException(String.format(WRONG_PERSON, "delete"));
        }
        //TODO затестити чи видаляэ всі чи лише перші скарги та фідбеки, які находить
        try {
            //TODO Затеститu чи видаляє всі скарги та фідбеки
            complaintRepository.findAllByPostId(post.getId()).forEach(complaintRepository::delete);
            feedbackRepository.findAllByPostId(post.getId()).forEach(feedbackRepository::delete);
            postRepository.delete(post);
        } catch (DatabaseRepositoryException e) {
            throw new DatabaseRepositoryException("Cant delete Post");
        }

        return dtoConverter.convertToDto(post, SuccessDeletedPost.class);
    }

    //TODO +
    //TODO додати дефолтні (актів,час,статус)
    @Override
    public SuccessUpdatedPost updatePost(UpdatePost updatePost) {
        System.err.println("ONE");
        User user = userRepository.findById(updatePost.getUserId()).orElseThrow(() -> new NotExistException(String.format(USER_NOT_EXIST_BY_ID, updatePost.getUserId())));
        if (!validatePostUser(user)) {
            throw new IncorrectActionException(WRONG_PERSON);
        }

        Set<Category> categories = updatePost
                .getCategorySet()
                .stream()
                .map(categoryService::getCategoryByType)
                .collect(Collectors.toSet());
        Post post = postRepository.getById(updatePost.getId()).withUser(user);

        System.err.println(post);
        Post newPost = dtoConverter.convertToEntity(updatePost, post)
                .withLocalDateTime(LocalDateTime.now())
                .withActive(post.getActive())
                .withCategories(categories);

        return dtoConverter.convertToDto(postRepository.save(newPost), SuccessUpdatedPost.class);
    }

    //TODO +
    @Override
    public Set<PostResponse> getPostsByActiveStatus() {
        HttpServletRequest request = getRequest();
        if (!userService.getUserFromRequest(request).getRole().getName().equals(MANAGER) &&
                !userService.getUserFromRequest(request).getRole().getName().equals(ADMIN)) {
            throw new AlreadyExistException(WRONG_PERSON);
        }
        List<Post> posts = new ArrayList<>(postRepository.findAllByActiveTrue());


        return posts.stream()
                .map(post -> (PostResponse) dtoConverter.convertToDto(post, PostResponse.class))
                .collect(Collectors.toSet());
    }

    //TODO +
    @Override
    public Set<PostResponse> getPostsByDisabledStatus() {
        HttpServletRequest request = getRequest();
        if (!userService.getUserFromRequest(request).getRole().getName().equals(MANAGER) &&
                !userService.getUserFromRequest(request).getRole().getName().equals(ADMIN)) {
            throw new AlreadyExistException(WRONG_PERSON);
        }
        List<Post> posts = postRepository.findAllByActiveFalse();
        return posts.stream()
                .map(post -> (PostResponse) dtoConverter.convertToDto(post, PostResponse.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<PostResponse> getPostsByUser(long userId) {
        User user = userService.getUserFromRequest(getRequest());
        User searchUser = userRepository.getById(userId);
        if (!user.getRole().getName().equals(MANAGER) &&
                !user.getRole().getName().equals(ADMIN)) {
            throw new AlreadyExistException(WRONG_PERSON);
        }
        List<Post> posts = postRepository.findAllByUserId(searchUser.getId());

        return posts.stream().map(post -> (PostResponse) dtoConverter.convertToDto(post, PostResponse.class)).collect(Collectors.toSet());
    }

    //TODO +
    @Override
    public Set<PostResponse> getPostByCategories(Set<CategoryProfile> categories) {
        if (categories.isEmpty()) {
            throw new IncorrectActionException(CATEGORY_IS_EMPTY);
        }
        User user = userService.getUserFromRequest(getRequest());
        if (!user.getRole().getName().equals(MANAGER) &&
                !user.getRole().getName().equals(ADMIN)) {
            throw new AlreadyExistException(WRONG_PERSON);
        }

        postRepository.findAllByCategoriesId(5).forEach(System.err::println);

        List<Post> posts = categories.stream()
                .filter(category -> categoryService.existCategory(category.getType()))
                .map(categoryProfile -> categoryService.getCategoryByType(categoryProfile.getType()))
                .flatMap(category -> postRepository.findAllByCategoriesId(category.getId()).stream())
                .collect(Collectors.toList());

        System.out.println("--------------------------------------");
        posts.forEach(System.err::println);
        return posts.stream().map(post -> (PostResponse) dtoConverter.convertToDto(post, PostResponse.class)).collect(Collectors.toSet());
    }

    @Override
    public PostRateResponse countPostRating(PostProfile postProfile) {
        List<Feedback> postFeedbacks = feedbackRepository.findAllByPostId(postProfile.getPostId());
        Post post = postRepository.getById(postProfile.getPostId());
        if (postFeedbacks == null) {
            post.setRate(0.0);
            postRepository.save(post);
            return new PostRateResponse().withRate(0).withId(postProfile.getPostId());
        }
        IntSummaryStatistics statistics = postFeedbacks.stream().mapToInt(Feedback::getRate).summaryStatistics();
        double rating = statistics.getAverage();
        post.setRate(rating);
        postRepository.save(post);
        return new PostRateResponse().withRate(rating).withId(postProfile.getPostId());
    }

    @Override
    public boolean postExist(Post post) {
        return postRepository.findById(post.getId()).isPresent();

    }


    //USE IT MORE
    private boolean validatePostUser(User user) {
        HttpServletRequest request = getRequest();
        return user.equals(userService.getUserFromRequest(request));
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
