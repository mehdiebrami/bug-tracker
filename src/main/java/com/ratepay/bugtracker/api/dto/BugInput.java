package com.ratepay.bugtracker.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BugInput {

	@NotEmpty
	private String reporter;

	@NotEmpty
	private String projectId;

	@NotEmpty
	private String summary;

	private PriorityTypeDto priority = PriorityTypeDto.MEDIUM;

	@NotNull
	private Long dueDate;

	@NotEmpty
	private String assignee;

	private String description;

	private String attachment;
}
