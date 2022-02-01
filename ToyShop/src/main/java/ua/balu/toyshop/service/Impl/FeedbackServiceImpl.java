package ua.balu.toyshop.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.balu.toyshop.converter.DtoConverter;
import ua.balu.toyshop.dto.feedback.*;
import ua.balu.toyshop.exception.DatabaseRepositoryException;
import ua.balu.toyshop.exception.IncorrectActionException;
import ua.balu.toyshop.exception.NotExistException;
import ua.balu.toyshop.model.Feedback;
import ua.balu.toyshop.model.Post;
import ua.balu.toyshop.model.User;
import ua.balu.toyshop.repository.FeedbackRepository;
import ua.balu.toyshop.repository.PostRepository;
import ua.balu.toyshop.repository.UserRepository;
import ua.balu.toyshop.service.FeedbackService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final static String USER_NOT_EXIST_BY_ID = "User with id %s doesnt exist";
    private final static String POST_NOT_EXIST_BY_ID = "Post with id %s doesnt exist";
    private final static String WRONG_FEEDBACK_CREATING_REGISTRATION = "You cant create feedback to other user";
    private final static String SELF_FEEDBACKING = "You cant create feedback to your post";
    private final static String WRONG_DELETING_REGISTRATION = "You cant delete other user feedback";
    private final static String CANT_DELETE_FEEDBACK = "Cant delete feedback because of relationship";
    private final static LocalDateTime DATE_TIME_NOW = LocalDateTime.now();


    private final FeedbackRepository feedbackRepository;
    private final DtoConverter dtoConverter;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, DtoConverter dtoConverter, UserServiceImpl userService, UserRepository userRepository, PostRepository postRepository) {
        this.feedbackRepository = feedbackRepository;
        this.dtoConverter = dtoConverter;
        this.userService = userService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Set<FeedbackResponse> getAllFeedbackByPostId(long postId) {

        return feedbackRepository
                .findAllByPostId(postId)
                .stream()
                .map(feedback -> (FeedbackResponse) dtoConverter.ConvertToDto(feedback, FeedbackResponse.class))
                .collect(Collectors.toSet());
    }

    @Override
    public SuccessCreatedFeedback createFeedback(CreateFeedback feedback, HttpServletRequest request) {
        Post post = postRepository.findById(feedback.getPostId())
                .orElseThrow(() -> new NotExistException(String.format(POST_NOT_EXIST_BY_ID, feedback.getPostId())));

        User requestUser = userService.getUserFromRequest(request);
        User feedbackUser = userRepository.findById(feedback.getUserId())
                .orElseThrow(() -> new NotExistException(String.format(USER_NOT_EXIST_BY_ID, feedback.getUserId())));
        User postUser = post.getUser();
        if (!requestUser.equals(feedbackUser)) {
            throw new IncorrectActionException(WRONG_FEEDBACK_CREATING_REGISTRATION);
        }
        if (requestUser.equals(postUser)) {
            throw new IncorrectActionException(SELF_FEEDBACKING);
        }

        Feedback savedFeedback = feedbackRepository.save(
                dtoConverter.convertToEntity(
                        feedback, new Feedback())
                        .withDateTime(DATE_TIME_NOW)
                        .withPost(post)
                        .withUser(requestUser));

        return dtoConverter.ConvertToDto(savedFeedback, SuccessCreatedFeedback.class);
    }

    @Override
    public SuccessDeletedFeedback deleteFeedback(DeleteFeedback deleteFeedback, HttpServletRequest request) {
        Feedback feedback = feedbackRepository.getById(deleteFeedback.getId());
        User requestUser = userService.getUserFromRequest(request);
        User feedbackUser = feedback.getUser();
        if (!requestUser.equals(feedbackUser)) {
            throw new IncorrectActionException(WRONG_DELETING_REGISTRATION);
        }
        try {
            feedbackRepository.delete(feedback);
        } catch (DatabaseRepositoryException e) {
            throw new DatabaseRepositoryException(CANT_DELETE_FEEDBACK);
        }

        return dtoConverter.ConvertToDto(feedback, SuccessDeletedFeedback.class);
    }

    public static LocalDateTime getDateTimeNow() {
        return DATE_TIME_NOW;
    }

    public static String getWrongFeedbackCreatingRegistration() {
        return WRONG_FEEDBACK_CREATING_REGISTRATION;
    }

    public static String getSelfFeedbacking() {
        return SELF_FEEDBACKING;
    }

    public static String getWrongDeletingRegistration() {
        return WRONG_DELETING_REGISTRATION;
    }
}

//TODO Перевірити DTO і замість DTO  в них повставляти айдішки
