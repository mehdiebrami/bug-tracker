package com.ratepay.bugtracker.model;

import java.io.Serializable;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Project implements Serializable {
	@Id
	private String id;

	private String name;

	private String description;

}
