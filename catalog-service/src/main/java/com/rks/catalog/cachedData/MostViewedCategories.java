package com.rks.catalog.cachedData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MostViewedCategories {

    private static final Logger log = LoggerFactory.getLogger(MostViewedCategories.class);
    private Map<String, Integer> categoryAndViewCount;

    public MostViewedCategories() {
        categoryAndViewCount = new HashMap<>();
    }

    public void addViewCount(String categoryName, int count) {
        if (!categoryAndViewCount.containsKey(categoryName)) {
            categoryAndViewCount.put(categoryName, count);
        } else {
            categoryAndViewCount.put(categoryName, categoryAndViewCount.get(categoryName)+count);
        }
    }

    @Override
    public String toString() {
        return "MostViewedCategories{" +
                "categoryAndViewCount=" + categoryAndViewCount +
                '}';
    }
}
