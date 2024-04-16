package com.rymcu.tenon.openai.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * Created on 2023/5/26 11:24.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.openai.service
 */

public interface SseService {
    SseEmitter connect(Long idUser);

    boolean send(Long idUser, String content);

    void close(Long idUser);
}
