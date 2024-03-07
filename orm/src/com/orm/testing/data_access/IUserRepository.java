package com.orm.testing.data_access;

import com.orm.data_access.ICrudRepository;
import com.orm.testing.models.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserRepository extends ICrudRepository<User, Long> {
    User getByEmail(String email) throws SQLException;
    List<User> getAllWithTaskStatus(String taskStatus) throws SQLException;
}
