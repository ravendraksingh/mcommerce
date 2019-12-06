package com.rks.catalog.cachedData;

import com.rks.catalog.models.BaseEntity;

import java.util.Map;

public class CategoryTemp extends BaseEntity {
    private String id;
    private String name;
    private String description;
    private Map<String, String> attr;

    public CategoryTemp() {
    }

    public CategoryTemp(String id, String name, String description, Map<String, String> attr) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.attr = attr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
}
