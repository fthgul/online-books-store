package com.getir.onlinebooks.store.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.history.RevisionMetadata;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.Instant;
import java.util.Date;

@MappedSuperclass
@Audited
@Getter
@Setter
public class BaseEntity {

    @Column(name = "created_date",
            nullable = false, updatable = false,
            columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;


    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Transient
    private RevisionMetadata<Integer> editVersion;

    public BaseEntity() {
        this.lastModifiedDate = Date.from(Instant.now());
    }

}

