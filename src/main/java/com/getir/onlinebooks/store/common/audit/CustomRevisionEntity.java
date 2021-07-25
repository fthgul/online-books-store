package com.getir.onlinebooks.store.common.audit;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "rev_info_his")
@RevisionEntity( CustomRevisionEntityListener.class )
@Getter
@Setter
public class CustomRevisionEntity extends DefaultRevisionEntity {

    @Column(name = "removal_time")
    private Date removalTime;

    @Column(name = "user_account")
    private String userAccount;
}
