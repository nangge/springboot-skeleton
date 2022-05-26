package com.nango.skeletonweb.interfaces;

import com.nango.skeletonweb.domain.entity.ProductDO;
import com.nango.skeletonweb.domain.entity.ProductNewDO;
import com.nango.skeletonweb.domain.service.ProductNewService;
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
    private ProductNewService productNewService;

    @RequestMapping("product/ds1/getOne")
    public ProductDO getProductFromDS1() {
        return productServiceImpl.getById(1343104512895377409L);
    }

    @RequestMapping("product/ds0/getOne")
    public ProductNewDO getProductFromDS0() {
        return productNewService.getById("1343104512895377409");
    }

    @RequestMapping("product/save")
    public boolean  save() {
        ArrayList<ProductNewDO> productDOS = new ArrayList<>();
        for (int i=0;i<1;i++) {
            ProductNewDO productDO = new ProductNewDO();
            productDO.setCode(Long.valueOf(i));
            productDO.setName("商品" + i);
            productDO.setPrice(i);
            productDOS.add(productDO);
        }
        return productNewService.saveBatch(productDOS);
    }
}
