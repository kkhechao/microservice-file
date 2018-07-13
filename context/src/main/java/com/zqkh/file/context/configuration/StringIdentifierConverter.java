package com.zqkh.file.context.configuration;

import com.jovezhao.nest.ddd.Identifier;
import com.jovezhao.nest.ddd.StringIdentifier;
import org.dozer.DozerConverter;
import org.springframework.stereotype.Component;


@Component
public class StringIdentifierConverter extends DozerConverter<Identifier, String> {


    public StringIdentifierConverter() {
        super(Identifier.class, String.class);
    }

    @Override
    public String convertTo(Identifier source, String destination) {
        return source.toValue();
    }

    @Override
    public Identifier convertFrom(String source, Identifier destination) {
        return new StringIdentifier(source);
    }
}
