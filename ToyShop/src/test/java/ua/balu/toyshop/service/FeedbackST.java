package ua.balu.toyshop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.balu.toyshop.converter.DtoConverter;
import ua.balu.toyshop.dto.feedback.*;
import ua.balu.toyshop.exception.IncorrectActionException;
import ua.balu.toyshop.model.Feedback;
import ua.balu.toyshop.model.Post;
import ua.balu.toyshop.model.User;
import ua.balu.toyshop.repository.FeedbackRepository;
import ua.balu.toyshop.repository.PostRepository;
import ua.balu.toyshop.repository.UserRepository;
import ua.balu.toyshop.service.Impl.FeedbackServiceImpl;
import ua.balu.toyshop.service.Impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequestWrapper;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackST {
    @Mock
    FeedbackRepository feedbackRepository;
    @Mock
    DtoConverter dtoConverter;
    @Mock
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    PostRepository postRepository;
    @Mock
    private HttpServletRequestWrapper request;
    @InjectMocks
    FeedbackServiceImpl feedbackService;

    private final String CORRECT_USER_EMAIL = "user@gmail.com";
    private final String USER_NAME = "User NAME";
    private final String WRONG_USER_NAME = "WRONGUser NAME";
    private final String FEEDBACK_TEXT = "FEEDBACK TEXT #1";
    private final String CORRECT_POST_TITLE = "Post title";
    private final long CORRECT_FEEDBACK_ID = 2L;
    private final long CORRECT_USER_ID = 1L;
    private final long ANOTHER_USER_ID = 5l;
    private final long WRONG_USER_ID = 10L;
    private final long CORRECT_POST_ID = 1L;
    private final long WRONG_POST_ID = 2L;

    private final Feedback emptyFeedback = new Feedback();
    private final DeleteFeedback deleteFeedback = DeleteFeedback.builder().id(CORRECT_FEEDBACK_ID).build();
    private SuccessDeletedFeedback deletedFeedback;
    private SuccessDeletedFeedback successDeletedFeedback;
    private User correctUser;
    private User wrongUser;
    private List<Feedback> feedbackList;
    private Post correctPost;
    private Post anotherUserPost;
    private Feedback correctFeedback;
    private FeedbackResponse feedbackResponse;
    private Set<FeedbackResponse> feedbackResponseSet;
    private CreateFeedback createFeedback;
    private final SuccessCreatedFeedback successCreatedFeedback = SuccessCreatedFeedback.builder()
            .id(CORRECT_FEEDBACK_ID)
            .text(FEEDBACK_TEXT).build();
    private final LocalDateTime DATE_TIME_NOW = FeedbackServiceImpl.getDateTimeNow();

    @BeforeEach
    public void setUp() {
        correctUser = User.builder()
                .id(CORRECT_USER_ID)
                .email(CORRECT_USER_EMAIL)
                .name(USER_NAME)
                .build();

        correctPost = Post.builder()
                .id(CORRECT_POST_ID)
                .title(CORRECT_POST_TITLE)
                .user(correctUser)
                .active(true)
                .build();
        anotherUserPost = Post.builder()
                .id(ANOTHER_USER_ID)
                .title(FEEDBACK_TEXT)
                .user(wrongUser)
                .build();
        correctFeedback = Feedback.builder()
                .text(FEEDBACK_TEXT)
                .user(correctUser)
                .rate((byte) 5)
                .build();
        feedbackResponse = FeedbackResponse.builder()
                .text(FEEDBACK_TEXT)
                .rate((byte) 5)
                .post(correctPost)
                .build();
        createFeedback = CreateFeedback.builder()
                .userId(ANOTHER_USER_ID)
                .postId(CORRECT_POST_ID)
                .rate((byte) 5)
                .text(FEEDBACK_TEXT)
                .build();
        wrongUser = User.builder()
                .id(WRONG_USER_ID)
                .name(WRONG_USER_NAME)
                .build();
        deletedFeedback = SuccessDeletedFeedback.builder()
                .id(CORRECT_FEEDBACK_ID)
                .text(FEEDBACK_TEXT)
                .build();
        successDeletedFeedback = SuccessDeletedFeedback.builder()
                .id(CORRECT_FEEDBACK_ID)
                .text(FEEDBACK_TEXT)
                .build();

        feedbackList = new ArrayList<>();
        feedbackList.add(correctFeedback);
        feedbackList.add(correctFeedback);

        feedbackResponseSet = new HashSet<>();
        feedbackResponseSet.add(feedbackResponse);
        feedbackResponseSet.add(feedbackResponse);

    }

    @Test
    void getAllFeedbackByCorrectPostIdShouldReturnSetOfFeedbackResponse() {
        when(feedbackRepository.findAllByPostId(CORRECT_POST_ID)).thenReturn(feedbackList);
        when(dtoConverter.convertToDto(correctFeedback, FeedbackResponse.class)).thenReturn(feedbackResponse);

        assertThat(feedbackService.getAllFeedbackByPostId(CORRECT_POST_ID)).isEqualTo(feedbackResponseSet);
    }

    @Test
    void getAllFeedbackByWrongPostIdShouldReturnEmptyList() {
        when(feedbackRepository.findAllByPostId(WRONG_POST_ID)).thenReturn(new ArrayList<>());

        assertThat(feedbackService.getAllFeedbackByPostId(WRONG_POST_ID)).hasSize(0);
    }

    @Test
    void createFeedbackByCorrectUserShouldReturnSuccessCreatedFeedback() {
        when(postRepository.findById(CORRECT_POST_ID)).thenReturn(Optional.of(anotherUserPost));
        when(userService.getUserFromRequest(request)).thenReturn(correctUser);
        when(userRepository.findById(createFeedback.getUserId())).thenReturn(Optional.of(correctUser));
        when(dtoConverter.convertToEntity(createFeedback, emptyFeedback))
                .thenReturn(correctFeedback
                        .withDateTime(DATE_TIME_NOW)
                        .withPost(correctPost)
                        .withUser(correctUser));

        when(feedbackRepository.save(correctFeedback
                .withPost(correctPost)
                .withUser(correctUser)
                .withDateTime(DATE_TIME_NOW))).thenReturn(correctFeedback);

        when(dtoConverter.convertToDto(correctFeedback, SuccessCreatedFeedback.class)).thenReturn(successCreatedFeedback);

        SuccessCreatedFeedback actual = feedbackService.createFeedback(createFeedback, request);

        assertThat(actual).isEqualTo(successCreatedFeedback);
    }

    @Test
    void createFeedbackByWrongUserMustReturnIncorrectActionException() {
        when(postRepository.findById(CORRECT_POST_ID)).thenReturn(Optional.of(correctPost));
        when(userService.getUserFromRequest(request)).thenReturn(wrongUser);
        when(userRepository.findById(createFeedback.getUserId())).thenReturn(Optional.of(correctUser));

        IncorrectActionException exception = Assertions.assertThrows(IncorrectActionException.class, () -> feedbackService.createFeedback(createFeedback, request));
        assertThat(exception.getMessage()).contains(FeedbackServiceImpl.getWrongFeedbackCreatingRegistration());

    }

    @Test
    void createFeedbackForYourselfMustReturnIncorrectActionException() {
        when(postRepository.findById(CORRECT_POST_ID)).thenReturn(Optional.of(correctPost));
        when(userService.getUserFromRequest(request)).thenReturn(correctUser);
        when(userRepository.findById(createFeedback.getUserId())).thenReturn(Optional.of(correctUser));

        IncorrectActionException exception = Assertions.assertThrows(IncorrectActionException.class, () -> feedbackService.createFeedback(createFeedback, request));
        assertThat(exception.getMessage()).contains(FeedbackServiceImpl.getSelfFeedbacking());

    }

    @Test
    void deleteFeedbackByCorrectUserShouldReturnSuccessDeletedFeedback() {
        when(feedbackRepository.getById(CORRECT_FEEDBACK_ID)).thenReturn(correctFeedback);
        when(userService.getUserFromRequest(request)).thenReturn(correctUser);
        doNothing().when(feedbackRepository).delete(correctFeedback);
        when(dtoConverter.convertToDto(correctFeedback, SuccessDeletedFeedback.class)).thenReturn(deletedFeedback);

        SuccessDeletedFeedback actual = feedbackService.deleteFeedback(deleteFeedback, request);
        assertThat(actual).isEqualTo(successDeletedFeedback);
    }

    @Test
    void deleteFeedbackByCorrectUserShouldReturnIncorrectActionException() {
        when(feedbackRepository.getById(CORRECT_FEEDBACK_ID)).thenReturn(correctFeedback);
        when(userService.getUserFromRequest(request)).thenReturn(wrongUser);
        IncorrectActionException exception = Assertions.assertThrows(IncorrectActionException.class, () -> feedbackService.deleteFeedback(deleteFeedback, request));
        assertThat(exception.getMessage()).contains(FeedbackServiceImpl.getWrongDeletingRegistration());
    }

}
