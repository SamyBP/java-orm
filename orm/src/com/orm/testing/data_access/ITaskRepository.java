package com.orm.testing.data_access;

import com.orm.data_access.ICrudRepository;
import com.orm.testing.models.Task;
import com.orm.testing.models.User;

import java.sql.SQLException;
import java.util.List;

public interface ITaskRepository extends ICrudRepository<Task, Long> {
    List<Task> getAllForUser(User user) throws SQLException;
}
