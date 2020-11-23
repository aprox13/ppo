package ru.ifkbhit.ppo4.model;


import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private Long id;
    private List<Task> tasks = new ArrayList<>();
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "id=" + id +
                ", tasks=" + tasks +
                ", name='" + name + '\'' +
                '}';
    }
}
