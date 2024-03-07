package com.orm.data_access;

import java.sql.SQLException;
import java.util.List;

public interface ICrudRepository<Model, Id> {
    Model save(Model model) throws SQLException;
    List<Model> getAll() throws SQLException;
    Model update(Model model) throws SQLException;
    boolean deleteById(Id id) throws  SQLException;
}
