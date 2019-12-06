package com.rks.catalog.controllers;

import com.rks.catalog.cart.Cart;
import com.rks.catalog.models.product.Product;
import com.rks.catalog.repositories.ProductRepository;
import com.rks.catalog.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/catalog/ext")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    // session scoped POJO
    @Autowired
    private Cart shoppingCart;

    @GetMapping("/v1/products/{productId}")
    public Product getProductById(@PathVariable("productId") String productId) {
        return productService.get(productId);
    }

    @PostMapping("/v1/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody Product product) {
        productService.add(product);
    }

    @GetMapping("/v1/products/getProductsByCategoryName/{categoryName}")
    public List<Product> getProductsByCategoryName(@PathVariable("categoryName") String categoryName) {
        return productService.getProductsByCategoryName(categoryName);
    }

    @DeleteMapping("/v1/products")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(String id) {
        productService.delete(id);
    }

    @PostMapping("/v1/cart/addToCart")
    public void addToCart(@RequestParam(value = "sku") String sku,
                          @RequestParam(value = "quantity") int quantity) {
        shoppingCart.addItem(sku, quantity);
        log.info(shoppingCart.toString());
    }

    @GetMapping("/v1/products/search")
    public List<Product> searchProducts(HttpServletRequest request) throws Exception {
        return productService.searchProducts(request.getParameterMap());
    }

}
