package com.aatrox.springmvc.converter;

import com.aatrox.common.anno.DateModify;
import com.aatrox.common.anno.DateTimeOperation;
import org.springframework.context.support.EmbeddedValueResolutionSupport;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.datetime.DateFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: aatrox-cloud
 * @description: 同一时间格式化
 * @author: aatrox
 * @create: 2021-04-08 14:59
 **/
public class GetDateFormatAnnotationFormatterFactory extends EmbeddedValueResolutionSupport
        implements AnnotationFormatterFactory<DateModify> {

    private static final Set<Class<?>> FIELD_TYPES;

    static {
        Set<Class<?>> fieldTypes = new HashSet<>(4);
        fieldTypes.add(Date.class);
        fieldTypes.add(Calendar.class);
        fieldTypes.add(Long.class);
        FIELD_TYPES = Collections.unmodifiableSet(fieldTypes);
    }

    @Override
    public Set<Class<?>> getFieldTypes() {
        return FIELD_TYPES;
    }

    @Override
    public Printer<?> getPrinter(DateModify annotation, Class<?> fieldType) {
        return getFormatter(annotation, fieldType);
    }

    @Override
    public Parser<?> getParser(DateModify annotation, Class<?> fieldType) {
        return getFormatter(annotation, fieldType);
    }

    protected Formatter<Date> getFormatter(DateModify annotation, Class<?> fieldType) {
        DateTimeOperation action = annotation.value();
        DateFormatter formatter = new DateFormatter() {
            @Override
            public Date parse(String text, Locale locale) throws ParseException {
                Date target = new GetDateConverter().convert(text);
                if (target == null) {
                    return null;
                }
                switch (action) {
                    case DEFAULT:
                        return new SimpleDateFormat(annotation.pattern()).parse(text);
                    case FIRST_DATE:
                        return getFirstDate(target);
                    case LAST_DATE:
                        return getLastDate(target);
                }
                return target;
            }
        };
        return formatter;
    }

    public Date getFirstDate(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        //timestramp类型中，23:59:59 如果毫秒值大于500，会自动进1秒
        instance.set(Calendar.MILLISECOND, 0);
        return instance.getTime();
    }

    public Date getLastDate(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.HOUR_OF_DAY, 23);
        instance.set(Calendar.MINUTE, 59);
        instance.set(Calendar.SECOND, 59);
        //timestramp类型中，00：00：00  如果毫秒值等于0，会自动减1秒
        instance.set(Calendar.MILLISECOND, 1);
        return instance.getTime();
    }


}

