package ua.balu.toyshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.balu.toyshop.controller.marker.Api;
import ua.balu.toyshop.dto.category.CategoryProfile;
import ua.balu.toyshop.dto.post.*;
import ua.balu.toyshop.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

/** +
 * Controller for handling Posts
 */
@RestController
public class PostController implements Api {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /** +
     * Use this endpoint to create post
     *
     * @param createPost
     * @param request
     * @return {@code SuccessCreatedPost}
     */
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MENAGER')")
    @PostMapping("/post")
    public SuccessCreatedPost createPost(@Valid @RequestBody CreatePost createPost, HttpServletRequest request) {
        return postService.addPost(createPost, request);
    }

    /** +
     * Use this endpoint to delete post  for id value
     *
     * @param deletePost
     * @param request
     * @return {@code SuccessDeletedPost}
     */
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @DeleteMapping("/post")
    public SuccessDeletedPost deletePost(@Valid @RequestBody DeletePost deletePost, HttpServletRequest request) {
        return postService.deletePost(deletePost, request);
    }

    /** +
     * Use this endpoint to update User post
     *
     * @param updatePost
     * @return {@code SuccessUpdatedPost}
     */
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','MANAGER')")
    @PutMapping("/post/update")
    public SuccessUpdatedPost updatedPost(@Valid @RequestBody UpdatePost updatePost) {
        System.err.println(1);
        return postService.updatePost(updatePost);
    }

    /** +
     * Use this endpoint to get post by status "Active"
     *
     * @return {@code Set<PostResponse> }
     */
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/post/active")
    public Set<PostResponse> getPostByActiveStatus() {
        return postService.getPostsByActiveStatus();
    }

    // Can unite up and bot methods

    /** +
     * Use this endpoint to get post by status "Disabled"
     *
     * @return {@code Set<PostResponse> }
     */
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/post/disabled")
    public Set<PostResponse> getPostByDisabledStatus() {
        return postService.getPostsByDisabledStatus();
    }

    /** +
     * Use this endpoint to get Post by  id of User
     *
     * @param userId
     * @return {@code Set<PostResponse>}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/post/user")
    public Set<PostResponse> getPostByUser(@RequestParam long userId) {
        return postService.getPostsByUser(userId);
    }


    /** +
     * Use this endpoint to get post by category value
     * @param categories
     * @return Set of {@code PostResponse}
     */
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MANAGER')")
    @GetMapping("/posts/categories")
    Set<PostResponse> getPostsByCategories(@RequestBody Set<CategoryProfile> categories) {
        return postService.getPostByCategories(categories);
    }

    //TODO test after complaint and feedbacks
    /** +
     * Use this endpoint to get Post real(actual) rating
     *
     * @param post
     * @return {@code PostRateResponse}
     */
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MANAGER')")
    @PatchMapping("/post/rating")
    public PostRateResponse countPostRating( @RequestBody PostProfile post) {
        return postService.countPostRating(post);
    }


}
