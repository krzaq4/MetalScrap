package pl.krzaq.metalscrap.service;

import java.util.List;

import pl.krzaq.metalscrap.dao.UserDAO;
import pl.krzaq.metalscrap.model.Role;
import pl.krzaq.metalscrap.model.User;

public interface UserService {

public UserDAO getUserDAO() ;

public Role getRoleByName(String name) ;

public List<User> getUsers() ;

public User getUserByLogin(String login) ;

public User getUserByLoginAndPass(String login, String pass) ;

public User getUserByEmail(String email) ;

public User getUserById(Long id) ;

public User getLoggedinUser();	

public void save(User user) ;

public void update(User user) ;

public void delete(User user) ;
}
