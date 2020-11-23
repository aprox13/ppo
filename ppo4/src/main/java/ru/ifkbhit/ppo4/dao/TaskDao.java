package ru.ifkbhit.ppo4.dao;

import ru.ifkbhit.ppo4.model.Task;
import ru.ifkbhit.ppo4.model.TaskList;

import java.util.List;

public interface TaskDao {
    void setDone(Task task);

    Task getTask(Long id);

    void deleteTask(long id);

    void deleteTaskList(long id);

    TaskList addList(TaskList taskList);

    Task addTask(Task task);

    List<TaskList> getLists();
}
