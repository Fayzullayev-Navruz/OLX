package uz.pdp.securitytest.entity;

import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

public interface Parent {
    @CreationTimestamp
    @Column(updatable = false)
    Timestamp createdTime = null;
    @UpdateTimestamp
    Timestamp updatedTime = null;


}
