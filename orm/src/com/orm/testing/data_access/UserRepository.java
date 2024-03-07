package com.orm.testing.data_access;

import com.orm.data_access.CrudRepository;
import com.orm.data_access.query_builder.Query;
import com.orm.testing.models.Task;
import com.orm.testing.models.User;

import java.sql.SQLException;
import java.util.List;

public class UserRepository extends CrudRepository<User, Long> implements IUserRepository {

    public UserRepository() {
        super(User.class);
    }

    @Override
    public User getByEmail(String email) throws SQLException {
        return Query.select()
                .from(User.class)
                .where("email = ?", email)
                .firstOrDefault(User.class);
    }

    @Override
    public List<User> getAllWithTaskStatus(String taskStatus) throws SQLException{
        return Query.select()
                .from(User.class)
                .join(Task.class, "user")
                .where("status = ?", taskStatus)
                .toList(User.class);
    }
}
