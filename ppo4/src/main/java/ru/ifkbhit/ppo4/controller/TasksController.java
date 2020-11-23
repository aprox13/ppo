package ru.ifkbhit.ppo4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ifkbhit.ppo4.dao.TaskDao;
import ru.ifkbhit.ppo4.model.Task;
import ru.ifkbhit.ppo4.model.TaskList;

@Controller
public class TasksController {

    final
    TaskDao taskDao;

    public TasksController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }


    @GetMapping(value = "/lists")
    public String lists(ModelMap map) {
        map.addAttribute("lists", taskDao.getLists());
        map.addAttribute("newTaskList", new TaskList());
        map.addAttribute("deleteTaskListId", -1L);
        map.addAttribute("newTask", new Task());
        return "index";
    }

    @PostMapping("/list-add")
    public String addList(@ModelAttribute("newTaskList") TaskList list) {
        taskDao.addList(list);
        return "redirect:/lists";
    }

    @PostMapping("/list/{id}/delete")
    public String listDelete(@PathVariable("id") Long id) {
        taskDao.deleteTaskList(id);
        return "redirect:/lists";
    }

    @PostMapping("/list/{id}/add-task")
    public String taskAdd(@PathVariable("id") Long listId, @ModelAttribute("newTask") Task task) {
        task.setTaskListId(listId);
        task.setDone(false);
        taskDao.addTask(task);
        return "redirect:/lists";
    }

    @PostMapping("/task/{id}/toggle")
    public String toggleTask(@PathVariable("id") Long taskId) {
        Task task = taskDao.getTask(taskId);
        if (task != null) {
            task.setDone(!task.isDone());
            taskDao.setDone(task);
        }
        return "redirect:/lists";
    }

    @PostMapping("/task/{id}/delete")
    public String deleteTask(@PathVariable("id") Long taskId) {
        taskDao.deleteTask(taskId);
        return "redirect:/lists";
    }
}
