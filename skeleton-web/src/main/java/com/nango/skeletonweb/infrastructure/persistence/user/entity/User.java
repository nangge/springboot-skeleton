package com.nango.skeletonweb.infrastructure.persistence.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nango.skeletonweb.infrastructure.persistence.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author nango
 * @since 2021-09-03
 */
@Data
  @EqualsAndHashCode(callSuper = true)
    @TableName("t_user")
public class User extends BaseModel {

    private static final long serialVersionUID = 1L;

      /**
     * 名称
     */
      private String name;

      /**
     * 城市
     */
      private Integer cityId;

      /**
     * 性别
     */
      private Boolean sex;

      /**
     * 电话
     */
      private String phone;

      /**
     * 邮箱
     */
      private String email;

      /**
     * 密码
     */
      private String password;


}
