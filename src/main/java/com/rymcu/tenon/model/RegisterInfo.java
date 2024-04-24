package com.rymcu.tenon.model;

import lombok.Data;

/**
 * Created on 2024/4/24 10:08.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.model
 */
@Data
public class RegisterInfo {

    private String phone;

    private String email;

    private String nickname;

    private String password;

    private String code;

}
