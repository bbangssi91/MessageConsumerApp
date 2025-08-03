package com.autoever.messageconsumerapp.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum MessageChannel {
    KAKAO_TALK_CHANNEL("kakaotalk-message-queue"),
    SMS_CHANNEL("sms-message-queue"),
    MESSAGE_DEAD_LETTER_CHANNEL("msg-dead-letter-queue");

    private String channelName;
}
