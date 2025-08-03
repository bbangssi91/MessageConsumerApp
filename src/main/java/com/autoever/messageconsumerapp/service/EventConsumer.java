package com.autoever.messageconsumerapp.service;

import com.autoever.messageconsumerapp.domain.vo.MessageLogsVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.autoever.messageconsumerapp.constants.enums.MessageChannel.KAKAO_TALK_CHANNEL;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventConsumer {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final MessageProcessingService messageProcessingService;

    // 61초 단위로 스케줄링
    @Scheduled(fixedRate = 61_000)
    public void consumeMessages() {
        int maxMessages = 100;

        for (int i = 0; i < maxMessages; i++) {
            String json = redisTemplate.opsForList().rightPop(KAKAO_TALK_CHANNEL.getChannelName());
            if (json == null) break;

            try {
                MessageLogsVO message = objectMapper.readValue(json, MessageLogsVO.class);
                messageProcessingService.processMessageAsync(message);
            } catch (JsonProcessingException e) {
                log.error("[Error] JSON 파싱 에러");
            }
        }
    }
}
