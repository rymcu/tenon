package com.rymcu.tenon.entity;

import com.alibaba.fastjson.annotation.JSONField;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created on 2024/4/13 15:01.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.entity
 */
@Data
@Table(name = "tenon_user")
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long idUser;
    /**
     * 登录账号
     */
    private String account;

    /**
     * 密码
     */
    @JSONField(serialize = false)
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 最后登录时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    /**
     * 最后在线时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastOnlineTime;
}
