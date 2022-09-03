package com.ratepay.bugtracker.api.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectOutPut implements Serializable {

	private String id;

	private String name;

	private String description;

}
