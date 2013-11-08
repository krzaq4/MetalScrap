package pl.krzaq.metalscrap.service;

import pl.krzaq.metalscrap.dao.UserDAO;
import pl.krzaq.metalscrap.model.User;

public interface UserService {

public User getUserByLogin(String login) ;

public User getUserByLoginAndPass(String login, String pass) ;
	
	
	
}
