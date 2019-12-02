package com.rks.catalog.models.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShippingInfo {
    private int weight;
    private DimensionInfo dimensions;
}
