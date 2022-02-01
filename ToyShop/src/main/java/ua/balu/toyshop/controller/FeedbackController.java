package ua.balu.toyshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.balu.toyshop.controller.marker.Api;
import ua.balu.toyshop.dto.feedback.*;
import ua.balu.toyshop.dto.post.PostProfile;
import ua.balu.toyshop.service.FeedbackService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

/**
 * Controller for managing feedbacks
 */
@RestController
public class FeedbackController implements Api {
    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /** +
     * Use this endpoint to get Feedback by certain id
     *
     * @param id
     * @return SET of {@code FeedbackResponse}
     */
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MANAGER')")
    @GetMapping("/feedbacks/post")
    public Set<FeedbackResponse> getFeedbacksByPostId(@RequestParam(name = "id") long id){
        return feedbackService.getAllFeedbackByPostId(id);
    }

    /** +
     * Use this endpoint to create new feedback
     *
     * @param {@code CreateFeedback}
     * @param request
     * @return @{code SuccessCreatedFeedback}
     */
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    @PostMapping("/feedback")
    public SuccessCreatedFeedback createFeedback(@Valid @RequestBody CreateFeedback feedback, HttpServletRequest request){
        return feedbackService.createFeedback(feedback,request);
    }

    /** +
     * Use this endpoint to delete feedback for id value
     *
     * @param {@code DeleteFeedback}
     * @param request
     *
     * @return {@code SuccessDeletedFeedback}
     */
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    @DeleteMapping("/feedback")
    public SuccessDeletedFeedback deleteFeedback(@Valid @RequestBody DeleteFeedback feedback,HttpServletRequest request){
        return feedbackService.deleteFeedback(feedback,request);
    }

}
