package com.ratepay.bugtracker.api.mapper;

import java.util.List;

import com.ratepay.bugtracker.api.dto.BugRequest;
import com.ratepay.bugtracker.api.dto.BugResponse;
import com.ratepay.bugtracker.api.dto.UpdateBugRequest;
import com.ratepay.bugtracker.model.Bug;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BugResourceMapper {


	Bug toBug(BugRequest request);

	Bug toBug(UpdateBugRequest request);

	List<BugResponse> toBugResponseList(List<Bug> allBugs);

	BugResponse toBugResponse(Bug bug);
}
