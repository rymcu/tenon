package com.rymcu.tenon.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created on 2024/4/17 8:56.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.entity
 */
@Data
@Table(name = "tenon_menu")
public class Menu {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long idMenu;

    /**
     * 菜单名称
     */
    private String label;

    /**
     * 菜单权限
     */
    private String permission;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单链接
     */
    private String href;

    /**
     * 状态
     */
    private Integer status;
    /**
     * 类型
     */
    private Integer menuType;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 父级菜单主键
     */
    private Long parentId;

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
