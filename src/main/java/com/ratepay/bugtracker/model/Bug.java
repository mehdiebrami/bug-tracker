package com.ratepay.bugtracker.model;

import java.io.Serializable;
import java.time.Instant;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@Document
public class Bug implements Serializable {
	@Id
	private String id;

	private String reporter;

	@DocumentReference
	private Project project;

	private String summary;

	private PriorityType priority;

	@Setter(AccessLevel.NONE)
	private Long createdDate = Instant.now().toEpochMilli();

	private Long dueDate;

	private String assignee;

	private String description;

	private String attachment;

}
