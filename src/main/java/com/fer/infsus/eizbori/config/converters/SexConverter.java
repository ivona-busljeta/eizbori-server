package com.fer.infsus.eizbori.config.converters;

import com.fer.infsus.eizbori.entity.enums.Sex;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SexConverter implements AttributeConverter<Sex, String> {

    @Override
    public String convertToDatabaseColumn(Sex sex) {
        return sex.getLabel();
    }

    @Override
    public Sex convertToEntityAttribute(String s) {
        return Sex.fromLabel(s);
    }
}
