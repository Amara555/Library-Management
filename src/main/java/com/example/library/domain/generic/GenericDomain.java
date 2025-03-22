package com.example.library.domain.generic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class GenericDomain<IdClass> implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected IdClass id;

    public GenericDomain() {
    }

    public GenericDomain(IdClass id) {
        this.id = id;
    }



    public void setId(IdClass id) {
        this.id = id;
    }

    public IdClass getId() {
        return id;
    }
}
