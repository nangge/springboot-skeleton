package com.nango.skeletonweb.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author nango
 * @Date 2021/09/03
 * @Description 基础模型
 */
@Data
public class BaseModel implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建日期 - 现在时表示主动创建
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间 - 过去分词表示被动更新
     */
    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyTime;

    /**
     * '是否删除  0：未删除  1：以删除'
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @TableLogic
    private Integer delFlag = 0;

}
