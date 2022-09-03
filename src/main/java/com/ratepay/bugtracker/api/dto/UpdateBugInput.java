package com.ratepay.bugtracker.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBugInput extends BugInput {
	@NotEmpty
	private String id;

}
