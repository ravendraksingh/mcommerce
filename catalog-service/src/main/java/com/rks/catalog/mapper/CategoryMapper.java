package com.rks.catalog.mapper;

import com.rks.catalog.dto.category.CategoryResponse;
import com.rks.catalog.models.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    CategoryResponse categoryToCategoryResponse(Category category) ;
    Category CategoryResponseToCategory(CategoryResponse categoryResponse);
}
