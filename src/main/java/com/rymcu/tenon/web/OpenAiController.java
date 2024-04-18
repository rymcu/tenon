package com.rymcu.tenon.web;

import com.rymcu.tenon.core.constant.ProjectConstant;
import com.rymcu.tenon.core.result.GlobalResult;
import com.rymcu.tenon.core.result.GlobalResultGenerator;
import com.rymcu.tenon.entity.User;
import com.rymcu.tenon.openai.entity.ChatMessagePrompt;
import com.rymcu.tenon.openai.service.OpenAiService;
import com.rymcu.tenon.openai.service.SseService;
import com.rymcu.tenon.util.Html2TextUtil;
import com.rymcu.tenon.util.UserUtils;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created on 2023/2/15 10:04.
 *
 * @author ronger
 * @email ronger-x@outlook.com
 * @desc : com.rymcu.tenon.openai
 */
@RestController
@RequestMapping("/api/v1/openai")
public class OpenAiController {
    @Resource
    private SseService sseService;

    @Value("${openai.api-key}")
    private String apiKey;

    @PostMapping("/chat")
    public GlobalResult chat(@RequestBody ChatMessagePrompt chatMessagePrompt) {
        List<ChatMessage> messages = chatMessagePrompt.getMessages();
        if (messages.isEmpty()) {
            throw new IllegalArgumentException("参数异常！");
        }
        User user = UserUtils.getCurrentUserByToken();
        Collections.reverse(messages);
        List<ChatMessage> list = new ArrayList<>(messages.size());
        if (messages.size() > 4) {
            messages = messages.subList(messages.size() - 4, messages.size());
        }
        if (messages.size() >= 4 && messages.size() % 4 == 0) {
            ChatMessage message = new ChatMessage("system", "简单总结一下你和用户的对话, 用作后续的上下文提示 prompt, 控制在 200 字内");
            list.add(message);
        }
        messages.forEach(chatMessageModel -> {
            ChatMessage message = new ChatMessage(chatMessageModel.getRole(), Html2TextUtil.getContent(chatMessageModel.getContent()));
            list.add(message);
        });
        return sendMessageStream(chatMessagePrompt.getModel(), user.getIdUser(), list);
    }

    @NotNull
    private GlobalResult sendMessageStream(String model, Long idUser, List<ChatMessage> list) {
        String openAIApiKey = System.getenv(ProjectConstant.OPENAI_API_KEY);
        if (StringUtils.isEmpty(openAIApiKey)) {
            openAIApiKey = apiKey;
        }
        OpenAiService service = new OpenAiService(openAIApiKey, Duration.ofSeconds(180));
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .model(model)
                .stream(true)
                .messages(list)
                .build();
        service.streamChatCompletion(completionRequest).doOnError(Throwable::printStackTrace)
                .blockingForEach(chunk -> {
                    if (chunk.getChoices().isEmpty() || Objects.isNull(chunk.getChoices().getFirst().getMessage())) {
                        return;
                    }
                    String text = chunk.getChoices().getFirst().getMessage().getContent();
                    if (StringUtils.isNotBlank(text)) {
                        return;
                    }
                    sseService.send(idUser, text);
                });
        service.shutdownExecutor();
        return GlobalResultGenerator.genSuccessResult();
    }
}
