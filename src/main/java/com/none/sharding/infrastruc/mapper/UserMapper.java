package com.none.sharding.infrastruc.mapper;

import com.none.sharding.domain.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author wyn
 * @Date 2020/11/4
 * @Description TODO
 */
@Mapper
public interface UserMapper {
    /**
     * 保存
     */
    void save(UserDO userDO);

    /**
     * 查询
     * @param id
     * @return
     */
    UserDO get(Long id);

    UserDO getBySex(Integer sex);
}
