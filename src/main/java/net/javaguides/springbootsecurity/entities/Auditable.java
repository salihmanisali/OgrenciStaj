package net.javaguides.springbootsecurity.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.persistence.*;
import java.util.Date;


@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U>
{
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private U createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @LastModifiedBy
    private U lastModifiedBy;

    @LastModifiedDate
    private Date lastModifiedDate;

    private String createdByIp;
    private String lastModifiedByIp;

    @PrePersist
    public void prePersist() {
        createdByIp = getIPAuthenticatedUser();

    }

    @PreUpdate
    public void preUpdate() {
        lastModifiedByIp = getIPAuthenticatedUser();
    }

    private String getIPAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String ipAddress=null;
        Object details = authentication.getDetails();
        if (details instanceof WebAuthenticationDetails)
            ipAddress = ((WebAuthenticationDetails) details).getRemoteAddress();

        return ipAddress;

    }
}