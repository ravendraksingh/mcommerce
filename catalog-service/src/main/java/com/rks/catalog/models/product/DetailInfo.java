package com.rks.catalog.models.product;

import com.rks.catalog.models.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class DetailInfo extends BaseEntity {
    private Map<String, Object> infoList;
}
