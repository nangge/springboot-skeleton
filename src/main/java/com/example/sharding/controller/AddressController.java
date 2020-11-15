package com.example.sharding.controller;

import com.example.sharding.entity.Address;
import com.example.sharding.mapper.AddressMapper;
import com.example.sharding.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author wyn
 * @Date 2020/11/4
 * @Description TODO
 */
@RestController
public class AddressController {
    @Resource
    private AddressMapper addressMapper;

    @Resource
    private AddressService addressService;

    @RequestMapping("/address/save")
    public String save() {
        for (int i = 0; i <10 ; i++) {
            Address address=new Address();
            address.setCode("code_"+i);
            address.setName("name_"+i);
            address.setPid(i+"");
            address.setType(0);
            address.setLit(i%2==0?1:2);
            addressMapper.save(address);
        }

        return "success";
    }

    @RequestMapping("/address/get/{id}")
    public Address get(@PathVariable Long id) {
//        return addressService.getById(id);
        return addressMapper.get(id);
    }
}
