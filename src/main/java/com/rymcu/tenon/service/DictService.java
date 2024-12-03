package com.rymcu.tenon.service;

import com.rymcu.tenon.core.service.Service;
import com.rymcu.tenon.entity.Dict;
import com.rymcu.tenon.model.DictSearch;

import java.util.List;

/**
 * Created on 2024/9/22 20:04.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.service
 */
public interface DictService extends Service<Dict> {
    List<Dict> findDictList(DictSearch search);

    Boolean saveDict(Dict dict);

    Boolean updateStatus(Long idDict, Integer status);

    Boolean updateDelFlag(Long idDict, Integer delFlag);
}
