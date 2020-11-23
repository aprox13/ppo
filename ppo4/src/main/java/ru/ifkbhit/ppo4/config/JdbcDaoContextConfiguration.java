package ru.ifkbhit.ppo4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.ifkbhit.ppo4.dao.JdbcTaskDao;
import ru.ifkbhit.ppo4.dao.TaskDao;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class JdbcDaoContextConfiguration {
    @Bean
    public TaskDao taskDao(DataSource dataSource) throws IOException {
        return new JdbcTaskDao(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:task.db");
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }
}