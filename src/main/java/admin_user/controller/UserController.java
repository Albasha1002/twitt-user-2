package admin_user.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import admin_user.dto.TweetDto;
import admin_user.dto.UserDto;
import admin_user.service.UserService;

@Controller
public class UserController {
	
	@Value("${server.servlet.context-path}")
    private String contextPath;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/registration")
	public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
		return "register";
	}
	
	@PostMapping("/registration")
	public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
		userService.save(userDto);
		model.addAttribute("message", "Registered Successfuly!");
		return "register";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/user-page")
	public String userPage (Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
		   String homeUrl = contextPath + "/user";
		return homeUrl;
	}
	
	@GetMapping("/admin-page")
	public String adminPage (Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
		  
		return "admin";
	}
	
    @GetMapping("/{email}")
    public ResponseEntity<List<admin_user.dto.TweetDto>> getAllTweetsByEmail(@PathVariable String email) {
        List<TweetDto> tweetDTOList = userService.getTweetById(email);
        return ResponseEntity.ok(tweetDTOList);
    }
    

    @GetMapping("/altweets")
    public String getAltweets(Model model) {
        // Get the currently authenticated user's authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extract the email from the authentication object
        String email = authentication.getName(); // Assuming email is stored as the principal username

        // Retrieve tweets associated with the user's email
        List<TweetDto> altweets = userService.getTweetById(email);

        // Add tweets to the model
        model.addAttribute("altweets", altweets);
        
      

        // Return the name of the Thymeleaf template to render
        return "tweet";
    }
    
    

}
