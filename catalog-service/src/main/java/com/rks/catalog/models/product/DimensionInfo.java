package com.rks.catalog.models.product;

import com.rks.catalog.models.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DimensionInfo extends BaseEntity {
    private int width;
    private int height;
    private int depth;
}
