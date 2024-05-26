package com.fer.infsus.eizbori.config.converters;

import com.fer.infsus.eizbori.entity.enums.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        return status.getLabel();
    }

    @Override
    public Status convertToEntityAttribute(String s) {
        return Status.fromLabel(s);
    }
}
