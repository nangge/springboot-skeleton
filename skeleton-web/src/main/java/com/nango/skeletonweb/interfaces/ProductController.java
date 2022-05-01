package com.nango.skeletonweb.interfaces;

import com.nango.skeletonweb.domain.entity.ProductDO;
import com.nango.skeletonweb.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

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

    @RequestMapping("product/save")
    public boolean  save() {
        ArrayList<ProductDO> productDOS = new ArrayList<>();
        for (int i=0;i<10000;i++) {
            ProductDO productDO = new ProductDO();
            productDO.setCode(Long.valueOf(i));
            productDO.setName("商品" + i);
            productDO.setPrice(i);
            productDOS.add(productDO);
        }
        return productNewServiceImpl.saveBatch(productDOS);
    }
}
