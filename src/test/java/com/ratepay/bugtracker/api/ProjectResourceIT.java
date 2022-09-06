package com.ratepay.bugtracker.api;

import java.util.Optional;

import com.ratepay.bugtracker.api.dto.ProjectInput;
import com.ratepay.bugtracker.api.dto.UpdateProjectInput;
import com.ratepay.bugtracker.model.Project;
import com.ratepay.bugtracker.model.dao.ProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DisplayName("Project Resource Integration test")
@ContextConfiguration(initializers = ContainerInit.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjectResourceIT {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;


	@Autowired
	private ProjectRepository projectRepository;

	@AfterEach
	void tearDown() {
		projectRepository.deleteAll();
	}

	@DisplayName("Add new project")
	@Test
	void addProject_success() {
		ProjectInput projectInput = new ProjectInput();
		projectInput.setName("RatePay");

		String url = String.format("http://localhost:%d/bug-tracker/api/projects", port);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(projectInput),
				String.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		String projectId = response.getBody();
		assertThat(projectId).isNotNull();
		Assertions.assertThat(projectId).isNotNull();
		Optional<Project> project = projectRepository.findById(projectId);
		Assertions.assertThat(project.isPresent()).isTrue();
		Assertions.assertThat(project.get().getId()).isNotNull();
		Assertions.assertThat(project.get().getName()).isEqualTo(projectInput.getName());

	}

	@DisplayName("Add new project with validation error")
	@Test
	void addProject_validation_failed() {
		ProjectInput projectInput = new ProjectInput();

		String url = String.format("http://localhost:%d/bug-tracker/api/projects", port);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(projectInput),
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}


	@DisplayName("Update a project with not found error")
	@Test
	void updateProject_notFound() {
		UpdateProjectInput updateProjectInput = new UpdateProjectInput();
		updateProjectInput.setId("62fd44ad63ad587a21dbe7d9");
		updateProjectInput.setName("Wrong balance! after pay");

		String url = String.format("http://localhost:%d/bug-tracker/api/projects", port);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(updateProjectInput),
				String.class);
		assertThat(response).isNotNull();
		assertThat(response.getBody()).isEqualTo("This project does not exist: 62fd44ad63ad587a21dbe7d9");
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
