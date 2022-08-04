package com.twitter.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.domain.Login;
import com.twitter.domain.OutputModel;
import com.twitter.domain.Useraccount;
import com.twitter.service.TwitterService;

@RestController
@RequestMapping(value = "/api/twitter/registerctrl")
@CrossOrigin(origins = "*")
public class RegisterCtrl {

	@Autowired
	private TwitterService twitterService;

	@GetMapping(value = "/test")
	public String test() {
		return "Hello World!!!!";
	}

	@PostMapping(value = "/register")
	public ResponseEntity<?> createAccount(@RequestBody Useraccount useraccount) {
		OutputModel outputModel = new OutputModel();
		try {
			Useraccount user = twitterService.saveUseraccount(useraccount);
			if (user != null) {
				outputModel = new OutputModel(true, "User Register Successfully");
			} else {
				outputModel = new OutputModel(false, "Somthing went to wrong");

			}
		} catch (Exception e) {
			outputModel = new OutputModel(true, "User Register Successfully");

		}
		return ResponseEntity.ok(outputModel);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody Login login) {
		OutputModel outputModel = new OutputModel();
		try {
			Useraccount useraccount = twitterService.getUseraccountByUsernameAndPassword(login);
			if (useraccount != null) {
				return ResponseEntity.ok(useraccount);
			} else {
				return ResponseEntity.ok(outputModel);
			}
		} catch (Exception e) {
			outputModel = new OutputModel(false, "Login Failed");

		}
		return ResponseEntity.ok(outputModel);

	}

	@GetMapping(value = "/searchByUsername/{userName}")
	public OutputModel searchByUsername(@PathVariable String userName) {
		Useraccount useraccount = twitterService.searchUseraccoundByUsername(userName);
		OutputModel outputModel = new OutputModel();
		if (useraccount != null) {
			outputModel = new OutputModel(true, "UserEmail found");
		} else {
			outputModel = new OutputModel(false, "Email Not found");
		}
		return outputModel;

	}

	@GetMapping(value = "/changePassword/{userName}/{password}")
	public OutputModel changePassword(@PathVariable String userName, @PathVariable String password) {
		Useraccount useraccount = twitterService.searchUseraccoundByUsername(userName);

		OutputModel outputModel = new OutputModel();
		if (useraccount != null) {
			useraccount.setPassword(password);
			Useraccount accouUseraccount = twitterService.saveUseraccount(useraccount);
			outputModel = new OutputModel(true, "Password Update Successfully");
		} else {
			outputModel = new OutputModel(false, " Not found");
		}
		return outputModel;

	}

}
