package admin_user.openfiegn;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import admin_user.dto.TweetDto;

@FeignClient(name = "tweet-app", url = "http://localhost:8081", path = "/tweet-app/api/tweets/")
public interface TweetClient {
	
	 @GetMapping("/{email}")
	 public ResponseEntity<List<TweetDto>> getAllTweetsByEmail(@PathVariable String email);
	

}
