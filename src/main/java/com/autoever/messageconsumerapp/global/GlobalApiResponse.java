package com.autoever.messageconsumerapp.global;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalApiResponse<T> {

    private int status;
    private String message;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    private T data;

    public GlobalApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.data = data;
    }

    // 정적 성공 응답메시지 생성
    public static <T> GlobalApiResponse<T> success(T data) {
        return new GlobalApiResponse<>(HttpStatus.OK.value(), "SUCCESS", data);
    }

    public static <T> GlobalApiResponse<T> failure(String message, T data) {
        return new GlobalApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, data);
    }

}
