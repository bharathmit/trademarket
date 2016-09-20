package com.trademarket.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trademarket.model.UserModel;
import com.trademarket.model.UserTransfer;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	
	
   
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public @ResponseBody UserTransfer siginUser(@RequestBody UserModel userModel) {
		UserModel userDetails=new UserModel();
		if( userModel.getUsername().equalsIgnoreCase("user") && userModel.getPassword().equalsIgnoreCase("user") ){
			
			userDetails.setUsername("user");
			userDetails.setPassword("user");
			userDetails.addAuthorities(new SimpleGrantedAuthority("ROLE_USER"));
		}
		else if( userModel.getUsername().equalsIgnoreCase("admin") && userModel.getPassword().equalsIgnoreCase("admin")){
			
			userDetails.setUsername("admin");
			userDetails.setPassword("admin");
			userDetails.addAuthorities(new SimpleGrantedAuthority("ROLE_ADMIN"));
			userDetails.addAuthorities(new SimpleGrantedAuthority("ROLE_USER"));
		}
		else{
			throw new UsernameNotFoundException(userModel.getUsername()+" : Username not found");
		}
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, 
				userModel.getPassword(), userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		
		UserDetails user=((UserDetails) authentication.getPrincipal());
		
		Map<String, Boolean> roles = new HashMap<String, Boolean>();
		
		for (GrantedAuthority authority : user.getAuthorities()) {
			roles.put(authority.getAuthority(), Boolean.TRUE);
		}
		
		UserTransfer token=new UserTransfer(userModel.getUsername(),roles);
      
        return  token;
	}

}
