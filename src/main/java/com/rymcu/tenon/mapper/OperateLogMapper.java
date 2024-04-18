package com.rymcu.tenon.mapper;

import com.rymcu.tenon.core.mapper.Mapper;
import com.rymcu.tenon.entity.OperateLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created on 2024/4/13 21:20.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.mapper
 */
public interface OperateLogMapper extends Mapper<OperateLog> {
    List<OperateLog> selectOperateLogs(@Param("bizNo") String bizNo, @Param("type") String type, @Param("subType") String subType, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
