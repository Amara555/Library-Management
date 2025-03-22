package com.example.library.domain;

import com.example.library.domain.generic.LocalDomain;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Roles")
public class Role extends LocalDomain {
    @Column(name = "Arabic_Name")
    @Nationalized
    private String arabicName;
    @Column(name = "English_Name")
    private String englishName;
    @Column(nullable = false,unique = true)
    private String name;


}
