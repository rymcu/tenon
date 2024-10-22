package com.rymcu.tenon.service;

import com.rymcu.tenon.core.service.Service;
import com.rymcu.tenon.entity.Role;
import com.rymcu.tenon.model.BindRoleMenuInfo;
import com.rymcu.tenon.model.RoleSearch;

import java.util.List;
import java.util.Set;

/**
 * Created on 2024/4/13 22:06.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.service
 */
public interface RoleService extends Service<Role> {
    List<Role> findRolesByIdUser(Long idUser);

    Boolean postRole(Role role);

    List<Role> findRoles(RoleSearch search);

    Boolean bindRoleMenu(BindRoleMenuInfo bindRoleMenuInfo);

    Set<Long> findRoleMenus(Long idRole);
}
