package com.orm.testing;

import com.orm.testing.data_access.ITaskRepository;
import com.orm.testing.data_access.IUserRepository;
import com.orm.testing.data_access.TaskRepository;
import com.orm.testing.data_access.UserRepository;
import com.orm.testing.dtos.TaskDto;
import com.orm.testing.dtos.UserDto;
import com.orm.testing.mappers.TaskProfile;
import com.orm.testing.mappers.UserProfile;
import com.orm.testing.models.Task;
import com.orm.testing.models.User;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Test {
    IUserRepository userRepository = new UserRepository();
    ITaskRepository taskRepository = new TaskRepository();
    UserProfile userProfile = new UserProfile();
    TaskProfile taskProfile = new TaskProfile();

    public void getAllTask() {
        try {

            System.out.println("______Fetching all tasks______");

            List<Task> tasks = taskRepository.getAll();
            for (Task task : tasks ) {
                System.out.println(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getTasksForUser() {
        try {

            UserDto userDto = new UserDto();
            userDto.setId(13L);

            System.out.println("______All task for user with id:" + 13 + "_______");
            List<Task> tasks = taskRepository.getAllForUser(userProfile.map(userDto));
            for (Task task : tasks) {
                System.out.println(task);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTask() {
        try {
            TaskDto taskDto = new TaskDto(13L,"test_add_name", "test_add_description", new Date(), "IN_PROGRESS");

            if (taskRepository.save(taskProfile.map(taskDto)) != null)
                System.out.println("Added task successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTask() {
        try {
            TaskDto taskDto = new TaskDto();
            taskDto.setId(5L);
            taskDto.setUserId(14L);
            taskDto.setDescription("new description");
            taskDto.setName("new name");
            taskDto.setStatus("COMPLETED");
            if (taskRepository.update(taskProfile.map(taskDto)) != null)
                System.out.println("Updated task successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeTask() {
        try {
            if (taskRepository.deleteById(10L))
                System.out.println("Deleted task successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getUsersWithTaskInProgress() {
        try {

            System.out.println("______Users with tasks in progress______");
            List<User> users = userRepository.getAllWithTaskStatus("IN_PROGRESS");

            for (User user : users) {
                System.out.println(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
