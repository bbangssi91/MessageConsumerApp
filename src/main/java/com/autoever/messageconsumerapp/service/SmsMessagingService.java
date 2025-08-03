package com.autoever.messageconsumerapp.service;

import com.autoever.messageconsumerapp.common.redis.RedisPublisher;
import com.autoever.messageconsumerapp.constants.enums.MessageChannel;
import com.autoever.messageconsumerapp.domain.vo.MessageLogsVO;
import com.autoever.messageconsumerapp.external.SmsMessageClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class SmsMessagingService {

    @Value( "${external.sms.api-key}")
    private String API_KEY;

    private final SmsMessageClient smsMessageClient;
    private final MessageLogService messageLogService;
    private final RedisPublisher redisPublisher;

    @Async("asyncTaskExecutor")
    public void processMessageAsync(MessageLogsVO message) throws JsonProcessingException {
        log.info("[Message Processing] Message Processing Start");

        try {
            // 1. SMS 메시지 API를 호출한다
            Map<String, Object> formParams = new HashMap<>();
            formParams.put("message", message.getContent());
            ResponseEntity<?> response = smsMessageClient.sendSmsMessage(API_KEY, message.getPhone(), formParams);

            // 2. HttpStatusCode 성공 결과에 따라, DB 상태를 update 한다
            messageLogService.updateStatus(message, response.getStatusCode().is2xxSuccessful());

        } catch (FeignException e) {
            log.error("[Message Processing] FeignException occurred: {}", e.getMessage(), e);
            // API 호출 예외 발생 시, 상태를 실패로 update 한다
            messageLogService.updateStatus(message, e.status() == 200);
            // 메시지 성공 실패일 경우, fallback 으로 데드레터 이벤트를 발행한다
            publishDeadLetterFallbackEvent(message);
        }
    }

    private void publishDeadLetterFallbackEvent(MessageLogsVO message) {
        try {
            String json = new ObjectMapper().writeValueAsString(message);
            redisPublisher.publish(MessageChannel.MESSAGE_DEAD_LETTER_CHANNEL.getChannelName(), json);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize fallback message", e);
        }
    }
}
