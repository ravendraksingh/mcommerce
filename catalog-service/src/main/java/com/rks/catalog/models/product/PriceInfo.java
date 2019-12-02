package com.rks.catalog.models.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PriceInfo {
    private Double list;
    private Double retail;
    private Double savings;
}
