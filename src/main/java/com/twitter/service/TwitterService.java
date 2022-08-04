package com.twitter.service;

import java.util.List;

import com.twitter.domain.Login;
import com.twitter.domain.Twitter;
import com.twitter.domain.Useraccount;

public interface TwitterService {

	public Useraccount saveUseraccount(Useraccount useraccount);

	public Useraccount getUseraccountByUsernameAndPassword(Login login);
	
	public List<Twitter> getAllPost();

	public List<Twitter> searchPostByUsername(String userName);

	public Twitter saveNewPost(Twitter twitter);

	public Twitter getTwitterById(Integer twitterId);

	public void deleteTweetById(Integer twitterId);

	public Useraccount searchUseraccoundByUsername(String userName);
	
}
