package com.example.library.utils.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import java.util.TimeZone;

public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {
        Hibernate5Module hm = new Hibernate5Module();
        hm.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        registerModule(hm).setTimeZone(TimeZone.getDefault());
    }

}