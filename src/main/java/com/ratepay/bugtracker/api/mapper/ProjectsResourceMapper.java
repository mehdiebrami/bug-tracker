package com.ratepay.bugtracker.api.mapper;

import java.util.List;

import com.ratepay.bugtracker.api.dto.ProjectInput;
import com.ratepay.bugtracker.api.dto.ProjectOutPut;
import com.ratepay.bugtracker.api.dto.UpdateProjectInput;
import com.ratepay.bugtracker.model.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectsResourceMapper {


	Project toProject(ProjectInput request);

	Project toProject(UpdateProjectInput request);

	List<ProjectOutPut> toProjectResponseList(List<Project> allProjects);

	ProjectOutPut toProjectOutput(Project project);
}
