package com.autoever.messageconsumerapp.repository;

import com.autoever.messageconsumerapp.domain.entity.MessageLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageLogsResitory extends JpaRepository<MessageLogs, Long> {
}
