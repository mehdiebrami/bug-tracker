package com.ratepay.bugtracker.api;

import java.util.Date;
import java.util.Optional;

import com.ratepay.bugtracker.api.dto.BugInput;
import com.ratepay.bugtracker.api.dto.PriorityTypeDto;
import com.ratepay.bugtracker.api.dto.ProjectInput;
import com.ratepay.bugtracker.api.dto.UpdateBugInput;
import com.ratepay.bugtracker.api.dto.UpdateProjectInput;
import com.ratepay.bugtracker.model.Bug;
import com.ratepay.bugtracker.model.Project;
import com.ratepay.bugtracker.model.dao.BugRepository;
import com.ratepay.bugtracker.model.dao.ProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DisplayName("Bug Resource Integration test")
class ResourceIT extends AbstractIT {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private BugRepository bugRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@AfterEach
	void tearDown() {
		bugRepository.deleteAll();
		projectRepository.deleteAll();
	}

	@DisplayName("Add new bug")
	@Test
	void addBug_success() {
		Project project = new Project();
		project.setName("RatePay");
		projectRepository.save(project);
		long time = new Date().getTime();
		BugInput bugInput = new BugInput();
		bugInput.setAssignee("MEHDI_DEV");
		bugInput.setDueDate(time);
		bugInput.setPriority(PriorityTypeDto.CRITICAL);
		bugInput.setProjectId(project.getId());
		bugInput.setSummary("Wrong balance!");
		bugInput.setReporter("MR. EBRAHIMI");
		bugInput.setDescription("Wrong balance! after pay");

		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(bugInput),
				String.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		String bugId = response.getBody();
		assertThat(bugId).isNotNull();
		Optional<Bug> bug = bugRepository.findById(bugId);
		assertThat(bug).isPresent();
		assertThat(bug.get().getReporter()).isEqualTo(bugInput.getReporter());
		assertThat(bug.get().getProject().getId()).isEqualTo(bugInput.getProjectId());

	}

	@DisplayName("Add new bug with validation error")
	@Test
	void addBug_validation_failed() {
		long time = new Date().getTime();
		BugInput bugInput = new BugInput();
		bugInput.setAssignee("MEHDI_DEV");
		bugInput.setDueDate(time);
		bugInput.setPriority(PriorityTypeDto.LOW);
		bugInput.setProjectId("RatePay");
		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(bugInput),
				Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@DisplayName("Update a bug")
	@Test
	void updateBug_success() {
		Project project = new Project();
		project.setName("RatePay");
		projectRepository.save(project);

		Bug persistedBug = new Bug();
		Bug save = bugRepository.save(persistedBug);

		long time = new Date().getTime();
		UpdateBugInput bugInput = new UpdateBugInput();
		bugInput.setId(save.getId());
		bugInput.setAssignee("MEHDI_DEV");
		bugInput.setDueDate(time);
		bugInput.setPriority(PriorityTypeDto.CRITICAL);
		bugInput.setProjectId(project.getId());
		bugInput.setSummary("Wrong balance!");
		bugInput.setReporter("MR. EBRAHIMI");
		bugInput.setDescription("Wrong balance! after pay");


		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(bugInput),
				String.class);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Optional<Bug> bug = bugRepository.findById(save.getId());
		Assertions.assertThat(bug).isPresent();
		Assertions.assertThat(bug.get().getReporter()).isEqualTo(bugInput.getReporter());
		Assertions.assertThat(bug.get().getProject().getId()).isEqualTo(bugInput.getProjectId());
		Assertions.assertThat(bug.get().getDescription()).isEqualTo(bugInput.getDescription());
	}

	@DisplayName("Update a bug with validation error")
	@Test
	void updateBug_validation_failed() {
		long time = new Date().getTime();
		BugInput bugInput = new BugInput();
		bugInput.setAssignee("MEHDI_DEV");
		bugInput.setDueDate(time);

		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(bugInput),
				Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@DisplayName("Update a bug with not found error")
	@Test
	void updateBug_notFound() {
		long time = new Date().getTime();
		UpdateBugInput updateBugInput = new UpdateBugInput();
		updateBugInput.setId("62fd44ad63ad587a21dbe7d9");
		updateBugInput.setAssignee("MEHDI_DEV");
		updateBugInput.setDueDate(time);
		updateBugInput.setPriority(PriorityTypeDto.CRITICAL);
		updateBugInput.setProjectId("RatePay");
		updateBugInput.setSummary("Wrong balance!");
		updateBugInput.setReporter("MR. EBRAHIMI");
		updateBugInput.setDescription("Wrong balance! after pay");

		String url = String.format("http://localhost:%d/bug-tracker/api/bugs", port);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(updateBugInput),
				String.class);
		assertThat(response).isNotNull();
		assertThat(response.getBody()).isEqualTo("This bug does not exist: 62fd44ad63ad587a21dbe7d9");
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
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
		Optional<Project> project = projectRepository.findById(projectId);
		assertThat(project).isPresent();
		assertThat(project.get().getId()).isNotNull();
		assertThat(project.get().getName()).isEqualTo(projectInput.getName());

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
