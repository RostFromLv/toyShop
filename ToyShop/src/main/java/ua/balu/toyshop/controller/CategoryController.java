package ua.balu.toyshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.balu.toyshop.controller.marker.Api;
import ua.balu.toyshop.converter.DtoConverter;
import ua.balu.toyshop.dto.category.CategoryProfile;
import ua.balu.toyshop.dto.category.CategoryResponse;
import ua.balu.toyshop.dto.category.SuccessCreatedCategory;
import ua.balu.toyshop.repository.CategoryRepository;
import ua.balu.toyshop.service.CategoryService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/** +
 * Controller for managing category
 */

@RestController
public class CategoryController implements Api {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final DtoConverter dtoConverter;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository, DtoConverter dtoConverter) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.dtoConverter = dtoConverter;
    }

    /** +
     * Use this endpoint to create Category
     *
     * @param categoryProfile
     * @return {@code SuccessCreatedCategory}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/category")
    public SuccessCreatedCategory addCategory(
            @Valid
            @RequestBody CategoryProfile categoryProfile) {
        return categoryService.addCategory(categoryProfile);
    }

    /** +
     * Use this endpoint to delete Category
     *
     * @param categoryProfile
     * @return {@code CategoryResponse}
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/category")
    public CategoryResponse deleteCategory(
            @Valid @RequestBody CategoryProfile categoryProfile) {
        return categoryService.deleteCategory(categoryProfile);
    }

    /** +
     * Use this endpoint to get all categories
     *
     * @return {@code List<CategoryResponse>}
     */
    //WRONG (Logic in controller(move to service))
    @PreAuthorize("hasAnyAuthority('USER','ADMIN','MANAGER')")
    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategories() {
        return getAllCategories();
    }

}
