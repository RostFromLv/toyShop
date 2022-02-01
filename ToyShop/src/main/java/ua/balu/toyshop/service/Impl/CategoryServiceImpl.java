package ua.balu.toyshop.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.balu.toyshop.converter.DtoConverter;
import ua.balu.toyshop.dto.category.CategoryProfile;
import ua.balu.toyshop.dto.category.CategoryResponse;
import ua.balu.toyshop.dto.category.SuccessCreatedCategory;
import ua.balu.toyshop.exception.AlreadyExistException;
import ua.balu.toyshop.exception.DatabaseRepositoryException;
import ua.balu.toyshop.exception.NotExistException;
import ua.balu.toyshop.model.Category;
import ua.balu.toyshop.repository.CategoryRepository;
import ua.balu.toyshop.service.CategoryService;

import java.util.Comparator;
import java.util.Optional;


@Service
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final static String ALREADY_EXIST = "Category with name %s  already exist";
    private final static String NOT_EXIST_EXCEPTION = " Category with name %s not exist";
    private final static  String CANT_DELETE_CATEGORY = "Category with name %s cant be deleted";
    private final CategoryRepository categoryRepository;
    private final DtoConverter dtoConverter;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, DtoConverter dtoConverter) {
        this.categoryRepository = categoryRepository;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public SuccessCreatedCategory addCategory(CategoryProfile categoryProfile) {

        String categoryType = categoryProfile.getType();

        if (existCategory(categoryType)) {
            throw new AlreadyExistException(String.format(ALREADY_EXIST, categoryType));
        }

        Category category = categoryRepository.save(dtoConverter.convertToEntity(categoryProfile, new Category()));
        log.debug("Added new category {}", category);

        return dtoConverter.ConvertToDto(category, SuccessCreatedCategory.class);
    }

    @Override
    public CategoryResponse deleteCategory(CategoryProfile categoryProfile) {

        Category category = getCategoryByType(categoryProfile.getType());

        try {
            categoryRepository.delete(category);
            categoryRepository.flush();
        }catch (DatabaseRepositoryException e){
            throw  new DatabaseRepositoryException(String.format(CANT_DELETE_CATEGORY,category.getType()));
        }

        log.debug("Successfully deleted category"+category.getType());

        return dtoConverter.ConvertToDto(category,CategoryResponse.class);
    }

    public Category getCategoryByType(String categoryName){

        Optional<Category> categoryOptional = getOptionalCategoryByType(categoryName);
        if (!categoryOptional.isPresent()){
            throw new NotExistException(String.format(NOT_EXIST_EXCEPTION,categoryName));
        }
        Category category = categoryOptional.get();
        log.debug("Get category:"+categoryName);
        return category;
    }
    public Optional<Category> getOptionalCategoryByType(String type){
       return categoryRepository.findAll().stream().filter(category -> category.getType().equals(type)).findAny();
    }

    public boolean existCategory(String categoryName) {
        return categoryRepository
                .findAll()
                .stream()
                .anyMatch(category -> category.getType().equals(categoryName));

    }



}
