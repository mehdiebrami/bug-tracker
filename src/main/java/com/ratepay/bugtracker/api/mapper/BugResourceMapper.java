package com.ratepay.bugtracker.api.mapper;

import java.util.List;

import com.ratepay.bugtracker.api.dto.BugInput;
import com.ratepay.bugtracker.api.dto.BugOutput;
import com.ratepay.bugtracker.api.dto.UpdateBugInput;
import com.ratepay.bugtracker.model.Bug;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BugResourceMapper {

	@Mapping(target = "project.id", source = "projectId")
	Bug toBug(BugInput request);

	@Mapping(target = "project.id", source = "projectId")
	Bug toBug(UpdateBugInput request);

	List<BugOutput> toBugResponseList(List<Bug> allBugs);

	@Mapping(target = "projectName", source = "project.name")
	BugOutput toBugOutput(Bug bug);
}
