package com.example.library.utils.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageSourceService {
    @Autowired
    private MessageSource messageSource;




    public String getMessage( String message) {
        return this.messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    }
}
