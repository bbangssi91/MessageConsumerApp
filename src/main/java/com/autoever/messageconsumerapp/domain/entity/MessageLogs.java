package com.autoever.messageconsumerapp.domain.entity;


import com.autoever.messageconsumerapp.constants.enums.TransmissionStatus;
import com.autoever.messageconsumerapp.global.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class MessageLogs extends BaseEntity {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID

    @Column(nullable = false)
    private String accountId; // 발송 대상자 계정

    @Column(nullable = false)
    private String phone; // 전화번호

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private TransmissionStatus transmissionStatus;

    private Long retryCount; // 재시도횟수

    public void changeStatusOnSuccess(boolean isSuccess) {
        this.transmissionStatus = isSuccess ?
                transmissionStatus.onSuccess() : transmissionStatus.onFail();
    }
}
