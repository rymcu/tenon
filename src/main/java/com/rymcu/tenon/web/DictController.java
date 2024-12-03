package com.rymcu.tenon.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rymcu.tenon.core.result.GlobalResult;
import com.rymcu.tenon.core.result.GlobalResultGenerator;
import com.rymcu.tenon.entity.Dict;
import com.rymcu.tenon.entity.User;
import com.rymcu.tenon.enumerate.DelFlag;
import com.rymcu.tenon.model.DictSearch;
import com.rymcu.tenon.service.DictService;
import com.rymcu.tenon.util.UserUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2024/9/22 20:21.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.web
 */
@RestController
@RequestMapping("/api/v1/admin/dict")
public class DictController {

    @Resource
    private DictService dictService;

    @GetMapping("/list")
    public GlobalResult<PageInfo<Dict>> dictList(DictSearch search) {
        PageHelper.startPage(search.getPageNum(), search.getPageSize());
        List<Dict> list = dictService.findDictList(search);
        PageInfo<Dict> pageInfo = new PageInfo<>(list);
        return GlobalResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/detail/{idDict}")
    public GlobalResult<Dict> dictDetail(@PathVariable Long idDict) {
        Dict dict = dictService.findById(String.valueOf(idDict));
        return GlobalResultGenerator.genSuccessResult(dict);
    }

    @PostMapping("/post")
    public GlobalResult<Boolean> addDict(@RequestBody Dict dict) {
        User user = UserUtils.getCurrentUserByToken();
        dict.setCreatedBy(user.getIdUser());
        Boolean flag = dictService.saveDict(dict);
        return GlobalResultGenerator.genSuccessResult(flag);
    }

    @PutMapping("/post")
    public GlobalResult<Boolean> updateDict(@RequestBody Dict dict) {
        User user = UserUtils.getCurrentUserByToken();
        dict.setUpdatedBy(user.getIdUser());
        Boolean flag = dictService.saveDict(dict);
        return GlobalResultGenerator.genSuccessResult(flag);
    }

    @PostMapping("/update-status")
    public GlobalResult<Boolean> updateStatus(@RequestBody Dict dict) {
        return GlobalResultGenerator.genSuccessResult(dictService.updateStatus(dict.getIdDict(), dict.getStatus()));
    }

    @DeleteMapping("/update-del-flag")
    public GlobalResult<Boolean> updateDelFlag( Long idDict) {
        return GlobalResultGenerator.genSuccessResult(dictService.updateDelFlag(idDict, DelFlag.DELETE.ordinal()));
    }

}
