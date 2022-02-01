package ua.balu.toyshop.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.balu.toyshop.converter.DtoConverter;
import ua.balu.toyshop.dto.complaint.ComplaintResponse;
import ua.balu.toyshop.dto.complaint.CreateComplaint;
import ua.balu.toyshop.dto.complaint.SuccessCreatedComplaint;
import ua.balu.toyshop.exception.DatabaseRepositoryException;
import ua.balu.toyshop.exception.IncorrectActionException;
import ua.balu.toyshop.exception.NotExistException;
import ua.balu.toyshop.model.Complaint;
import ua.balu.toyshop.model.Post;
import ua.balu.toyshop.model.User;
import ua.balu.toyshop.repository.ComplaintRepository;
import ua.balu.toyshop.repository.PostRepository;
import ua.balu.toyshop.repository.UserRepository;
import ua.balu.toyshop.service.ComplaintService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ComplaintServiceImpl implements ComplaintService {

    private final static String USER_NOT_EXIST_BY_ID = "User with id %s doesnt exist";
    private final static String POST_NOT_EXIST_BY_ID = "Post with id %s doesnt exist";
    private final static String WRONG_COMPLAINT_REGISTRATION = "You cant create complaint to other user";
    private final static String WRONG_DELETING_REGISTRATION = "You cant delete other user complaint";
    private final static String SELF_COMPLAINT = "You cant complaint  your own post ";
    private final static String NOT_EXIST_COMPLAINT_BY_ID = "Complaint with id %s doesnt exist";
    private final static String COMPLAINT_DELETE_EXCEPTION = "Cant delete complaint because of relationship";

    private final DtoConverter dtoConverter;
    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final PostRepository postRepository;

    @Autowired
    public ComplaintServiceImpl(DtoConverter dtoConverter, ComplaintRepository complaintRepository, UserRepository userRepository, UserServiceImpl userService, PostRepository postRepository) {
        this.dtoConverter = dtoConverter;
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @Override
    public Set<ComplaintResponse> getAllComplaintByPost(Long id) {
        return complaintRepository.findAllByPostId(id).stream().map(complaint -> (ComplaintResponse) dtoConverter.ConvertToDto(complaint, ComplaintResponse.class)).collect(Collectors.toSet());
    }

    @Override
    public SuccessCreatedComplaint addComplaint(CreateComplaint createComplaint, HttpServletRequest request) {
        long complaintUserId = createComplaint.getUserId();
        User complaintUser = userRepository
                .findById(complaintUserId)
                .orElseThrow(() -> new NotExistException(String.format(USER_NOT_EXIST_BY_ID, complaintUserId)));
        User requestUser = userService.getUserFromRequest(request);
        Post complaintPost = postRepository
                .findById(createComplaint.getPostId())
                .orElseThrow(() -> new NotExistException(String.format(POST_NOT_EXIST_BY_ID, createComplaint.getPostId())));
        User postUser = complaintPost.getUser();

        if (!complaintUser.equals(requestUser)) {
            throw new IncorrectActionException(WRONG_COMPLAINT_REGISTRATION);
        }
        if (postUser.equals(requestUser)) {
            throw new IncorrectActionException(SELF_COMPLAINT);
        }

        Complaint complaint = complaintRepository.save(dtoConverter.convertToEntity(createComplaint, new Complaint())
                .withDateTime(LocalDateTime.now())
                .withUser(requestUser)
                .withPost(complaintPost));

        return dtoConverter.ConvertToDto(complaint, SuccessCreatedComplaint.class);
    }

    @Override
    public ComplaintResponse deleteComplaint(Long id, HttpServletRequest request) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new NotExistException(String.format(NOT_EXIST_COMPLAINT_BY_ID, id)));
        User complaintUser = complaint.getUser();
        User requestUser = userService.getUserFromRequest(request);
        if (!complaintUser.equals(requestUser)) {
            throw new IncorrectActionException(WRONG_DELETING_REGISTRATION);
        }
        try {
            complaintRepository.delete(complaint);
        } catch (DatabaseRepositoryException e) {
            throw new DatabaseRepositoryException(COMPLAINT_DELETE_EXCEPTION);
        }

        return dtoConverter.ConvertToDto(complaint, ComplaintResponse.class);
    }
}
