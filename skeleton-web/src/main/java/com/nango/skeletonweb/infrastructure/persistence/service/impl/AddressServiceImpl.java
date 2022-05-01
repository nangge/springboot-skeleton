package com.nango.skeletonweb.infrastructure.persistence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nango.skeletonweb.domain.entity.AddressDO;
import com.nango.skeletonweb.domain.service.AddressService;
import com.nango.skeletonweb.infrastructure.persistence.mapper.AddressMapper;
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
