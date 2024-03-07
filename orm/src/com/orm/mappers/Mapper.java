package com.orm.mappers;
import com.orm.annotations.Join;

import java.lang.reflect.Field;

public class Mapper{
    public <Source, Destination> Destination createMap(Source src, Class<Destination> dst)  {
        Destination object = null;
        try {
            object = dst.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Field[] dstFields = dst.getDeclaredFields();

        for (Field dstField : dstFields) {
            if (!dstField.isAnnotationPresent(Join.class)) {
                dstField.setAccessible(true);
                try {
                    Field srcField = src.getClass().getDeclaredField(dstField.getName());
                    srcField.setAccessible(true);
                    dstField.set(object, srcField.get(src));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        return object;
    }
}
