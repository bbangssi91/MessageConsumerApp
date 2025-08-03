package com.autoever.messageconsumerapp.global.auditing;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass // 부모 클래스의 매핑 정보를 모두 제공해주는 어노테이션. 해당 어노테이션은 Entity로 인식되지 않으며, 데이터베이스에 테이블이 생성되지 않음.
public abstract class BaseEntity {

    @Column(
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @CreatedDate
    @JsonFormat(
            shape = STRING,
            pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    @Column(
            nullable = false,
            insertable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @LastModifiedDate
    @JsonFormat(
            shape = STRING,
            pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyDate;

}
