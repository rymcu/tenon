package com.rymcu.tenon.mapper;

import com.rymcu.tenon.core.mapper.Mapper;
import com.rymcu.tenon.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created on 2024/4/13 22:06.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.mapper
 */
public interface RoleMapper extends Mapper<Role> {
    List<Role> selectRolesByIdUser(@Param("idUser") Long idUser);

    Role selectRoleByPermission(@Param("permission") String permission);

    List<Role> selectRoles(@Param("label") String label, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("order") String order, @Param("sort") String sort);

    int insertRoleMenu(@Param("idRole") Long idRole, @Param("idMenu") Long idMenu);

    Set<Long> selectRoleMenus(@Param("idRole") Long idRole);
}
