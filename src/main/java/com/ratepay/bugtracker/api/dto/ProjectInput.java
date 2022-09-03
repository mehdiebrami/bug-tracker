package com.ratepay.bugtracker.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectInput implements Serializable {

	@NotEmpty
	private String name;

	private String description;

}
