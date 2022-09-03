package com.ratepay.bugtracker.service;

import java.util.List;

import com.ratepay.bugtracker.exception.NotFoundProjectException;
import com.ratepay.bugtracker.model.Project;


public interface ProjectService {

	String addProject(Project project);

	List<Project> getAllProjects();

	Project getProject(String id) throws NotFoundProjectException;

	void updateProject(Project project) throws NotFoundProjectException;

	void deleteProject(String id) throws NotFoundProjectException;
}
