//package com.timekeeping.common.entity;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.persistence.Column;
//import javax.persistence.EntityListeners;
//import javax.persistence.MappedSuperclass;
//import java.io.Serializable;
//import java.sql.Timestamp;
//
///**
// * @author quang on 13/10/2023
// * @project BE
// */
//@MappedSuperclass
//@Getter
//@Setter
//@EntityListeners(AuditingEntityListener.class)
//public abstract class Auditable<U> implements Serializable {
//
//    private static final long serialVersionUID = 5282450495494154675L;
//
//    @Column(name = "created_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    @CreatedDate
//    protected Timestamp createdDate;
//
//    @Column(name = "modified_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    @LastModifiedDate
//    protected Timestamp modifiedDate;
//
//    @Column(name = "created_by", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) DEFAULT 'Unknown'")
//    @CreatedBy
//    protected U createdBy;
//
//    @Column(name = "modified_by", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'Unknown'")
//    @LastModifiedBy
//    protected U modifiedBy;
//}