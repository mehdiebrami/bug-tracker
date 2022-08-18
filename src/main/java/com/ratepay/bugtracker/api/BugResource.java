package com.ratepay.bugtracker.api;

import java.util.List;

import javax.validation.Valid;

import com.ratepay.bugtracker.api.mapper.BugResourceMapper;
import com.ratepay.bugtracker.api.dto.BugRequest;
import com.ratepay.bugtracker.api.dto.BugResponse;
import com.ratepay.bugtracker.api.dto.UpdateBugRequest;
import com.ratepay.bugtracker.exception.NotFoundBugException;
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

@Tag(name = "Bug resource")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/bugs")
public class BugResource {

	private final BugService service;

	private final BugResourceMapper mapper;

	@Operation(summary = "Add new bug")
	@PostMapping
	public ResponseEntity<String> addBug(@RequestBody @Valid BugRequest request) {
		return ResponseEntity.ok(service.addBug(mapper.toBug(request)));
	}

	@Operation(summary = "Update a bug")
	@PutMapping()
	public ResponseEntity<Void> updateBug(@RequestBody @Valid UpdateBugRequest request) throws NotFoundBugException {
		service.updateBug(mapper.toBug(request));
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Get all bugs")
	@GetMapping()
	public ResponseEntity<List<BugResponse>> getAllBugs() {
		return ResponseEntity.ok(mapper.toBugResponseList(service.getAllBugs()));
	}

	@Operation(summary = "Get a bug by id")
	@GetMapping("/{bugId}")
	public ResponseEntity<BugResponse> getBug(@PathVariable String bugId) throws NotFoundBugException {
		return ResponseEntity.ok(mapper.toBugResponse(service.getBug(bugId)));
	}

	@Operation(summary = "Delete a bug by id")
	@DeleteMapping("/{bugId}")
	public ResponseEntity<Void> deleteBug(@PathVariable("bugId") String bugId) throws NotFoundBugException {
		service.deleteBug(bugId);
		return ResponseEntity.ok().build();
	}

}
