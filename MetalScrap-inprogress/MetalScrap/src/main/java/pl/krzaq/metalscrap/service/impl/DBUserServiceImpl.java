package pl.krzaq.metalscrap.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.Executions;

import pl.krzaq.metalscrap.model.Role;
import pl.krzaq.metalscrap.service.DBUserService;


public class DBUserServiceImpl implements UserDetailsService {

	private UserServiceImpl userService ;
	
	

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		String password = userService.getUserByLogin(username).getPassword() ;
		Set<Role> roles = userService.getUserByLogin(username).getRoles() ;
		
		Set<GrantedAuthorityImpl> authorities = new HashSet<GrantedAuthorityImpl>() ;
		Iterator<Role> i = roles.iterator() ;
		
		while(i.hasNext()) {
			
			authorities.add(new GrantedAuthorityImpl(i.next().getName())) ;
			
		}
		
		
		User user = new User(username, password, authorities) ;
		Executions.getCurrent().getSession().setAttribute("currentUser", userService.getUserByLogin(username)) ;
		return user ;
	}

	public UserServiceImpl getUserService() {
		return userService;
	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	
	

}
