package springdatajpatalk.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import springdatajpatalk.entities.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, String> {

	Developer findByNickname(String string);

	@Query("from Developer order by nickname")
	List<Developer> findAllOrderByNickname();

	List<Developer> findAllByOrderByNickname();
	
}
