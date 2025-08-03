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
import static com.autoever.messageconsumerapp.constants.enums.MessageChannel.SMS_CHANNEL;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventConsumer {

    private static final long MESSAGE_SCHEDULING_RATE = 60_100;

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final KakaoMessagingService kakaoMessagingService;
    private final SmsMessagingService smsMessagingService;

    // 60초 단위로 스케줄링 (불필요한 Fallback 방지를 위해 fixedDelay 전략 사용)
    @Scheduled(fixedDelay = MESSAGE_SCHEDULING_RATE)
    public void consumeKakaoEventMessages() {
        int kakaoMaxMessages = 100;

        for (int i = 0; i < kakaoMaxMessages; i++) {
            String json = redisTemplate.opsForList().rightPop(KAKAO_TALK_CHANNEL.getChannelName());
            if (json == null) break;

            try {
                MessageLogsVO message = objectMapper.readValue(json, MessageLogsVO.class);
                kakaoMessagingService.processMessageAsync(message);
            } catch (JsonProcessingException e) {
                log.error("[Error] JSON 파싱 에러");
            }
        }
    }

    // 60초 단위로 스케줄링 (불필요한 Fallback 방지를 위해 fixedDelay 전략 사용)
    @Scheduled(fixedDelay = MESSAGE_SCHEDULING_RATE)
    public void consumeSmsEventMessages() {
        int smsMaxMessages = 500;

        for (int i = 0; i < smsMaxMessages; i++) {
            String json = redisTemplate.opsForList().rightPop(SMS_CHANNEL.getChannelName());
            if (json == null) break;

            try {
                MessageLogsVO message = objectMapper.readValue(json, MessageLogsVO.class);
                smsMessagingService.processMessageAsync(message);
            } catch (JsonProcessingException e) {
                log.error("[Error] JSON 파싱 에러");
            }
        }
    }


}
