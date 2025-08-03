package com.autoever.messageconsumerapp.external.fallback;

import com.autoever.messageconsumerapp.external.SmsMessageClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SmsMessageClientFallbackFactory implements FallbackFactory<SmsMessageClient> {

    @Override
    public SmsMessageClient create(Throwable cause) {
        return (apiKey, phone, params) -> ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
}
