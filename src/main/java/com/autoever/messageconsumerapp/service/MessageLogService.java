package com.autoever.messageconsumerapp.service;

import com.autoever.messageconsumerapp.domain.vo.MessageLogsVO;
import com.autoever.messageconsumerapp.repository.MessageLogsResitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MessageLogService {

    private final MessageLogsResitory messageLogsRepository;

    @Transactional
    public void updateStatus(MessageLogsVO message, boolean isSuccess) {
        messageLogsRepository.findById(message.getId())
                .ifPresent(logs -> logs.changeStatusOnSuccess(isSuccess));
    }
}
