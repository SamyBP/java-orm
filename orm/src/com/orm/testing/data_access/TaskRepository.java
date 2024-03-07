package com.orm.testing.data_access;

import com.orm.data_access.CrudRepository;
import com.orm.data_access.query_builder.Query;
import com.orm.testing.models.Task;
import com.orm.testing.models.User;
import java.sql.SQLException;
import java.util.List;

public class TaskRepository extends CrudRepository<Task, Long> implements ITaskRepository {

    public TaskRepository() {
        super(Task.class);
    }

    @Override
    public List<Task> getAllForUser(User user) throws SQLException {
        return Query.select()
                .from(Task.class)
                .join(User.class, "tasks")
                .where("userId = ?", user.getId())
                .toList(Task.class);
    }
}
