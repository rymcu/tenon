package com.rymcu.tenon.mapper;

import com.rymcu.tenon.core.mapper.Mapper;
import com.rymcu.tenon.entity.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created on 2024/9/22 19:58.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.mapper
 */
public interface DictMapper extends Mapper<Dict> {
    List<Dict> selectDictList(@Param("q") String q, @Param("dictTypeCode") String dictTypeCode, @Param("status") Integer status);

    int updateStatus(@Param("idDict") Long idDict, @Param("status") Integer status);

    int updateDelFlag(@Param("idDict") Long idDict);
}
