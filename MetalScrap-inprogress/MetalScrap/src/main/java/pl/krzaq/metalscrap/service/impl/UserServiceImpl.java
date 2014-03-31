package pl.krzaq.metalscrap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Role;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.UserService;
import pl.krzaq.metalscrap.dao.UserDAO; 


public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO ;
	

	@Override
	public User getUserByLogin(String login) {
		
		return userDAO.getUserByLogin(login) ;
	}

	@Override
	public User getUserByLoginAndPass(String login, String pass) {
		return userDAO.getUserByLoginAndPass(login, pass) ;
	}

	
	@Override 
	public User getLoggedinUser() {
		User res = null ;
		if (SecurityContextHolder.getContext().getAuthentication()!=null) {
			res = userDAO.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName()) ;
		} 
		
		return res ;
	}
	
	//-----------------------------------------------------------------------------
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void save(User user) {
		userDAO.saveUser(user);
		
	}

	@Override
	public void update(User user) {
		userDAO.update(user);
		
	}

	@Override
	public void delete(User user) {
		userDAO.delete(user);
	}

	@Override
	public User getUserByEmail(String email) {
		return userDAO.getUserByEmail(email) ;
	}

	@Override
	public User getUserById(Long id) {
		return userDAO.getUserById(id) ;
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return userDAO.getUsers() ;
	}

	@Override
	public Role getRoleByName(String name) {
		// TODO Auto-generated method stub
		return userDAO.getRoleByName(name) ;
	}
	
	

}
