package com.rymcu.tenon.service.impl;

import com.rymcu.tenon.core.service.AbstractService;
import com.rymcu.tenon.entity.Menu;
import com.rymcu.tenon.mapper.MenuMapper;
import com.rymcu.tenon.model.Link;
import com.rymcu.tenon.model.MenuSearch;
import com.rymcu.tenon.service.MenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        List<Menu> menus = menuMapper.selectMenuListByLabelAndParentId(search.getQuery(), search.getParentId());
        List<Link> links = new ArrayList<>();
        for (Menu menu : menus) {
            Link link = new Link();
            link.setId(menu.getIdMenu());
            link.setLabel(menu.getLabel());
            link.setParentId(menu.getParentId());
            link.setIcon(menu.getIcon());
            link.setSortNo(menu.getSortNo());
            link.setStatus(menu.getStatus());
            MenuSearch menuSearch = new MenuSearch();
            menuSearch.setParentId(menu.getIdMenu());
            link.setChildren(findMenus(menuSearch));
            links.add(link);
        }
        return links;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveMenu(Menu menu) {
        Menu oldMenu = menuMapper.selectByPrimaryKey(menu.getIdMenu());
        if (Objects.nonNull(oldMenu)) {
            oldMenu.setLabel(menu.getLabel());
            oldMenu.setPermission(menu.getPermission());
            oldMenu.setIcon(menu.getIcon());
            oldMenu.setHref(menu.getHref());
            oldMenu.setStatus(menu.getStatus());
            oldMenu.setMenuType(menu.getMenuType());
            oldMenu.setSortNo(menu.getSortNo());
            oldMenu.setParentId(menu.getParentId());
            oldMenu.setUpdatedTime(menu.getUpdatedTime());
            return menuMapper.updateByPrimaryKeySelective(oldMenu) > 0;
        }
        menu.setCreatedTime(new Date());
        return menuMapper.insertSelective(menu) > 0;
    }

    @Override
    public List<Link> findChildrenMenus(MenuSearch search) {
        List<Menu> menus = menuMapper.selectMenuListByLabelAndParentId(search.getQuery(), search.getParentId());
        List<Link> links = new ArrayList<>();
        for (Menu menu : menus) {
            links.add(convertLink(menu));
        }
        return links;
    }

    @Override
    public Boolean updateStatus(Long idMenu, Integer status) {
        return menuMapper.updateStatusByIdMenu(idMenu, status) > 0;
    }

    @Override
    public Boolean updateDelFlag(Long idMenu, Integer delFlag) {
        return menuMapper.updateDelFlag(idMenu, delFlag) > 0;
    }

    private List<Link> findLinkTreeMode(Long idUser, long parentId) {
        List<Menu> menus = menuMapper.selectMenuListByIdUserAndParentId(idUser, parentId);
        List<Link> links = new ArrayList<>();
        for (Menu menu : menus) {
            Link link = convertLink(menu);
            link.setChildren(findLinkTreeMode(idUser, menu.getIdMenu()));
            links.add(link);
        }
        return links;
    }

    private static Link convertLink(Menu menu) {
        Link link = new Link();
        link.setId(menu.getIdMenu());
        link.setLabel(menu.getLabel());
        link.setParentId(menu.getParentId());
        link.setTo(menu.getHref());
        link.setIcon(menu.getIcon());
        link.setSortNo(menu.getSortNo());
        link.setStatus(menu.getStatus());
        return link;
    }
}
