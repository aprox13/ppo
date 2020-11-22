package ru.ifkbhit.ppo4.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ifkbhit.ppo4.model.TaskList;

@Repository
public interface TaskListRepository extends CrudRepository<TaskList, Long> {
}