package com.rymcu.tenon.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ronger
 */
@Data
@Table(name = "tenon_role")
public class Role implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long idRole;

    /**
     * 角色名称
     */
    private String label;

    /**
     * 角色权限
     */
    private String permission;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    /**
     * 更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
}
