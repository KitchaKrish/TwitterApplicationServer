package com.twitter.ctrl;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.twitter.domain.FileUploadResponse;
import com.twitter.domain.OutputModel;
import com.twitter.domain.Twitter;
import com.twitter.domain.TwitterModel;
import com.twitter.domain.Useraccount;
import com.twitter.service.TwitterService;
import com.twitter.util.FileUploadUtil;

@RestController
@RequestMapping(value = "/api/twitter/flightctrl")
@CrossOrigin(origins = "*")
public class TwitterCtrl {

	@Autowired
	private TwitterService service;

	@GetMapping(value = "/test")
	public String test() {
		return "Hello World!!!!";
	}

	@PostMapping("/uploadFile")
	public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile)
			throws IOException {

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		long size = multipartFile.getSize();

		Random rand = new Random();
		int int1 = rand.nextInt(100000);
		String fileUploadName = Integer.toString(int1) + fileName;
		String filecode = FileUploadUtil.saveFile(fileUploadName, multipartFile);
		FileUploadResponse response = new FileUploadResponse();
		response.setFileName(fileName);
		response.setSize(size);
		response.setDownloadUri("feedImage/" + fileUploadName);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/addPost")
	public ResponseEntity<?> addPost(@RequestBody Twitter twitterModel) {
		OutputModel outputModel = new OutputModel();
		try {
			Twitter twitterSave = service.saveNewPost(twitterModel);
			if (twitterSave != null) {
				outputModel = new OutputModel(true, "Twitter save Successfully");
			} else {
				outputModel = new OutputModel(false, "Somthing went to wrong");
			}
		} catch (Exception e) {
			outputModel = new OutputModel(false, "Somthing went to wrong");
		}
		return ResponseEntity.ok(outputModel);
	}

	@GetMapping(value = "/getAllPost")
	public List<Twitter> getAllPost() {
		return service.getAllPost();
	}

	@GetMapping(value = "/searchPostByUsername/{userName}")
	public List<Twitter> searchPostByUsername(@PathVariable String userName) {
		return service.searchPostByUsername(userName);
	}

	@GetMapping(value = "/likePosts/{twitterId}")
	public String likePosts(@PathVariable Integer twitterId) {
		Twitter twitter = service.getTwitterById(twitterId);
		if (!twitter.getLikeCount().isEmpty()) {
			int i = Integer.parseInt(twitter.getLikeCount());
			i = i + 1;
			twitter.setLikeCount(String.valueOf(i));
			service.saveNewPost(twitter);
		} else {
			twitter.setLikeCount(String.valueOf(1));
			service.saveNewPost(twitter);
		}

		return "";
	}

	@DeleteMapping(value = "/deletepost/{twitterId}")
	public OutputModel deletepost(@PathVariable Integer twitterId) {
		service.deleteTweetById(twitterId);
		OutputModel outputModel = new OutputModel();
		return outputModel = new OutputModel(true, "Twitter save Successfully");

	}


}
