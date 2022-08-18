package com.ratepay.bugtracker.service.impl;

import java.util.List;
import java.util.Optional;

import com.ratepay.bugtracker.exception.NotFoundBugException;
import com.ratepay.bugtracker.model.Bug;
import com.ratepay.bugtracker.model.dao.BugRepository;
import com.ratepay.bugtracker.service.BugService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BugServiceImpl implements BugService {
	private final BugRepository repository;

	@Override
	public String addBug(Bug bug) {
		return repository.save(bug).getId();
	}

	@Override
	public List<Bug> getAllBugs() {
		return repository.findAll();
	}

	@Override
	public Bug getBug(String id) throws NotFoundBugException {
		return repository.findById(id).orElseThrow(() -> new NotFoundBugException(id));
	}

	@Override
	public void updateBug(Bug bug) throws NotFoundBugException {
		Optional<Bug> bugOptional = repository.findById(bug.getId());
		if (bugOptional.isPresent()) {
			repository.save(bug);
		} else {
			throw new NotFoundBugException(bug.getId());
		}
	}

	@Override
	public void deleteBug(String id) throws NotFoundBugException {
		Optional<Bug> bugOptional = repository.findById(id);
		if (bugOptional.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new NotFoundBugException(id);
		}
	}
}
