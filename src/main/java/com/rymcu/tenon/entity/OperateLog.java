package com.rymcu.tenon.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created on 2024/2/27 14:16.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.entity
 */
@Data
@Table(name = "tenon_operate_log")
public class OperateLog {

    /**
     * 日志主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long idOperateLog;
    /**
     * 链路追踪编号
     * <p>
     * 一般来说，通过链路追踪编号，可以将访问日志，错误日志，链路追踪日志，logger 打印日志等，结合在一起，从而进行排错。
     */
    private String traceId;

    private String bizNo;

    private String tenant;
    /**
     * 操作者
     */
    private String operator;
    /**
     * 操作模块
     */
    private String type;

    /**
     * 操作分类
     */
    private String subType;
    /**
     * 操作内容，记录整个操作的明细
     * 例如说，修改编号为 1 的用户信息，将性别从男改成女。
     */
    private String content;
    /**
     * 拓展字段，有些复杂的业务，需要记录一些字段
     * 例如说，记录订单编号，则可以添加 key 为 "orderId"，value 为订单编号
     */
    private String extra;

    private Boolean fail;
    /**
     * 请求方法名
     */
    private String requestMethod;
    /**
     * 请求地址
     */
    private String requestUrl;
    /**
     * 用户 IP
     */
    private String userIp;
    /**
     * 浏览器 UA
     */
    private String userAgent;

    /**
     * Java 方法名
     */
    private String javaMethod;

    /**
     * 开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

}
