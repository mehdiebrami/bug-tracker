package com.ratepay.bugtracker.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.ratepay.bugtracker.exception.NotFoundBugException;
import com.ratepay.bugtracker.exception.NotFoundProjectException;
import com.ratepay.bugtracker.model.Bug;
import com.ratepay.bugtracker.model.Project;
import com.ratepay.bugtracker.model.dao.BugRepository;
import com.ratepay.bugtracker.service.BugService;
import com.ratepay.bugtracker.service.ProjectService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BugServiceImpl implements BugService {
	private final BugRepository bugRepository;

	private final ProjectService projectService;

	@Override
	public String addBug(Bug bug) throws NotFoundProjectException {
		Project project = projectService.getProject(bug.getProject().getId());
		return Objects.nonNull(project) ? bugRepository.save(bug).getId() : null;
	}

	@Override
	public List<Bug> getAllBugs() {
		return bugRepository.findAll();
	}

	@Override
	public List<Bug> getBugsByProject(String projectId) throws NotFoundProjectException {
		Project project = projectService.getProject(projectId);
		return bugRepository.findAllByProject(project);
	}

	@Override
	public Bug getBug(String id) throws NotFoundBugException {
		return bugRepository.findById(id).orElseThrow(() -> new NotFoundBugException(id));
	}

	@Override
	public void updateBug(Bug bug) throws NotFoundBugException, NotFoundProjectException {
		Optional<Bug> bugOptional = bugRepository.findById(bug.getId());
		if (bugOptional.isPresent()) {
			Project project = projectService.getProject(bug.getProject().getId());
			bug.setProject(project);
			bugRepository.save(bug);
		} else {
			throw new NotFoundBugException(bug.getId());
		}
	}

	@Override
	public void deleteBug(String id) throws NotFoundBugException {
		Optional<Bug> bugOptional = bugRepository.findById(id);
		if (bugOptional.isPresent()) {
			bugRepository.deleteById(id);
		} else {
			throw new NotFoundBugException(id);
		}
	}
}
