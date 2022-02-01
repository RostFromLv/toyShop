package ua.balu.toyshop.service;


import ua.balu.toyshop.dto.feedback.*;
import ua.balu.toyshop.dto.post.PostProfile;
import ua.balu.toyshop.model.Feedback;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface FeedbackService {
    /**
     * Use this method to get all  Feedback for certain Post
     * @param postId
     * @return  Set of @{code  FeedbackResponse}
     */
    Set<FeedbackResponse> getAllFeedbackByPostId(long postId);

    /**
     * The method for creating Feedback
     * @param feedback
     * @param request
     * @return {@code SuccessCreatedFeedback}
     */
    SuccessCreatedFeedback createFeedback(CreateFeedback feedback, HttpServletRequest request);

    /**
     * The method for deleting Feedback
     *
     * @param feedback
     * @param request
     * @return {@code SuccessDeletedFeedback}
     */
    SuccessDeletedFeedback deleteFeedback(DeleteFeedback feedback,HttpServletRequest request);
}
