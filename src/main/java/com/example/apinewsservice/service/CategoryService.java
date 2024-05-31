package com.example.apinewsservice.service;

import com.example.apinewsservice.exception.EntityNotFoundException;
import com.example.apinewsservice.model.Category;
import com.example.apinewsservice.repository.CategoryRepository;
import com.example.apinewsservice.util.BeanUtils;
import com.example.apinewsservice.web.model.CategoryFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll(CategoryFilter filter) {
        return categoryRepository.findAll(PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Категория с id {0}, не найдена.", id)));
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Category category) {
        Category existedCategory = findById(category.getId());
        BeanUtils.copyNonNullProperties(category, existedCategory);
        return categoryRepository.save(existedCategory);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
