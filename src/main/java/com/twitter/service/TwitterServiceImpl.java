package com.twitter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.domain.Login;
import com.twitter.domain.Twitter;
import com.twitter.domain.Useraccount;

@Service
public class TwitterServiceImpl implements TwitterService {

	@Autowired
	private IUseraccountRepository repository;

	@Autowired
	private ITwitterRepository iTwitterRepository;

	@Override
	public Useraccount saveUseraccount(Useraccount useraccount) {
		return repository.save(useraccount);
	}

	@Override
	public Useraccount getUseraccountByUsernameAndPassword(Login login) {
		return repository.findByLoginIdAndPassword(login.getUsername(), login.getPassword());
	}

	@Override
	public List<Twitter> getAllPost() {
		return iTwitterRepository.findAll();
	}

	@Override
	public List<Twitter> searchPostByUsername(String userName) {
		return iTwitterRepository.findByUserName(userName);
	}

	@Override
	public Twitter saveNewPost(Twitter twitter) {
		return iTwitterRepository.save(twitter);
	}

	@Override
	public Twitter getTwitterById(Integer twitterId) {
		return iTwitterRepository.findById(twitterId).orElseThrow();
	}

	@Override
	public void deleteTweetById(Integer twitterId) {
		iTwitterRepository.deleteById(twitterId);
	}

	@Override
	public Useraccount searchUseraccoundByUsername(String userName) {
		// TODO Auto-generated method stub
		return  repository.findByEmail(userName);
	}

}
