package com.orm.data_access;

import com.orm.annotations.Id;
import com.orm.annotations.Join;
import com.orm.data_access.db.Database;
import com.orm.data_access.query_builder.Query;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;

public class CrudRepository<Model, Id> implements ICrudRepository<Model, Id> {
    private final Class<Model> clazz;

    public CrudRepository(Class<Model> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Model save(Model model) throws SQLException {
        Field[] fields = this.clazz.getDeclaredFields();
        String[] columns = new String[fields.length - 2];
        Object[] fieldValues = new Object[fields.length - 2];
        int columnIndex = 0;

        for (Field field : fields) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(com.orm.annotations.Id.class) && !field.isAnnotationPresent(Join.class)) {
                columns[columnIndex] = field.getName();
                try {
                    fieldValues[columnIndex] = field.get(model);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                columnIndex++;
            }
        }

        Query query = Query.insertInto(model.getClass())
                    .withColumns(columns)
                    .withValues(fieldValues);

        return (query.executeUpdate() == 0) ? null : model;
    }

    @Override
    public List<Model> getAll() throws SQLException {
        return Query.select()
                .from(this.clazz)
                .toList(this.clazz);
    }

    @Override
    public Model update(Model model) throws SQLException {
        Field[] fields = this.clazz.getDeclaredFields();
        String[] columns = new String[fields.length - 2];
        Object[] fieldValues = new Object[fields.length - 2];
        String identifier = new String();
        Object identifierValue = new Object();
        int columnIndex = 0;

        for (Field field : fields) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(com.orm.annotations.Id.class) && !field.isAnnotationPresent(Join.class)) {
                columns[columnIndex] = field.getName();
                try {
                    fieldValues[columnIndex] = field.get(model);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                columnIndex++;
            }
            else {
                if (field.isAnnotationPresent(com.orm.annotations.Id.class)) {
                    identifier = field.getName();
                    try {
                        identifierValue = field.get(model);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        Query query = Query.update(model.getClass())
                .set(columns)
                .values(fieldValues)
                .where(identifier + "=?", identifierValue);

        return (query.executeUpdate() == 0) ? null : model;
    }

    @Override
    public boolean deleteById(Id id) throws SQLException {
        return Query.delete()
                .from(this.clazz)
                .where("Id=?", id)
                .executeUpdate() != 0;

    }
}
