package com.autoever.messageconsumerapp.domain.vo;

import com.autoever.messageconsumerapp.constants.enums.TransmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageLogsVO {

    private Long id; // ID
    private String accountId; // 발송 대상자 계정
    private String phone; // 전화번호
    private String content;
    private TransmissionStatus transmissionStatus;
    private Long retryCount; // 재시도횟수
}
