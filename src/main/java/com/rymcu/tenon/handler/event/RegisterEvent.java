package com.rymcu.tenon.handler.event;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created on 2024/4/18 8:09.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.handler.event
 */
@Data
@AllArgsConstructor
public class RegisterEvent {

    private Long idUser;

    private String account;

}
