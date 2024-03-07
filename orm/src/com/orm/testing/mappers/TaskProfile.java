package com.orm.testing.mappers;

import com.orm.mappers.Mapper;
import com.orm.testing.dtos.TaskDto;
import com.orm.testing.models.Task;

public class TaskProfile extends Mapper {

    public Task map(TaskDto taskDto) {
        return this.createMap(taskDto, Task.class);
    }

}
