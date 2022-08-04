package com.twitter.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twitter.domain.Useraccount;

public interface IUseraccountRepository extends JpaRepository<Useraccount, Integer> {

	Useraccount findByLoginIdAndPassword(String username, String password);

	Useraccount findByEmail(String userName);

}
