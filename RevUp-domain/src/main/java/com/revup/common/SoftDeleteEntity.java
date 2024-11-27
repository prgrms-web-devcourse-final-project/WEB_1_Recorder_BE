package com.revup.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class SoftDeleteEntity extends BaseTimeEntity {

    @Enumerated(EnumType.STRING)
    private BooleanStatus isDeleted = BooleanStatus.FALSE;

    public void softDelete(){
        this.isDeleted = BooleanStatus.TRUE;
    }
}
