package com.autoever.messageconsumerapp.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     *  Redis에 이벤트를 전송할때, 큐 방식의 FIFO를 만족하도록 LPUSH + BRPOP 을 사용
     *
     *  @param channel   카카오메시지 채널 (kakao-message-queue)
     *  @param message   메시지 내용
     */
    public void publish(String channel, String message) {
        redisTemplate.opsForList().leftPush(channel, message);
    }
}
