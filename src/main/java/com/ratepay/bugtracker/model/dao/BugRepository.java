package com.ratepay.bugtracker.model.dao;

import com.ratepay.bugtracker.model.Bug;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends MongoRepository<Bug, String> {
}
