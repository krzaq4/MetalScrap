package pl.krzaq.metalscrap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	

}
