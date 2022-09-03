package com.ratepay.bugtracker.model.dao;

import com.ratepay.bugtracker.model.Project;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
}
