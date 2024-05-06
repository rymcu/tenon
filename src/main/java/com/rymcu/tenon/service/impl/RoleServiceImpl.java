package com.rymcu.tenon.service.impl;

import com.rymcu.tenon.core.service.AbstractService;
import com.rymcu.tenon.entity.Role;
import com.rymcu.tenon.mapper.RoleMapper;
import com.rymcu.tenon.model.BindRoleMenuInfo;
import com.rymcu.tenon.model.RoleSearch;
import com.rymcu.tenon.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    @Transactional(rollbackFor = Exception.class)
    public Boolean postRole(Role role) {
        Role oldRole = roleMapper.selectByPrimaryKey(role.getIdRole());
        if (Objects.nonNull(oldRole)) {
            oldRole.setLabel(role.getLabel());
            oldRole.setPermission(role.getPermission());
            oldRole.setStatus(role.getStatus());
            oldRole.setUpdatedTime(new Date());
            return roleMapper.updateByPrimaryKeySelective(oldRole) > 0;
        }
        role.setCreatedTime(new Date());
        return roleMapper.insertSelective(role) > 0;
    }

    @Override
    public List<Role> findRoles(RoleSearch search) {
        return roleMapper.selectRoles(search.getLabel(), search.getStartDate(), search.getEndDate(), search.getOrder(), search.getSort());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean bindRoleMenu(BindRoleMenuInfo bindRoleMenuInfo) {
        int num = 0;
        for (Long idMenu : bindRoleMenuInfo.getIdMenus()) {
            num += roleMapper.insertRoleMenu(bindRoleMenuInfo.getIdRole(), idMenu);
        }
        return num == bindRoleMenuInfo.getIdMenus().size();
    }
}
