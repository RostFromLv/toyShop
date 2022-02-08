package ua.balu.toyshop.service;

import ua.balu.toyshop.dto.category.CategoryProfile;
import ua.balu.toyshop.dto.category.CategoryResponse;
import ua.balu.toyshop.dto.category.SuccessCreatedCategory;
import java.util.List;

public interface CategoryService {
    /**
     * The method  for adding category
     * @param categoryProfile
     * @return {@code SuccessCreatedCategory}
     */
    SuccessCreatedCategory addCategory(CategoryProfile categoryProfile);

    /**
     * The method for deleting Category
     * @param categoryProfile
     * @return {@code CategoryResponse}
     */
    CategoryResponse deleteCategory(CategoryProfile categoryProfile);

    List<CategoryResponse> getAllCategories();
}
