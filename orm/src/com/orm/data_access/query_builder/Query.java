package com.orm.data_access.query_builder;

import com.orm.annotations.Join;
import com.orm.annotations.ManyToOne;
import com.orm.annotations.OneToMany;
import com.orm.data_access.db.Database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Query {
    private String sql;
    private List<Object> parameters;
    private final Connection connection;

    private Query(String sql) {
        this.sql = sql;
        this.connection = Database.getConnection();
        this.parameters = new ArrayList<>();
    }

    public static Query select(String... attributes) {
        StringBuilder sqlSelect = new StringBuilder("SELECT ");

        if (attributes.length == 0) {
            sqlSelect.append("* ");
        } else {
            for (int i = 0; i < attributes.length; i++) {
                sqlSelect.append(attributes[i]);
                if (i < attributes.length - 1)
                    sqlSelect.append(", ");
                else
                    sqlSelect.append(" ");
            }
        }

        return new Query(sqlSelect.toString());
    }

    public Query from(Class<?> clazz) {
        this.sql += "FROM " + clazz.getSimpleName();
        return this;
    }

    public Query join(Class<?> clazz, String fieldName) {
        this.sql += " JOIN " + clazz.getSimpleName() + " ON ";
        try {
            Field field = clazz.getDeclaredField(fieldName);
            String tableName = "";

            if (field.isAnnotationPresent(ManyToOne.class)) {
                tableName = field.getAnnotation(ManyToOne.class).table();
            }
            else if (field.isAnnotationPresent(OneToMany.class)) {
                tableName = field.getAnnotation(OneToMany.class).table();
            }
            else {
                throw new NoSuchFieldException("Field is not annotated");
            }

            String column = field.getAnnotation(Join.class).column();
            String referencedColumn = field.getAnnotation(Join.class).referencedColumn();

            this.sql += tableName + "." + referencedColumn + " = " + clazz.getSimpleName() + "." + column;

            return this;

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }


    public Query where(String condition, Object... parameters) {
        this.sql += " WHERE " + condition;
        this.parameters.addAll(Arrays.asList(parameters));
        return this;
    }

    public static Query update(Class<?> clazz) {
        String sqlUpdate = "UPDATE " + clazz.getSimpleName() + " ";

        return new Query(sqlUpdate);
    }

    public Query set(String... attributes) {
        if (attributes.length != 0) {
            StringBuilder sqlUpdate = new StringBuilder("SET ");
            for (int i = 0; i < attributes.length; i++) {
                sqlUpdate.append(attributes[i]);
                sqlUpdate.append("=?");
                if (i < attributes.length - 1)
                    sqlUpdate.append(", ");
            }

            this.sql += sqlUpdate.toString();
        }

        return this;
    }

    public Query values(Object... values) {
        parameters.addAll(Arrays.asList(values));
        return this;
    }

    public static Query delete() {
        return new Query("DELETE ");
    }

    public static Query insertInto(Class<?> clazz) {
        return new Query("INSERT INTO " + clazz.getSimpleName());
    }

    public Query withColumns(String... columns) {
        if (columns.length != 0) {
            StringBuilder sqlColumnBuilder = new StringBuilder("(");
            sqlColumnBuilder.append(String.join(", ", columns));
            sqlColumnBuilder.append(")");
            this.sql += sqlColumnBuilder.toString();
        }

        return this;
    }

    public Query withValues(Object... values) {
        if (values.length != 0) {
            StringBuilder sqlValues = new StringBuilder(" VALUES (");
            for (int i = 0; i < values.length; i++) {
                sqlValues.append("?");
                if (i < values.length - 1)
                    sqlValues.append(", ");
                this.parameters.add(values[i]);
            }
            sqlValues.append(")");
            this.sql += sqlValues.toString();
        }
        return this;
    }
    private ResultSet executeQuery() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.sql);
        setParameters(statement);
        return statement.executeQuery();
    }

    public int executeUpdate() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.sql);
        setParameters(statement);
        return statement.executeUpdate();
    }

     public <Model> Model firstOrDefault(Class<Model> clazz) throws SQLException {
        ResultSet resultSet = executeQuery();

        if (resultSet.next()) {
            try {
                Model model = clazz.getConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();

                for (Field field : fields) {
                    if (!field.isAnnotationPresent(Join.class)) {
                        field.setAccessible(true);
                        field.set(model, resultSet.getObject(field.getName()));
                    }
                }

                return model;

            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
     }

     public <Model> List<Model> toList(Class<Model> clazz) throws SQLException {
        ResultSet resultSet = executeQuery();

        try {
            List<Model> resultList = new ArrayList<>();

            while (resultSet.next()) {
                Model model = clazz.getConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();

                for (Field field : fields) {
                    if (!field.isAnnotationPresent(Join.class)) {
                        field.setAccessible(true);
                        field.set(model, resultSet.getObject(field.getName()));
                    }
                }

                resultList.add(model);
            }

            return resultList;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
     }

    private void setParameters(PreparedStatement statement) throws SQLException {
        if (this.parameters != null) {
            int index = 0;

            for (Object parameter : this.parameters) {
                statement.setObject(++index, parameter);
            }
        }
    }
}
