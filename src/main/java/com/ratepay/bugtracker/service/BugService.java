package com.ratepay.bugtracker.service;

import java.util.List;

import com.ratepay.bugtracker.exception.NotFoundBugException;
import com.ratepay.bugtracker.exception.NotFoundProjectException;
import com.ratepay.bugtracker.model.Bug;


public interface BugService {

	String addBug(Bug bug) throws NotFoundBugException, NotFoundProjectException;

	List<Bug> getAllBugs();

	List<Bug> getBugsByProject(String projectId) throws NotFoundBugException, NotFoundProjectException;

	Bug getBug(String id) throws NotFoundBugException;

	void updateBug(Bug bug) throws NotFoundBugException, NotFoundProjectException;

	void deleteBug(String id) throws NotFoundBugException;
}
