package com.rymcu.tenon.mapper;

import com.rymcu.tenon.core.mapper.Mapper;
import com.rymcu.tenon.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created on 2024/4/17 9:43.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.mapper
 */
public interface MenuMapper extends Mapper<Menu> {
    List<Menu> selectMenuListByIdRole(@Param("idRole") Long idRole);

    List<Menu> selectMenuListByIdUser(@Param("idUser") Long idUser);

    List<Menu> selectMenuListByIdUserAndParentId(@Param("idUser") Long idUser, @Param("parentId") Long parentId);
}
