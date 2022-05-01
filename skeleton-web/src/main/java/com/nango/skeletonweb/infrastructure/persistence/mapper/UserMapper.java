package com.nango.skeletonweb.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nango.skeletonweb.domain.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author wyn
 * @Date 2020/11/4
 * @Description TODO
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
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
