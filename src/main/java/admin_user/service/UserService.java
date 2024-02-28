package admin_user.service;

import java.util.List;

import admin_user.dto.TweetDto;
import admin_user.dto.UserDto;
import admin_user.model.User;

public interface UserService {
	
	User save (UserDto userDto);
	
	List<TweetDto> getTweetById(String email);
	

}
