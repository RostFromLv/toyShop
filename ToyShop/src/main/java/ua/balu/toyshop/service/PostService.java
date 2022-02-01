package ua.balu.toyshop.service;

import ua.balu.toyshop.dto.category.CategoryProfile;
import ua.balu.toyshop.dto.post.*;
import ua.balu.toyshop.model.Post;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.List;

public interface PostService {

    /**
     * The method for creating new post, return Information about created post
     *
     * @param createPost
     * @param request
     * @return {@code SuccessCreatedPost}
     */
    SuccessCreatedPost addPost(CreatePost createPost, HttpServletRequest request);

    /**
     * The method for delete post, return information about deleted post
     *
     * @param deletePost
     * @param request
     * @return {@code SuccessDeletedPost}
     */
    SuccessDeletedPost deletePost(DeletePost deletePost, HttpServletRequest request);

    /**
     * The method for updating post, return information about updated post
     *
     * @param updatePost
     * @return {@code SuccessUpdatedPost}
     */
    SuccessUpdatedPost updatePost(UpdatePost updatePost);

    /**
     * The method return Active posts
     *
     * @return Set of {@code PostResponse}
     */
    Set<PostResponse> getPostsByActiveStatus();

    /**
     * The method return Disabled posts
     *
     * @return Set of {@code PostResponse}
     */
    Set<PostResponse> getPostsByDisabledStatus();

    /**
     * The method return Post by user Id
     *
     * @param userId
     * @return Set of {@code PostResponse}
     */
    Set<PostResponse> getPostsByUser(long userId);

    /**
     * The method return posts by categories(have mistakes in logic)
     *
     * @param categories
     * @return Set of {@code PostResponse}
     */
    Set<PostResponse> getPostByCategories(Set<CategoryProfile> categories);

    /**
     * The method for recount Post rating
     * @param postProfile
     * @return {@code PostRateResponse}
     */
    PostRateResponse countPostRating(PostProfile postProfile);

    boolean postExist(Post post);

}
