package com.rymcu.tenon.service.impl;

import com.rymcu.tenon.core.service.AbstractService;
import com.rymcu.tenon.entity.Menu;
import com.rymcu.tenon.mapper.MenuMapper;
import com.rymcu.tenon.model.Link;
import com.rymcu.tenon.model.MenuSearch;
import com.rymcu.tenon.service.MenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2024/4/17 9:49.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.service.impl
 */
@Service
public class MenuServiceImpl extends AbstractService<Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findMenusByIdRole(Long idRole) {
        return menuMapper.selectMenuListByIdRole(idRole);
    }

    @Override
    public List<Link> findLinksByIdUser(Long idUser) {
        return findLinkTreeMode(idUser, 0L);
    }

    @Override
    public List<Link> findMenus(MenuSearch search) {
        List<Menu> menus = menuMapper.selectMenuListByParentId(search.getParentId());
        List<Link> links = new ArrayList<>();
        for (Menu menu : menus) {
            Link link = new Link();
            link.setId(menu.getIdMenu());
            link.setLabel(menu.getLabel());
            link.setParentId(menu.getParentId());
            link.setTo(menu.getHref());
            link.setIcon(menu.getIcon());
            MenuSearch menuSearch = new MenuSearch();
            menuSearch.setParentId(menu.getIdMenu());
            link.setChildren(findMenus(menuSearch));
            links.add(link);
        }
        return links;
    }

    private List<Link> findLinkTreeMode(Long idUser, long parentId) {
        List<Menu> menus = menuMapper.selectMenuListByIdUserAndParentId(idUser, parentId);
        List<Link> links = new ArrayList<>();
        for (Menu menu : menus) {
            Link link = new Link();
            link.setId(menu.getIdMenu());
            link.setLabel(menu.getLabel());
            link.setParentId(menu.getParentId());
            link.setTo(menu.getHref());
            link.setIcon(menu.getIcon());
            link.setChildren(findLinkTreeMode(idUser, menu.getIdMenu()));
            links.add(link);
        }
        return links;
    }
}
