package com.autoever.messageconsumerapp.external.fallback;

import com.autoever.messageconsumerapp.external.KakaoMessageClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class KakaoMessageClientFallbackFactory implements FallbackFactory<KakaoMessageClient> {

    @Override
    public KakaoMessageClient create(Throwable cause) {
        return (apiKey, request) -> ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
}
