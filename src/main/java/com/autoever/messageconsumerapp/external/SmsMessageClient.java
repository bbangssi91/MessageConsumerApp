package com.autoever.messageconsumerapp.external;

import com.autoever.messageconsumerapp.config.FeignClientConfig;
import com.autoever.messageconsumerapp.external.fallback.SmsMessageClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "smsMessageClient",
        fallbackFactory = SmsMessageClientFallbackFactory.class,
        configuration = FeignClientConfig.class
)
public interface SmsMessageClient {

    @PostMapping(value = "/sms", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<String> sendSmsMessage(@RequestHeader("API_KEY") String apiKey,
                                        @RequestParam("phone") String phone,
                                        @RequestParam Map<String, ?> params);
}
