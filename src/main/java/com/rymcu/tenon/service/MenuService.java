package com.rymcu.tenon.service;

import com.rymcu.tenon.core.service.Service;
import com.rymcu.tenon.entity.Menu;
import com.rymcu.tenon.model.Link;

import java.util.List;

/**
 * Created on 2024/4/17 9:49.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.service
 */
public interface MenuService extends Service<Menu> {
    List<Menu> findMenusByIdRole(Long idRole);

    List<Link> findLinksByIdUser(Long idUser);
}
