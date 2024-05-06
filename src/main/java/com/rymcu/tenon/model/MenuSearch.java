package com.rymcu.tenon.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 2024/4/30 15:21.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.model
 */
@Getter
@Setter
public class MenuSearch extends BaseSearch {

    private Long parentId;

    private String label;

}
