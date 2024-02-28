package admin_user.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import admin_user.dto.TweetDto;
import admin_user.dto.UserDto;
import admin_user.model.User;
import admin_user.openfiegn.TweetClient;
import admin_user.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired(required = true)
	private TweetClient tweetClient;
	

	@Override
	public User save(UserDto userDto) {
		User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()) , userDto.getRole(), userDto.getFullname());
		return userRepository.save(user);
	}

	@Override
	public List<TweetDto> getTweetById(String email) {
		// TODO Auto-generated method stub
		
		
		
		
		User user=userRepository.findByEmail(email);
		
		UserDto userDto=modelMapper.map(user,UserDto.class);
		
	   ResponseEntity<List<TweetDto>> tweetResponse = tweetClient.getAllTweetsByEmail(email);
	   
	    List<TweetDto> tweetDtoList=tweetResponse.getBody();
	    
	    
	    userDto.setTweetDtoList(tweetDtoList);
		
		return userDto.getTweetDtoList();
	}
	
	

}
