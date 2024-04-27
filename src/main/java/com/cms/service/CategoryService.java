package com.cms.service;

import com.cms.dto.CategoryDto;
import com.cms.dto.DtoMapper;
import com.cms.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(DtoMapper.CATEGORY_TO_DTO).collect(Collectors.toList());
    }
}
