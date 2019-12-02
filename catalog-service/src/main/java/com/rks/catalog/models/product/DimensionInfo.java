package com.rks.catalog.models.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DimensionInfo {
    private int width;
    private int height;
    private int depth;
}
