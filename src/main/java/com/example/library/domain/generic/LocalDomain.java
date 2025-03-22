package com.example.library.domain.generic;

import com.example.library.security.model.CustomUserDetails;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
//import com.example.File.config.security.model.CustomUserDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class LocalDomain extends GenericDomain<Integer> {

    @Column(name = "Record_Status")
    Integer recordStatus = 1;

    @Column(name = "Creation_Date", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    Date creationDate;

    @Column(name = "CreatorId", updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Integer creatorId;

    @Column(name = "Modified_Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    Date modifiedDate;

    @Column(name = "ModifierId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Integer modifierId;

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public Integer getModifierId() {
        return modifierId;
    }

    @PrePersist
    public void setDatePrePersist() {
        this.setCreationDate(new Date());
        if (CustomUserDetails.getCurrentInstance() != null && CustomUserDetails.getCurrentInstance().getApplicationUser() != null)
            this.setCreatorId(CustomUserDetails.getCurrentInstance().getApplicationUser().getId());

}

    @PreUpdate
    public void setDatePreUpdate() {
        this.setModifiedDate(new Date());
        if (CustomUserDetails.getCurrentInstance() != null && CustomUserDetails.getCurrentInstance().getApplicationUser() != null)
            this.setModifierId(CustomUserDetails.getCurrentInstance().getApplicationUser().getId());
    }
}
