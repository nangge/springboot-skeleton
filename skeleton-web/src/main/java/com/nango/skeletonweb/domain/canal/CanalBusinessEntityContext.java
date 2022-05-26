package com.nango.skeletonweb.domain.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author nango
 * @package com.nango.skeletonweb.domain.canalDataSyncBusiness
 * @class CanalBusinessEntityContext
 * @date 2022/5/10 21:34
 * @description canal拉取entity聚合体
 */
@Data
@Accessors(chain = true)
public class CanalBusinessEntityContext {

    /**
     * 库名
     */
    private String schemaName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 事件类型 update|insert|delete
     */
    private CanalEntry.EventType eventType;

    /**
     * 表字段
     */
    private List<List<CanalEntry.Column>> columnsList;
}
