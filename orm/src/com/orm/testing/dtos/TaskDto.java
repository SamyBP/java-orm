package com.orm.testing.dtos;

import java.util.Date;

public class TaskDto {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private Date deadline;
    private String status;

    public TaskDto() {
    }

    public TaskDto(Long userId,String name, String description, Date deadline, String status) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return deadline;
    }

    public void setDate(Date deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
