package com.rymcu.tenon.service;

import com.rymcu.tenon.core.service.Service;
import com.rymcu.tenon.model.OperateLogSearch;
import com.rymcu.tenon.entity.OperateLog;

import java.util.List;

/**
 * Created on 2024/3/13 9:48.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.service
 */
public interface OperateLogService extends Service<OperateLog> {
    List<OperateLog> findOperateLogs(OperateLogSearch operateLogSearch);
}
