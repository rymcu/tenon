package com.rymcu.tenon.model;


import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author ronger
 */
@Data
public class TokenUser {

    private Long idUser;

    private String account;

    private String token;

    private String refreshToken;

}
