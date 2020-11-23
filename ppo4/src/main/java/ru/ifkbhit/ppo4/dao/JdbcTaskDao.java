package ru.ifkbhit.ppo4.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import ru.ifkbhit.ppo4.model.Task;
import ru.ifkbhit.ppo4.model.TaskList;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


public class JdbcTaskDao extends JdbcDaoSupport implements TaskDao {


    public JdbcTaskDao(DataSource dataSource) throws IOException {
        setDataSource(dataSource);

        URL initFileUri = Objects.requireNonNull(
                getClass().getClassLoader().getResource("sql/create.sql")
        );
        Arrays.stream(String.join("", Files.readAllLines(Paths.get(initFileUri.getPath())))
                .split(";")).forEach(Objects.requireNonNull(getJdbcTemplate())::execute);
    }

    @Override
    public void setDone(Task task) {
        String sql = "UPDATE Task SET DONE = ? WHERE ID = ?";
        Objects.requireNonNull(getJdbcTemplate()).update(sql, task.isDone(), task.getId());
    }

    @Override
    public Task getTask(Long id) {
        String sql = "SELECT * FROM Task WHERE ID = " + id;

        return Objects.requireNonNull(getJdbcTemplate()).query(sql, new TaskMapper()).stream().findFirst().orElse(null);
    }


    @Override
    public void deleteTask(long id) {
        String sql = "DELETE FROM Task WHERE ID = ?";
        Objects.requireNonNull(getJdbcTemplate()).update(sql, id);
    }

    @Override
    public void deleteTaskList(long id) {
        String sql = "DELETE FROM TaskList WHERE ID = ?";
        Objects.requireNonNull(getJdbcTemplate()).update(sql, id);

        String sqlTask = "DELETE FROM Task WHERE LIST_ID = ?";
        getJdbcTemplate().update(sqlTask, id);
    }

    @Override
    public TaskList addList(TaskList taskList) {
        String sql = "INSERT INTO TaskList(NAME) VALUES (?)";
        Map<Integer, Object> params = Map.of(1, taskList.getName());

        var id = insertAndGetId(sql, params);
        taskList.setId(id);

        return taskList;
    }

    @Override
    public Task addTask(Task task) {
        String sql = "INSERT INTO Task(TASK, DONE, LIST_ID) VALUES (?, ?, ?)";
        Map<Integer, Object> params = Map.of(1, task.getTask(), 2, task.isDone(), 3, task.getTaskListId());

        var id = insertAndGetId(sql, params);
        task.setId(id);

        return task;
    }

    private TaskList combine(List<TaskList> taskLists) {
        assert !taskLists.isEmpty();

        TaskList list = new TaskList();
        Set<Task> tasks = new HashSet<>();

        taskLists.forEach(tl -> tasks.addAll(tl.getTasks()));

        list.setName(taskLists.get(0).getName());
        list.setId(taskLists.get(0).getId());
        list.setTasks(new ArrayList<>(tasks.stream().sorted(Comparator.comparing(Task::getId)).collect(Collectors.toList())));
        return list;
    }

    @Override
    public List<TaskList> getLists() {
        String sql = "SELECT Task.ID AS tId, Task.TASK AS task, Task.DONE AS done, TaskList.ID AS tlId, TaskList.NAME AS name FROM TaskList LEFT JOIN Task ON Task.LIST_ID = TaskList.ID";
        List<TaskList> lists = Objects.requireNonNull(getJdbcTemplate()).query(sql, new TaskListMapper());


        return lists
                .stream()
                .collect(Collectors.groupingBy(TaskList::getId))
                .values()
                .stream()
                .map(this::combine)
                .collect(Collectors.toList());
    }


    private static class TaskMapper implements RowMapper<Task> {

        @Override
        public Task mapRow(ResultSet resultSet, int i) throws SQLException {
            Task result = new Task();
            result.setId(resultSet.getLong("ID"));
            result.setTask(resultSet.getString("TASK"));
            result.setDone(resultSet.getBoolean("DONE"));
            result.setTaskListId(resultSet.getLong("LIST_ID"));
            return result;
        }
    }

    private static class TaskListMapper implements RowMapper<TaskList> {

        @Override
        public TaskList mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<TaskList, List<Task>> temp = new HashMap<>();

            TaskList taskList = new TaskList();
            taskList.setId(rs.getLong("tlId"));
            taskList.setName(rs.getString("name"));

            temp.putIfAbsent(taskList, new ArrayList<>());

            long taskId = rs.getLong("tId");
            String taskData = rs.getString("task");
            boolean done = rs.getBoolean("done");

            if (taskData != null) {
                Task task = new Task();
                task.setTask(taskData);
                task.setId(taskId);
                task.setDone(done);
                task.setTaskListId(taskList.getId());


                temp.get(taskList).add(task);
                taskList.setTasks(Collections.singletonList(task));
            }

            return taskList;
        }
    }

    private Long insertAndGetId(String sql, Map<Integer, Object> params) {
        var keyHolder = new GeneratedKeyHolder();
        Objects.requireNonNull(getJdbcTemplate()).update(conn -> {
            PreparedStatement statement = conn.prepareStatement(sql);
            for (Map.Entry<Integer, Object> entry : params.entrySet()) {
                var key = entry.getKey();
                var value = entry.getValue();
                statement.setObject(key, value);
            }
            return statement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
