package com.evdealer.ev_dealer_management.car.controller;

import com.evdealer.ev_dealer_management.car.model.dto.car.CarListGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.category.CategoryGetDetailDto;
import com.evdealer.ev_dealer_management.car.model.dto.category.CategoryListGetDto;
import com.evdealer.ev_dealer_management.car.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<CategoryListGetDto> getAllCars(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(categoryService.getAllCategory(pageNo, pageSize));
    }

    @PostMapping("/new")
    public ResponseEntity<CategoryGetDetailDto> addNewCategory(@RequestParam(name = "categoryName") String categoryName) {
        return ResponseEntity.ok(categoryService.addNewCategory(categoryName));
    }

    @PatchMapping("/{categoryId}/rename")
    public ResponseEntity<Void> rename(@PathVariable(value = "categoryId") Long categoryId,
                                       @RequestParam(value = "categoryName") String categoryName) {
        categoryService.renameCategory(categoryId, categoryName);
        return ResponseEntity.noContent().build();
    }

}
