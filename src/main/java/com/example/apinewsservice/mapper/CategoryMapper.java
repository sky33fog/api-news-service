package com.example.apinewsservice.mapper;


import com.example.apinewsservice.model.Category;
import com.example.apinewsservice.web.model.CategoryCreateRequest;
import com.example.apinewsservice.web.model.CategoryResponse;
import com.example.apinewsservice.web.model.CategoryResponseList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category requestToCategory(CategoryCreateRequest request);

    @Mapping(source = "categoryId", target = "id")
    Category requestToCategory(Long categoryId, CategoryCreateRequest request);

    CategoryResponse categoryToResponse(Category category);

    default CategoryResponseList categoryListToCategoryListResponse(List<Category> categories) {
        CategoryResponseList response = new CategoryResponseList();

        response.setCategories(categories.stream().map(this::categoryToResponse).collect(Collectors.toList()));
        return response;
    }
}
