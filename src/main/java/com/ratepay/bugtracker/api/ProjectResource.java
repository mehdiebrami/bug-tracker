package com.ratepay.bugtracker.api;

import java.util.List;

import javax.validation.Valid;

import com.ratepay.bugtracker.api.dto.ProjectInput;
import com.ratepay.bugtracker.api.dto.ProjectOutPut;
import com.ratepay.bugtracker.api.dto.UpdateProjectInput;
import com.ratepay.bugtracker.api.mapper.ProjectsResourceMapper;
import com.ratepay.bugtracker.exception.NotFoundProjectException;
import com.ratepay.bugtracker.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Projects resource")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/projects")
public class ProjectResource {

	private final ProjectService service;

	private final ProjectsResourceMapper mapper;

	@Operation(summary = "Add new project")
	@PostMapping
	public ResponseEntity<String> addProject(@RequestBody @Valid ProjectInput request) {
		return ResponseEntity.ok(service.addProject(mapper.toProject(request)));
	}

	@Operation(summary = "Update a project")
	@PutMapping
	public ResponseEntity<Void> updateProject(@RequestBody @Valid UpdateProjectInput request) throws NotFoundProjectException {
		service.updateProject(mapper.toProject(request));
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Get all projects")
	@GetMapping
	public ResponseEntity<List<ProjectOutPut>> getAllProjects() {
		return ResponseEntity.ok(mapper.toProjectResponseList(service.getAllProjects()));
	}

	@Operation(summary = "Get a project by id")
	@GetMapping("/{projectId}")
	public ResponseEntity<ProjectOutPut> getProject(@PathVariable String projectId) throws NotFoundProjectException {
		return ResponseEntity.ok(mapper.toProjectOutput(service.getProject(projectId)));
	}

	@Operation(summary = "Delete a project by id")
	@DeleteMapping("/{projectId}")
	public ResponseEntity<Void> deleteProject(@PathVariable("projectId") String projectId) throws NotFoundProjectException {
		service.deleteProject(projectId);
		return ResponseEntity.ok().build();
	}

}
