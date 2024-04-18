package com.rymcu.tenon.service.impl;

import com.rymcu.tenon.core.service.AbstractService;
import com.rymcu.tenon.entity.Role;
import com.rymcu.tenon.mapper.RoleMapper;
import com.rymcu.tenon.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2024/4/13 22:06.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.service.impl
 */
@Service
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRolesByIdUser(Long idUser) {
        return roleMapper.selectRolesByIdUser(idUser);
    }

    @Override
    public Boolean postRole(Role role) {
        return roleMapper.insertSelective(role) > 0;
    }
}
