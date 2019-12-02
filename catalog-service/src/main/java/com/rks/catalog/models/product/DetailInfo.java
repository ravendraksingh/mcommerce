package com.rks.catalog.models.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
public class DetailInfo {
    private Map<String, Object> infoList;
}
