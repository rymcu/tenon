package com.rymcu.tenon.model;

import lombok.Data;

import java.util.Set;

/**
 * Created on 2024/5/4 13:34.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.model
 */
@Data
public class BindRoleMenuInfo {

    private Long idRole;

    private Set<Long> idMenus;
}
