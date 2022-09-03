package com.ratepay.bugtracker.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BugOutput {

	private String id;

	private String reporter;

	private String projectName;

	private String summary;

	private PriorityTypeDto priority;

	private Long createdDate;

	private Long dueDate;

	private String assignee;

	private String description;

	private String attachment;
}
