package com.hemant.oauth2.config.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	Map<String, User> userMap;
	
	@PostConstruct
	public void initUsers() {
		// admin@gmail.com -- admin -- role=ADMIN, so he is admin user
		User adminUser = new User("admin@gmail.com", "admin", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
		// normal@gmail.com -- normal -- role=NORMAL, so he is normal user
		User normalUser = new User("normal@gmail.com", "normal", Arrays.asList(new SimpleGrantedAuthority("ROLE_NORMAL")));

		userMap = new HashMap<>();
		userMap.put(adminUser.getUsername(), adminUser);
		userMap.put(normalUser.getUsername(), normalUser);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMap.get(username);
		if(null == user) {
			return null;
		}
		return new User(username, user.getPassword(), user.getAuthorities());
	}
}
