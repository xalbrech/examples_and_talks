package springdatajpa_demo;

import java.util.Arrays;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import springdatajpatalk.entities.Developer;
import springdatajpatalk.entities.Task;
import springdatajpatalk.repositories.DeveloperRepository;
import springdatajpatalk.repositories.TaskRepository;
import springdatajpatalk.spring.SpringConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
@Transactional
public class DemoTest {
	
	@Inject DeveloperRepository developerRepository;
	@Inject TaskRepository taskRepository;

	@Before
	public void setUp() {
		Developer homer = developerRepository.save(new Developer("Homer", "null"));
		taskRepository.save(new Task("fix it!", homer));
		developerRepository.save(new Developer("Bart", "pointer"));
		developerRepository.save(new Developer("Lisa", "exception"));
		developerRepository.save(new Developer("Barney", "error"));
		developerRepository.flush();
	}
	
	@Test
	public void testFindAllDevelopers() {
		System.out.println(developerRepository.findOne("Homer"));
		System.out.println(developerRepository.findAll(Arrays.asList("Homer", "Lisa")));
		System.out.println(developerRepository.findAll());
	}
	
	@Test
	public void testSomeMoreFinders() {
		// find by nickname
		System.out.println(developerRepository.findByNickname("exception"));
		
		// find by containing letter
		// find and order
		System.out.println(developerRepository.findAllByOrderByNickname());
		
		// find task by developer
	}
	
	
}
