package com.orm.testing.controllers;

import com.orm.testing.data_access.ITaskRepository;
import com.orm.testing.dtos.TaskDto;
import com.orm.testing.mappers.TaskProfile;
import com.orm.testing.models.Task;
import com.orm.testing.models.User;

import java.sql.SQLException;
import java.util.List;

public class TaskController {
    private final ITaskRepository taskRepository;
    private final TaskProfile taskProfile = new TaskProfile();

    public TaskController(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTask() throws SQLException {
        return taskRepository.getAll();
    }

    public boolean addTask(TaskDto taskDto) throws SQLException {
        return taskRepository.save(taskProfile.map(taskDto)) != null;
    }

    public boolean updateTask(TaskDto taskDto) throws SQLException {
        return taskRepository.update(taskProfile.map(taskDto)) != null;
    }

    public boolean removeTask(TaskDto taskDto) throws SQLException {
        return taskRepository.deleteById(taskDto.getId());
    }

    public List<Task> getTasksForUser(User user) throws SQLException {
        return taskRepository.getAllForUser(user);
    }
}
