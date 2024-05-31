package com.example.apinewsservice.web.controller;


import com.example.apinewsservice.mapper.CategoryMapper;
import com.example.apinewsservice.model.Category;
import com.example.apinewsservice.service.CategoryService;
import com.example.apinewsservice.web.model.CategoryCreateRequest;
import com.example.apinewsservice.web.model.CategoryFilter;
import com.example.apinewsservice.web.model.CategoryResponse;
import com.example.apinewsservice.web.model.CategoryResponseList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<CategoryResponseList> fidAll(@Valid CategoryFilter filter) {
        return ResponseEntity.ok(categoryMapper.categoryListToCategoryListResponse(categoryService.findAll(filter)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryMapper.categoryToResponse(categoryService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@RequestBody @Valid CategoryCreateRequest request) {
        Category newCategory = categoryService.save(categoryMapper.requestToCategory(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.categoryToResponse(newCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id,
                                                   @RequestBody @Valid CategoryCreateRequest request) {
        Category updatedCategory = categoryService.update(categoryMapper.requestToCategory(id, request));
        return ResponseEntity.ok(categoryMapper.categoryToResponse(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
