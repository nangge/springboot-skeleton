package com.none.sharding.interfaces;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.none.sharding.domain.entity.AddressDO;
import com.none.sharding.domain.service.AddressService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
            addressDO.setCreateTime(LocalDateTime.now());
            addressService.save(addressDO);
        }

        return "success";
    }

    @RequestMapping("/address/get")
    public AddressDO get(
            @RequestParam Long id,
            @RequestParam String dateTime) {
        QueryWrapper<AddressDO> addressDOQueryWrapper = new QueryWrapper<>();
        ArrayList<Long> ids = new ArrayList<>();
        ids.add((long)98);
        ids.add(id);
        addressDOQueryWrapper.lambda()
                .in(AddressDO::getId, ids)
                .eq(AddressDO::getCode, 123)
                .eq(AddressDO::getCreateTime, dateTime);
        return addressService.getOne(addressDOQueryWrapper);
    }

}
