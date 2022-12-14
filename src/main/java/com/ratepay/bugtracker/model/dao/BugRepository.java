package com.ratepay.bugtracker.model.dao;

import java.util.List;

import com.ratepay.bugtracker.model.Bug;
import com.ratepay.bugtracker.model.Project;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends MongoRepository<Bug, String> {

	List<Bug> findAllByProject(Project project);
}
