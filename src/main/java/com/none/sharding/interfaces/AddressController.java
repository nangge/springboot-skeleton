package com.none.sharding.interfaces;

import com.none.sharding.domain.entity.AddressDO;
import com.none.sharding.domain.service.AddressService;
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
    private AddressService addressService;

    @RequestMapping("/address/save")
    public String save() {
        for (int i = 0; i <10 ; i++) {
            AddressDO addressDO =new AddressDO();
            addressDO.setCode("code_"+i);
            addressDO.setName("name_"+i);
            addressDO.setPid(i+"");
            addressDO.setType(0);
            addressDO.setLit(i%2==0?1:2);
            addressService.save(addressDO);
        }

        return "success";
    }

}
