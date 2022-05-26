package com.nango.skeletonweb.application.canalDataSyncBusiness;

import com.nango.skeletonweb.domain.canal.CanalBusinessEntityContext;
import com.nango.skeletonweb.domain.entity.ProductNewDO;
import com.nango.skeletonweb.infrastructure.persistence.mapper.ProductNewMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author nango
 * @package com.nango.skeletonweb.application.canalDataSyncBusiness
 * @class ProductCanalDataConsumer
 * @date 2022/5/17 16:35
 * @description 商品数据同步消费者
 */
@Service
@Slf4j
public class ProductSyncDataServiceImpl
        extends AbstractCanalConsumerService {
    @Resource
    private ProductNewMapper productNewMapper;

    /**
     * 获取同步表名称
     *
     * @return
     */
    @Override
    public String getTableName() {
        return "t_product";
    }

    /**
     * 获取新表名称
     *
     * @return
     */
    @Override
    public String getTableNewName() {
        return "t_product_new";
    }

    /**
     * 执行sql
     * @param productEntityContextList
     */
    @Override
    public void execSql(List<CanalBusinessEntityContext> productEntityContextList) {
        List<String> sqlList = this.dealDumpLog(productEntityContextList);

        if (!CollectionUtils.isEmpty(sqlList)) {
            productNewMapper.execSQL(StringUtils.join(sqlList, ";"));
        }
    }

    /**
     * 记录是否已存在
     *
     * @param id
     * @return
     */
    @Override
    public boolean isExist(String id) {
        ProductNewDO productNewDO = productNewMapper.selectById(Long.valueOf(id));
        return Objects.nonNull(productNewDO);
    }
}
