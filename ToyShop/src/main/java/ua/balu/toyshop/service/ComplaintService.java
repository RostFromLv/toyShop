package ua.balu.toyshop.service;

import ua.balu.toyshop.dto.complaint.ComplaintResponse;
import ua.balu.toyshop.dto.complaint.CreateComplaint;
import ua.balu.toyshop.dto.complaint.SuccessCreatedComplaint;
import ua.balu.toyshop.dto.post.PostProfile;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface ComplaintService {
    /**
     * The method for getting all complaint by Post return Set of ComplaintResponse
     * @param id
     * @return Set of {@code ComplaintResponse}
     */
    Set<ComplaintResponse> getAllComplaintByPost(Long  id);

    /**
     * The method for creating complaint, return information about created complaint
     * @param createComplaint
     * @param request
     * @return {@code SuccessCreatedComplaint}
     */
    SuccessCreatedComplaint addComplaint(CreateComplaint createComplaint, HttpServletRequest request);

    /**
     * The method for deleting complaint,return information about deleted complaint
     * @param id
     * @param request
     * @return {@code ComplaintResponse}
     */
    ComplaintResponse deleteComplaint(Long id,HttpServletRequest request);
}
