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
public class ShippingInfo extends BaseEntity {
    private int weight;
    private DimensionInfo dimensions;
}
