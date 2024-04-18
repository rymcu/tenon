package com.rymcu.tenon.service;

import com.rymcu.tenon.core.service.Service;
import com.rymcu.tenon.entity.Role;

import java.util.List;

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
}
