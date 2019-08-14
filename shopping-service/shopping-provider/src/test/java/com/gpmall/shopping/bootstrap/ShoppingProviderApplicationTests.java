package com.gpmall.shopping.bootstrap;

import com.gpmall.shopping.*;
import com.gpmall.shopping.dto.AddCartRequest;
import com.gpmall.shopping.dto.AllProductCateRequest;
import com.gpmall.shopping.dto.ProductDetailRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingProviderApplicationTests {

    @Autowired
    private ICartService cartService;


    @Test
    public void testCartService() throws IOException {
        AddCartRequest request = new AddCartRequest();
        request.setItemId(100023501L);
        request.setUserId(123L);
        cartService.addToCart(request);
        System.in.read();
    }

    @Autowired
    private IContentService contentService;

    @Test
    public void testContentService() throws IOException {
        contentService.queryNavList();
        System.in.read();
    }

    @Autowired
    private IHomeService homeService;
    @Test
    public void testHomeService() throws IOException {
        homeService.homepage();
        System.in.read();
    }

    @Autowired
    private IProductCateService productCateService;

    @Test
    public void testProductCateService() throws IOException {
        AllProductCateRequest request = new AllProductCateRequest();
        request.setSort("1");
        productCateService.getAllProductCate(request);
        System.in.read();
    }

    @Autowired
    private IProductService productService;

    @Test
    public void testProductService() throws IOException {
        ProductDetailRequest productDetailRequest = new ProductDetailRequest();
        productDetailRequest.setId(100023501L);
        productService.getProductDetail(productDetailRequest);

        // ----------------------------------------------------------------------------

//        productService.getRecommendGoods();

        System.in.read();
    }
}
