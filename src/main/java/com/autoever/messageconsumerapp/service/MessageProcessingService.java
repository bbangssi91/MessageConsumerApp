package com.autoever.messageconsumerapp.service;

import com.autoever.messageconsumerapp.common.redis.RedisPublisher;
import com.autoever.messageconsumerapp.constants.enums.MessageChannel;
import com.autoever.messageconsumerapp.domain.vo.MessageLogsVO;
import com.autoever.messageconsumerapp.dto.request.KakaoMessageRequestDto;
import com.autoever.messageconsumerapp.external.KakaoMessageClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageProcessingService {

    private final String API_KEY = "asdfjalksdfjalksdfjalksdwgaegew";

    private final RedisTemplate<String, String> redisTemplate;
    private final KakaoMessageClient kakaoMessageClient;
    private final MessageLogService messageLogService;
    private final RedisPublisher redisPublisher;
    private final ObjectMapper objectMapper;

    @Async("asyncTaskExecutor")
    public void processMessageAsync(MessageLogsVO message) throws JsonProcessingException {
        log.info("[Message Processing] Message Processing Start");
        KakaoMessageRequestDto kakaoMessageRequestDto = new KakaoMessageRequestDto(message.getPhone(), message.getContent());
        // 1. 카카오메시지 API를 호출한다
        ResponseEntity<?> response = kakaoMessageClient.sendKakaoMessage(API_KEY, kakaoMessageRequestDto);
        log.info("[Message Processing] Call API End {}", response.getStatusCode());

        boolean isSuccess = response.getStatusCode().is2xxSuccessful();

        // 2. HttpStatusCode 성공 결과에 따라, DB 상태를 update 한다
        messageLogService.updateStatus(message, isSuccess);

        // 3. 메시지 성공 실패일 경우, 별도로 SMS 전송 이벤트를 발행한다
        if(!isSuccess) {
            String json = objectMapper.writeValueAsString(message);
            redisPublisher.publish(MessageChannel.SMS_CHANNEL.getChannelName(), json);
        }

    }
}
