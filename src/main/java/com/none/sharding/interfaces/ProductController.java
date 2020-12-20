package com.none.sharding.interfaces;

import com.none.sharding.domain.entity.ProductDO;
import com.none.sharding.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author nango
 * @Date 2020/12/20
 * @Description 商品
 */
@RestController
public class ProductController {
    @Autowired
    private ProductService productServiceImpl;

    @Autowired
    private ProductService productNewServiceImpl;

    @RequestMapping("product/ds1/getOne")
    public ProductDO getProductFromDS1() {
        return productServiceImpl.getById(1);
    }

    @RequestMapping("product/ds0/getOne")
    public ProductDO getProductFromDS0() {
        return productNewServiceImpl.getById(1);
    }
}
