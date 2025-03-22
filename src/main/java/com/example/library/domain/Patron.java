package com.example.library.domain;

import com.example.library.domain.generic.LocalDomain;
import com.example.library.utils.RoleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@Table(name = "Patron")
public class Patron extends LocalDomain {

  @Column(name = "First_Name",nullable = false)
  private String firstName ;

  @Column(name = "Last_Name",nullable = false)
  private String lastName ;

  @Column(name = "Email",unique = true,nullable = false)
  private String email;

  @Column(name = "Phone")
  private String phone;

  public Patron() {

  }
  @OneToMany()
  Set<Book> bookList;

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



}
