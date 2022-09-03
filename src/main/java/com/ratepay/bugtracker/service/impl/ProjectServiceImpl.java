package com.ratepay.bugtracker.service.impl;

import java.util.List;
import java.util.Optional;

import com.ratepay.bugtracker.exception.NotFoundProjectException;
import com.ratepay.bugtracker.model.Project;
import com.ratepay.bugtracker.model.dao.ProjectRepository;
import com.ratepay.bugtracker.service.ProjectService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {
	private final ProjectRepository repository;

	@Override
	public String addProject(Project project) {
		return repository.save(project).getId();
	}

	@Override
	public List<Project> getAllProjects() {
		return repository.findAll();
	}

	@Override
	public Project getProject(String id) throws NotFoundProjectException {
		return repository.findById(id).orElseThrow(() -> new NotFoundProjectException(id));
	}

	@Override
	public void updateProject(Project project) throws NotFoundProjectException {
		Optional<Project> projectOptional = repository.findById(project.getId());
		if (projectOptional.isPresent()) {
			repository.save(project);
		} else {
			throw new NotFoundProjectException((project.getId()));
		}
	}

	@Override
	public void deleteProject(String id) throws NotFoundProjectException {
		Optional<Project> projectOptional = repository.findById(id);
		if (projectOptional.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new NotFoundProjectException(id);
		}
	}
}
