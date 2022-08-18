package com.ratepay.bugtracker.api.dto;

import com.ratepay.bugtracker.model.PriorityType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BugResponse {

	private String id;

	private String reporter;

	private String project;

	private String summary;

	private PriorityType priority;

	private Long createdDate;

	private Long dueDate;

	private String assignee;

	private String description;

	private String attachment;
}
