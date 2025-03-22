package com.example.library.domain;

import com.example.library.domain.generic.LocalDomain;
import com.example.library.security.model.CustomUserDetails;
import com.example.library.utils.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "Application_User")
public class ApplicationUser extends LocalDomain {
  @Nationalized
  @Column(name = "Arabic_Name",nullable = false)
  private String arabicName ;

  @Column(name = "English_Name",nullable = false)
  private String englishName ;

  @Column(name = "Password",nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password ;

  @Column(name = "Username",unique = true,nullable = false)
  private String username;

  @ManyToOne(fetch = FetchType.EAGER)
  private Role role;

  @Column(name = "Phone")
  private String phone;

  public ApplicationUser() {

  }

  @Override
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public Date getCreationDate() {
    return super.getCreationDate();
  }

  @Override
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public Date getModifiedDate() {
    return super.getModifiedDate();
  }

  @Override
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public Integer getCreatorId() {
    return super.getCreatorId();
  }

  @Override
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public Integer getModifierId() {
    return super.getModifierId();
  }

  public boolean  isAdmin(){
    return this.role.getName().equals(RoleType.Admin.name());
  }

}
