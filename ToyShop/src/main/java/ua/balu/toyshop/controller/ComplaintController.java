package ua.balu.toyshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.balu.toyshop.controller.marker.Api;
import ua.balu.toyshop.dto.complaint.ComplaintResponse;
import ua.balu.toyshop.dto.complaint.CreateComplaint;
import ua.balu.toyshop.dto.complaint.SuccessCreatedComplaint;
import ua.balu.toyshop.dto.post.PostProfile;
import ua.balu.toyshop.service.ComplaintService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

/** +
 * Controller for managing complaint
 */
@RestController
public class ComplaintController implements Api {

    private final ComplaintService complaintService;

    @Autowired
    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    /** +
     * Use this endpoint to obtain all complaints by Post
     * @param id
     * @return Set of {@code ComplaintResponse}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/complaint/post")
    public Set<ComplaintResponse> getAllComplaintByPost(@RequestParam(name = "id") long id) {
        return complaintService.getAllComplaintByPost(id);
    }

    /** +
     * Use this endpoint to create complaint
     *
     * @param createComplaint
     * @param request
     * @return {@code SuccessCreatedComplaint}
     */
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','MANAGER')")
    @PostMapping("/complaint")
    public SuccessCreatedComplaint createComplaint(@Valid @RequestBody CreateComplaint createComplaint, HttpServletRequest request){
        return complaintService.addComplaint(createComplaint,request);
    }

    /** +
     * Use this endpoint to delete complaint
     * @param id
     * @param request
     * @return {@code ComplaintResponse}
     */
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    @DeleteMapping("/complaint")
    public ComplaintResponse deleteComplaint(@RequestParam Long id,HttpServletRequest request){
        return complaintService.deleteComplaint(id,request);
    }
}
