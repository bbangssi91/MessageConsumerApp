package com.autoever.messageconsumerapp.external;

import com.autoever.messageconsumerapp.config.FeignClientConfig;
import com.autoever.messageconsumerapp.dto.request.KakaoMessageRequestDto;
import com.autoever.messageconsumerapp.external.fallback.KakaoMessageClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "kakaoMessageClient",
        url = "http://localhost:8081",
        fallbackFactory = KakaoMessageClientFallbackFactory.class,
        configuration = FeignClientConfig.class
)
public interface KakaoMessageClient {

    @PostMapping(value = "/kakaotalk-messages", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> sendKakaoMessage(@RequestHeader("API_KEY") String apiKey, @RequestBody KakaoMessageRequestDto request);

}
