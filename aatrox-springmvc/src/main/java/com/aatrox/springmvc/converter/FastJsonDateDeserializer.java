package com.aatrox.springmvc.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * @program: aatrox-cloud
 * @description: fastjson配置
 * @author: aatrox
 * @create: 2021-04-08 14:56
 **/
public class FastJsonDateDeserializer extends StdScalarDeserializer<Date> {
    protected FastJsonDateDeserializer(Class vc) {
        super(vc);
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        String text = p.getText();
        return new GetDateConverter().convert(text);
    }
}
