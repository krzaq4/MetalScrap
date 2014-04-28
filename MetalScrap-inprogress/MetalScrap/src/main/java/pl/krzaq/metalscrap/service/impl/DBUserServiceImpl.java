package pl.krzaq.metalscrap.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.Executions;

import pl.krzaq.metalscrap.model.Role;
import pl.krzaq.metalscrap.service.DBUserService;


public class DBUserServiceImpl implements UserDetailsService {

	@Autowired
	private UserServiceImpl userService ;
	
	

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		String password = userService.getUserByLogin(username).getPassword() ;
		Set<Role> roles = userService.getUserByLogin(username).getRoles() ;
		
		Set<SimpleGrantedAuthority> authorities = new LinkedHashSet<SimpleGrantedAuthority>();
		//Set<GrantedAuthorityImpl> authorities = new HashSet<GrantedAuthorityImpl>() ;
		for (Role role:roles) {
			if(role!=null) {
				authorities.add(new SimpleGrantedAuthority(role.getName().toUpperCase())) ;
			}
			
		}
		
		
		User user = new User(username, password, authorities) ;
		
		return user ;
	}

	public UserServiceImpl getUserService() {
		return userService;
	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	
	

}
