package com.ratepay.bugtracker.api;

import java.util.List;

import javax.validation.Valid;

import com.ratepay.bugtracker.api.dto.BugInput;
import com.ratepay.bugtracker.api.dto.BugOutput;
import com.ratepay.bugtracker.api.dto.UpdateBugInput;
import com.ratepay.bugtracker.api.mapper.BugResourceMapper;
import com.ratepay.bugtracker.exception.NotFoundBugException;
import com.ratepay.bugtracker.exception.NotFoundProjectException;
import com.ratepay.bugtracker.service.BugService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Bugs resource")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/bugs")
public class BugResource {

	private final BugService service;

	private final BugResourceMapper mapper;

	@Operation(summary = "Add new bug")
	@PostMapping
	public ResponseEntity<String> addBug(@RequestBody @Valid BugInput request) throws NotFoundBugException, NotFoundProjectException {
		return ResponseEntity.ok(service.addBug(mapper.toBug(request)));
	}

	@Operation(summary = "Update a bug")
	@PutMapping
	public ResponseEntity<Void> updateBug(@RequestBody @Valid UpdateBugInput request) throws NotFoundBugException, NotFoundProjectException {
		service.updateBug(mapper.toBug(request));
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Get all bugs")
	@GetMapping
	public ResponseEntity<List<BugOutput>> getAllBugs() {
		return ResponseEntity.ok(mapper.toBugResponseList(service.getAllBugs()));
	}

	@Operation(summary = "Get a bug by id")
	@GetMapping("/{bugId}")
	public ResponseEntity<BugOutput> getBug(@PathVariable String bugId) throws NotFoundBugException {
		return ResponseEntity.ok(mapper.toBugOutput(service.getBug(bugId)));
	}

	@Operation(summary = "Get all bugs by specific project")
	@GetMapping("/project/{projectId}")
	public ResponseEntity<List<BugOutput>> getBugsByProject(@PathVariable String projectId) throws NotFoundBugException, NotFoundProjectException {
		return ResponseEntity.ok(mapper.toBugResponseList(service.getBugsByProject(projectId)));
	}

	@Operation(summary = "Delete a bug by id")
	@DeleteMapping("/{bugId}")
	public ResponseEntity<Void> deleteBug(@PathVariable("bugId") String bugId) throws NotFoundBugException {
		service.deleteBug(bugId);
		return ResponseEntity.ok().build();
	}

}
