package pl.krzaq.metalscrap.bind;

import org.zkoss.bind.annotation.Command;

import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class UserViewBind {

	
	private User user ;
	private Boolean allowRegistration = false ;
	private Boolean error = false ;
	private Boolean step1 = true ;
	private Boolean step2 = false ;
	private String message ;
	private String rePassword ;
	
	@Command
	public void checkLogin() {
		
		if (ServicesImpl.getUserService().getUserByLogin(user.getLogin()) != null ) {
			allowRegistration = allowRegistration && false ;
			error = error && false ;
			message = "Podana nazwa użytkownika jest już zajęta" ;
		} else {
			allowRegistration = allowRegistration && true ;
			error = error && true ;
			message = "" ;
		}
		
	}
	
	@Command
	public void checkEmail() {
		
		if (ServicesImpl.getUserService().getUserByEmail(user.getEmail()) != null ) {
			allowRegistration = allowRegistration && false ;
			error = error && false ;
			message = "Podany adres e-mail jest już zajęty" ;
		} else {
			allowRegistration = allowRegistration && true ;
			error = error && true ;
			message = "" ;
		}
		
	}
	
	@Command
	public void checkPassword() {
		
		if(!(user.getPassword().length()>5 && user.getPassword().matches("[a-z]+[A-Z]+[0-9]+[._-]*"))) {
			allowRegistration = allowRegistration && false ;
			error = error && false ;
			message = "Hasło musi składać się z przynajmniej 5 znaków oraz zawierać chociaż jedną małą, jedną dużą literę i cyfrę" ;
		} else {
			allowRegistration = allowRegistration && true ;
			error = error && true ;
			message = "" ;
		}
		
	}
	
	@Command
	public void checkPasswordMatch() {
		
		if(!user.getPassword().equals(rePassword)) {
			allowRegistration = allowRegistration && false ;
			error = error && false ;
			message = "Hasła różnią się" ;
		} else {
			allowRegistration = allowRegistration && true ;
			error = error && true ;
			message = "" ;
		}
	}
	
	@Command
	public void registerUser() {}
	
	
}
