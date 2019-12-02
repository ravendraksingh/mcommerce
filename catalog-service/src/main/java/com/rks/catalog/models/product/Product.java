package com.rks.catalog.models.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "products")
public class Product {
    private String id;
    private String type;
    private String sku;
    private String title;
    private String description;
    private ShippingInfo shipping;
    private PriceInfo pricing;
    private DetailInfo details;
}





