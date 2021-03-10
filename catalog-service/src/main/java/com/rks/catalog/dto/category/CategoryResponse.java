package com.rks.catalog.dto.category;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@Data
public class CategoryResponse {
    private String id;
    private String name;
    private String description;
    private Map<String, Object> attr;
}
