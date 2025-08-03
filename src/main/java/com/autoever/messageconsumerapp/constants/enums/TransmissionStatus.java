package com.autoever.messageconsumerapp.constants.enums;

public enum TransmissionStatus {

    PENDING {
        @Override
        public TransmissionStatus onSuccess() {
            return KAKAO_MESSAGE;
        }
        @Override
        public TransmissionStatus onFail() {
            return KAKAO_MESSAGE_FAIL;
        }
    },
    KAKAO_MESSAGE {
        @Override
        public TransmissionStatus onSuccess() {
            return KAKAO_MESSAGE;
        }
        @Override
        public TransmissionStatus onFail() {
            return KAKAO_MESSAGE_FAIL;
        }
    },
    KAKAO_MESSAGE_FAIL {
        @Override
        public TransmissionStatus onSuccess() {
            return SMS_MESSAGE;
        }
        @Override
        public TransmissionStatus onFail() {
            return SMS_MESSAGE_FAIL;
        }
    },
    SMS_MESSAGE {
        @Override
        public TransmissionStatus onSuccess() {
            return SMS_MESSAGE;
        }
        @Override
        public TransmissionStatus onFail() {
            return SMS_MESSAGE_FAIL;
        }
    },
    SMS_MESSAGE_FAIL {
        @Override
        public TransmissionStatus onSuccess() {
            return SMS_MESSAGE;
        }
        @Override
        public TransmissionStatus onFail() {
            return PENDING;
        }
    };

    public abstract TransmissionStatus onSuccess();
    public abstract TransmissionStatus onFail();

}
