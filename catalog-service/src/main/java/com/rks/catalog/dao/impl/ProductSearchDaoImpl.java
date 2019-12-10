package com.rks.catalog.dao.impl;

import com.rks.catalog.dao.ProductSearchDao;
import com.rks.catalog.models.product.Product;
import com.rks.mcommon.exception.BadRequestException;
import com.rks.mcommon.exception.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import com.rks.mcommon.constants.CommonConstants.*;
import com.rks.mcommon.constants.CommonErrorCodeConstants.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.rks.catalog.constants.Constants.PRODUCT_NOT_FOUND_ERR_MSG;
import static com.rks.mcommon.constants.CommonConstants.FAILED;
import static com.rks.mcommon.constants.CommonErrorCodeConstants.BAD_REQUEST_ERROR_CODE;
import static com.rks.mcommon.constants.CommonErrorCodeConstants.NOT_FOUND_ERROR_CODE;

@Component
public class ProductSearchDaoImpl implements ProductSearchDao {

    private static final Logger log = LoggerFactory.getLogger(ProductSearchDaoImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Product> searchProducts(Map<String, String[]> searchCriteriaMap) {

        List<Product> productList;
        Query query = new Query();

        log.info("query before modification --> {}", query);

        if (searchCriteriaMap != null) {
            for (String key : searchCriteriaMap.keySet()) {
                String value = searchCriteriaMap.get(key)[0];
                log.info("key --> {} Value -> {}", key, value);

                if (value != null && value.contains(":")) {
                    String[] temp = value.split(":");

                    if (temp.length != 2) {
                        throw new BadRequestException(FAILED, BAD_REQUEST_ERROR_CODE, "Bad request");
                    }

                    String operator = temp[0];
                    Double doubleValue = 0d;
                    String strValue = "";

                    if (StringUtils.equalsAnyIgnoreCase(operator,"lte", "gte")) {
                        if (!NumberUtils.isCreatable(temp[1])) {
                            throw new BadRequestException(FAILED, BAD_REQUEST_ERROR_CODE, "Bad request");
                        }
                        doubleValue = NumberUtils.createDouble(temp[1]);
                    } else if (StringUtils.equalsAnyIgnoreCase(operator,"regex")) {
                        strValue = temp[1];
                    }

                    switch (operator) {
                        case "regex":
                            Pattern pattern = Pattern.compile(strValue, Pattern.CASE_INSENSITIVE);
                            log.info("pattern --> {}", pattern);
                            query.addCriteria(Criteria.where(key).regex(pattern));
                            break;
                        case "lte":
                            query.addCriteria(Criteria.where(key).lte(doubleValue));
                            break;
                        case "gte":
                            query.addCriteria(Criteria.where(key).gte(doubleValue));
                            break;
                        case "eq":
                            query.addCriteria(Criteria.where(key).is(doubleValue));
                            break;
                    }
                }
            }
        }
        log.info("query --> {}", query);
        query.with(Sort.by(Sort.Direction.DESC, "sku"));
        productList = mongoTemplate.find(query, Product.class);

        if (productList == null) {
            throw new NotFoundException(FAILED, NOT_FOUND_ERROR_CODE, PRODUCT_NOT_FOUND_ERR_MSG);
        }
        return productList;
    }
}
