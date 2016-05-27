package springdatajpatalk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import springdatajpatalk.entities.Task;

public interface TaskRepository extends JpaRepository<Task, String> {

	
}
