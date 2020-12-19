package com.none.sharding.infrastruc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.none.sharding.domain.entity.AddressDO;
import com.none.sharding.domain.service.AddressService;
import com.none.sharding.infrastruc.mapper.AddressMapper;
import org.springframework.stereotype.Service;

/**
 * @Author wyn
 * @Date 2020/11/10
 * @Description TODO
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressDO>
        implements AddressService {
}
