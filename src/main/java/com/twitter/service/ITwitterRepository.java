package com.twitter.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twitter.domain.Twitter;

public interface ITwitterRepository extends JpaRepository<Twitter, Integer> {

	List<Twitter> findByUserName(String userName);

}
