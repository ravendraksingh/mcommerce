package com.rks.catalog.models.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rks.catalog.models.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "categories")
public class Category extends BaseEntity {
    private String id;
    private String name;
    private String description;
    private Map<String, Object> attr;
}
