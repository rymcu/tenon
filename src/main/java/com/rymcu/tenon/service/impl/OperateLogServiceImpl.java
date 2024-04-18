package com.rymcu.tenon.service.impl;

import com.rymcu.tenon.core.service.AbstractService;
import com.rymcu.tenon.model.OperateLogSearch;
import com.rymcu.tenon.entity.OperateLog;
import com.rymcu.tenon.mapper.OperateLogMapper;
import com.rymcu.tenon.service.OperateLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2024/3/13 9:48.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.service.impl
 */
@Service
public class OperateLogServiceImpl extends AbstractService<OperateLog> implements OperateLogService {

    @Resource
    private OperateLogMapper operateLogMapper;

    @Override
    public List<OperateLog> findOperateLogs(OperateLogSearch operateLogSearch) {
        return operateLogMapper.selectOperateLogs(operateLogSearch.getBizNo(), operateLogSearch.getType(), operateLogSearch.getSubType(), operateLogSearch.getStartDate(), operateLogSearch.getEndDate());
    }
}
