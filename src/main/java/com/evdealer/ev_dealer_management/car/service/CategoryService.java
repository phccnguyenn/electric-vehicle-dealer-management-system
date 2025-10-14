package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.Category;
import com.evdealer.ev_dealer_management.car.model.dto.category.CategoryGetDetailDto;
import com.evdealer.ev_dealer_management.car.model.dto.category.CategoryInfoGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.category.CategoryListGetDto;
import com.evdealer.ev_dealer_management.car.repository.CategoryRepository;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryListGetDto getAllCategory(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return toCategoryListGetDto(categoryPage);
    }

    public Category addCarToCategory(Long categoryId, Car car) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CATEGORY_NOT_FOUND, categoryId));
        category.getCars().add(car);
        return categoryRepository.save(category);
    }

//    public Category getCategoryByName(String categoryName) {
//        return categoryRepository.findByCategoryName()
//    }

    public CategoryGetDetailDto addNewCategory(String categoryName) {
        Category category = new Category();
        category.setCategoryName(categoryName.toUpperCase());

        return CategoryGetDetailDto.fromModel(categoryRepository.save(category));
    }

    public void renameCategory(Long categoryId, String categoryName) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.CATEGORY_NOT_FOUND, categoryId));

        if (categoryName != null && !category.getCategoryName().equals(categoryName.toUpperCase()))
            category.setCategoryName(categoryName.toUpperCase());

        categoryRepository.save(category);
    }

    private CategoryListGetDto toCategoryListGetDto(Page<Category> categoryPage) {
        List<CategoryInfoGetDto> categoryInfoGetDto = categoryPage.getContent()
                .stream()
                .map(CategoryInfoGetDto::fromModel)
                .toList();

        return new CategoryListGetDto(
                categoryInfoGetDto,
                categoryPage.getNumber(),
                categoryPage.getSize(),
                (int) categoryPage.getTotalElements(),
                categoryPage.getTotalPages(),
                categoryPage.isLast()
        );
    }

}
