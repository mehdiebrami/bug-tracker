package com.ratepay.bugtracker.service;

import java.util.List;

import com.ratepay.bugtracker.exception.NotFoundBugException;
import com.ratepay.bugtracker.model.Bug;


public interface BugService {

	String addBug(Bug bug);

	List<Bug> getAllBugs();

	Bug getBug(String id) throws NotFoundBugException;

	void updateBug(Bug bug) throws NotFoundBugException;

	void deleteBug(String id) throws NotFoundBugException;
}
