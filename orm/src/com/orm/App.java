package com.orm;

import com.orm.testing.Test;
import com.orm.testing.data_access.ITaskRepository;
import com.orm.testing.data_access.IUserRepository;
import com.orm.testing.data_access.TaskRepository;
import com.orm.testing.data_access.UserRepository;
import com.orm.testing.models.Task;
import com.orm.testing.models.User;

import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Test test = new Test();

//        test.getAllTask();
//        test.getTasksForUser();
//        test.addTask();
//        test.updateTask();
//        test.removeTask();
        test.getUsersWithTaskInProgress();
    }
}