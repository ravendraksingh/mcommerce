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
public class PriceInfo extends BaseEntity {
    private Double list;
    private Double retail;
    private Double savings;
}
