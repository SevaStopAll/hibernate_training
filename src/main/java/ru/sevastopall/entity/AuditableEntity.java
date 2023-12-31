package ru.sevastopall.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import ru.sevastopall.listener.AuditDatesListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditDatesListener.class)
/*@Audited*/
public abstract class AuditableEntity<T extends Serializable> implements BaseEntity<T>{

    private Instant createdAt;
    private String createdBy;

    private Instant updatedAt;
    private String updatedBy;


}
