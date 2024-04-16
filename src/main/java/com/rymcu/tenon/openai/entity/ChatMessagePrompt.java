package com.rymcu.tenon.openai.entity;

import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.Data;

import java.util.List;

/**
 * Created on 2023/7/16 14:52.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.openai.entity
 */
@Data
public class ChatMessagePrompt {

    String model;

    List<ChatMessage> messages;


}
