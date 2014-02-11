package pl.krzaq.metalscrap.service;

import pl.krzaq.metalscrap.dao.UserDAO;
import pl.krzaq.metalscrap.model.User;

public interface UserService {

public User getUserByLogin(String login) ;

public User getUserByLoginAndPass(String login, String pass) ;

public User getUserByEmail(String email) ;

public User getUserById(Long id) ;

public User getLoggedinUser();	

public void save(User user) ;

public void update(User user) ;

public void delete(User user) ;
}
