package com.rks.catalog.bootstrap;

import com.rks.catalog.models.category.Category;
import com.rks.catalog.models.product.*;
import com.rks.catalog.repositories.CategoryRepository;
import com.rks.catalog.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    /*@Autowired
    private CacheManager cacheManager;
*/
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryReposMongo;

    @Override
    public void run(String... args) throws Exception {
        deleteAllProducts();
        createFewProducts();
        listAllProducts();
        //createFewCategories();
        //clearAllCache();
    }

    /*private void clearAllCache() {
        cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());
    }*/

    private void createFewCategories() {
        Category c1 = Category.builder().name("Electronics").description("Electronic Items").build();
        Map attr = new HashMap();
        attr.put("onSale", "yes");
        attr.put("promo-code", Arrays.asList(new String[]{"DHOOM-2020", "CLOUD-9", "ANNIV", "COBOL-45", "AREA-63"}));
        c1.setAttr(attr);
        categoryReposMongo.save(c1);
    }

    private void listAllProducts() {
        List<Product> productList = productRepository.findByType("Coffee-Mug");
        productList.forEach(product -> {
            System.out.println(product);
        });
    }

    private void deleteAllProducts() {
        productRepository.deleteAll();
    }

    private void createFewProducts() {
        PriceInfo priceInfo = PriceInfo.builder().list(500d).retail(450d).savings(50d).build();
        DimensionInfo dimensionDetails = DimensionInfo.builder().width(10).height(10).depth(1).build();
        ShippingInfo shippingDetails = ShippingInfo.builder().weight(5).dimensions(dimensionDetails).build();

        Product newProduct = Product.builder().type("Coffee-Mug").sku("CM-01-R").name("Coffee mug model 01 red")
                .description("A great coffee mug")
                .shipping(shippingDetails)
                .pricing(priceInfo)
                .build();

        DetailInfo detailInfo = new DetailInfo();

        Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("title", "A Love Supreme [Original Recording Reissued]");
        infoMap.put("artist", "John Coltrane");
        infoMap.put("genre", new HashSet(Arrays.asList(new String[] {"Jazz", "General"})));
        infoMap.put("tracks", new ArrayList<>(Arrays.asList(new String[] {
                "A Love Supreme Part I: Acknowledgement",
                "A Love Supreme Part II - Resolution",
                "A Love Supreme, Part III: Pursuance",
                "A Love Supreme, Part IV-Psalm"
        })));

        detailInfo.setInfoList(infoMap);

        Product p1 = Product.builder().sku("aa-kk-vol-1").type("Audio Album").name("Kishore Kumar Vol-1")
                .description("By kishore kumar")
                .shipping(shippingDetails)
                .pricing(PriceInfo.builder().list(500d).retail(480d).savings(20d).build())
                //.details(detailInfo)
                .build();
        productRepository.save(p1);

        Product p2 = Product.builder().sku("aa-kk-vol-2").type("Audio Album").name("Kishore Kumar Vol-2")
                .description("By kishore kumar")
                .shipping(shippingDetails)
                .pricing(PriceInfo.builder().list(500d).retail(450d).savings(50d).build())
                //.details(detailInfo)
                .build();
        productRepository.save(p2);

        Product p3 = Product.builder().sku("aa-kk-vol-3").type("Audio Album").name("Kishore Kumar Vol-3")
                .description("By kishore kumar")
                .shipping(shippingDetails)
                .pricing(PriceInfo.builder().list(500d).retail(400d).savings(100d).build())
                //.details(detailInfo)
                .build();
        productRepository.save(p3);

        Product p4 = Product.builder().sku("aa-kk-vol-4").type("Audio Album").name("Kishore Kumar Vol-4")
                .description("By kishore kumar")
                .shipping(shippingDetails)
                .pricing(PriceInfo.builder().list(800d).retail(800d).savings(0d).build())
                //.details(detailInfo)
                .build();
        productRepository.save(p4);
    }
}
