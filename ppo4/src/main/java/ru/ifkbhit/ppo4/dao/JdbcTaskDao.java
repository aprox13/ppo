//package ru.ifkbhit.ppo4.dao;
//
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.support.JdbcDaoSupport;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import ru.ifkbhit.ppo4.model.Task;
//import ru.ifkbhit.ppo4.model.TaskList;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.*;
//
//
//public class JdbcTaskDao extends JdbcDaoSupport implements TaskDao {
//
//    final DataSource dataSource;
//
//    public JdbcTaskDao(DataSource dataSource) throws IOException {
//        this.dataSource = dataSource;
//        setDataSource(dataSource);
//
//        URL initFileUri = Objects.requireNonNull(
//                getClass().getClassLoader().getResource("sql/create.sql")
//        );
//        Arrays.stream(String.join("", Files.readAllLines(Paths.get(initFileUri.getPath())))
//                .split(";")).forEach(getJdbcTemplate()::execute);
//    }
//
//    @Override
//    public void setDone(Task task) {
//        String sql = "UPDATE Task SET DONE = ? WHERE ID = ?";
//        getJdbcTemplate().update(sql, task.isDone(), task.getId());
//    }
//
//    @Override
//    public void deleteTask(long id) {
//        String sql = "DELETE FROM Task WHERE ID = ?";
//        getJdbcTemplate().update(sql, id);
//    }
//
//    @Override
//    public void deleteTaskList(long id) {
//        String sql = "DELETE FROM TaskList WHERE ID = ?";
//        getJdbcTemplate().update(sql, id);
//    }
//
//    @Override
//    public TaskList addList(TaskList taskList) {
//        String sql = "INSERT INTO TaskList(NAME) VALUES (?)";
//        Map<Integer, Object>  params = Map.of(1, taskList.getName());
//
//        var id = insertAndGetId(sql, params);
//        taskList.setId(id);
//
//        return taskList;
//    }
//
//    @Override
//    public Task addTask(Task task) {
//        String sql = "INSERT INTO Task(TASK, DONE, LIST_ID) VALUES (?, ?, ?)";
//        Map<Integer, Object>  params = Map.of(1, task.getTask(), 2, task.isDone(), 3, task.getListId());
//
//        var id = insertAndGetId(sql, params);
//        task.setId(id);
//
//        return task;
//    }
//
//    @Override
//    public List<TaskList> getLists() {
//        String sql = "SELECT Task.ID AS tId, Task.TASK AS task, Task.DONE AS done, TaskList.ID AS tlId, TaskList.NAME AS name FROM TaskList LEFT JOIN Task ON Task.LIST_ID = TaskList.ID";
//        List<TaskList> lists = getJdbcTemplate().query(sql, new TaskListMapper());
//
//        Map<Long, TaskList> temp = new HashMap<>();
//        lists.forEach(list -> {
//            temp.computeIfPresent(list.getId(), (key, value) -> value.addTasks(list.getTasks()));
//            temp.putIfAbsent(list.getId(), list);
//        });
//
//        return new ArrayList<>(temp.values());
//    }
//
//
//    private static class TaskListMapper implements RowMapper<TaskList> {
//
//        @Override
//        public TaskList mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Map<TaskList, List<Task>> temp = new HashMap<>();
//
//            TaskList taskList =
//                    new TaskList(
//                            rs.getLong("tlId"),
//                            rs.getString("name")
//                    );
//
//            temp.putIfAbsent(taskList, new ArrayList<>());
//
//            long taskId = rs.getLong("tId");
//            String taskData = rs.getString("task");
//            boolean done = rs.getBoolean("done");
//
//            if (taskData != null) {
//                Task task = new Task(taskData, done, taskId, taskList.getId());
//                temp.get(taskList).add(task);
//
//                taskList.setTasks(Collections.singletonList(task));
//            }
//
//            return taskList;
//        }
//    }
//
//    private Long insertAndGetId(String sql, Map<Integer, Object> params) {
//        var keyHolder = new GeneratedKeyHolder();
//        getJdbcTemplate().update(conn -> {
//            PreparedStatement statement = conn.prepareStatement(sql);
//            for (Map.Entry<Integer, Object> entry : params.entrySet()) {
//                var key = entry.getKey();
//                var value = entry.getValue();
//                statement.setObject(key, value);
//            }
//            return statement;
//        }, keyHolder);
//
//        return keyHolder.getKey().longValue();
//    }
//}
