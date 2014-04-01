package pl.krzaq.metalscrap.bind;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;

import pl.krzaq.metalscrap.model.Role;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;
import pl.krzaq.metalscrap.utils.Constants;
import pl.krzaq.metalscrap.utils.Utilities;

public class UserViewBind {

	// Rejestracja
	
	@Wire("#username")
	private Textbox username ;
	
	@Wire("#email")
	private Textbox email ;
	
	@Wire("#password")
	private Textbox password ;
	
	@Wire("#repassword")
	private Textbox repassword ;
	
	@Wire("#error")
	private Div error ;
	
	
	// -----------------------------
	
	
	
	
	
	// Rejestracja
	
	private User user = new User() ;
	private String registerImage = "resources/images/register-ico.png";
	
	
	private Boolean allowRegistration = false ;
	
	private boolean emailExists = false ;
	private boolean loginExists = false ;
	private boolean passwordNotFormatted = false ;
	private boolean passwordNotMatch = false ;
	
	private boolean step1 = true ;
	private boolean step2 = false ;
	
	private String loginMessage ="";
	private String emailMessage ="";
	private String passMessage ="";
	private String repassMessage ="";
	private String rePassword="" ;
	
	@Command
	@NotifyChange({"loginExists", "allowRegistration", "loginMessage"})
	public void checkLogin() {
		
		if (ServicesImpl.getUserService().getUserByLogin(user.getLogin()) != null ) {
			allowRegistration = false ;
			loginExists = true ;
			loginMessage = "Podana nazwa użytkownika jest już zajęta" ;
			username.setSclass("default wrongValue");
		} else {
			allowRegistration = !loginExists && !emailExists && !passwordNotFormatted && !passwordNotMatch ;
			loginExists = false ;
			loginMessage = "" ;
			username.setSclass("default correctValue");
		}
		
		resize() ;
		
	}
	
	@Command
	@NotifyChange({ "emailExists", "allowRegistration", "emailMessage"})
	public void checkEmail() {
		
		if (ServicesImpl.getUserService().getUserByEmail(user.getEmail()) != null ) {
			allowRegistration = false ;
			emailExists = true ;
			emailMessage = "Podany adres e-mail jest już zajęty" ;
			email.setSclass("default wrongValue");
		} 
		/*else
		if (!user.getEmail().matches("([_A-Za-z0-9-]+)(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})")) {
			allowRegistration = false ;
			emailExists = true ;
			message = "To nie jest poprawny adres e-mail" ;
			email.setSclass("default wrongValue");
		}*/
		else {
			allowRegistration = !loginExists && !emailExists && !passwordNotFormatted && !passwordNotMatch ;
			emailExists = false ;
			emailMessage = "" ;
			email.setSclass("default correctValue");
		}
		resize() ;
		
	}
	
	@Command
	@NotifyChange({ "passwordNotFormatted", "allowRegistration", "passMessage"})
	public void checkPassword() {
		
		if( user.getPassword().matches("\\b(?=.{6,})(?=.*[a-z])(?=.*[A-Z])(?=.*[_-]).*$\\b")) {
			allowRegistration =  !loginExists && !emailExists && !passwordNotFormatted && !passwordNotMatch ;
			passwordNotFormatted = false ;
			passMessage="" ;
			password.setSclass("default correctValue");
		} else {
			allowRegistration = false ;
			passwordNotFormatted = true ;
			passMessage = "Hasło musi składać się z przynajmniej 6 znaków oraz zawierać chociaż jedną małą, jedną dużą literę i cyfrę" ;
			password.setSclass("default wrongValue");
		}
		resize() ;
		
	}
	
	@Command
	@NotifyChange({"passwordNotMatch", "allowRegistration", "repassMessage"})
	public void checkPasswordMatch() {
		
		if(!user.getPassword().equals(rePassword)) {
			allowRegistration = false ;
			passwordNotMatch = true ;
			repassMessage = "Hasła różnią się" ;
			repassword.setSclass("default wrongValue");
		} else {
			allowRegistration = true ;
			passwordNotMatch= false ;
			repassMessage = "" ;
			repassword.setSclass("default correctValue");
		}
		resize() ;
		
	}
	
	@Command
	@NotifyChange({"step1", "step2"})
	public void registerUser() {
		
		try {
			Calendar cal = new GregorianCalendar() ;
			cal.set(Calendar.MILLISECOND, 0);
			user.setCreatedOn(cal.getTime());
			user.setPassword(Utilities.hash(Utilities.HASH_METHOD_MD5, user.getPassword()));
			user.setStatus(User.STATUS_NEW);
			Set<Role> roles = new HashSet<Role>() ;
			
			roles.add(ServicesImpl.getUserService().getRoleByName("ROLE_USER"));
			user.setRoles(roles);
			ServicesImpl.getUserService().save(user);
			
			String link = "http://localhost:8080/MetalScrap/app/register?confirmation=" ;
			String token = Utilities.hash(Utilities.HASH_METHOD_MD5, user.getLogin().concat(user.getEmail().concat(String.valueOf(user.getCreatedOn().getTime())))) ;
			System.out.println("login: "+user.getLogin());
			System.out.println("email: "+user.getEmail());
			System.out.println("milis: "+user.getCreatedOn().getTime());
			System.out.println("token: "+token);
			Map<String, Object> model = new HashMap<String, Object>() ;
			model.put("link", link+token) ;
			model.put("login", user.getLogin()) ;
			model.put("email", user.getEmail()) ;
			
			ServicesImpl.getMailService().sendMail(Constants.MAIL_REGISTRATION_CONFIRMATION, model, "Potwierdzenie rejestracji", user.getEmail());
			step1 = false ;
			step2 = true ;
			
		} catch(NoSuchAlgorithmException ex) {
			
		}
	}

	
	 @AfterCompose
	 @NotifyChange({"loginExists", "emailExists", "passwordNotFormatted", "passwordNotMatch", "allowRegistration", "message", "jUserMessage", "jPassMessage", "jUserInvalid", "jPassInvalid", "allowLogin"})
	    public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
	        Selectors.wireComponents(view, this, false);
	        this.setAllowRegistration(false);
	    	
	    	emailExists = false ;
	    	loginExists = false ;
	    	passwordNotFormatted = false ;
	    	passwordNotMatch = false ;
	    	
	    	
	        
	        //wire event listener
//	      Selectors.wireEventListeners(view, this);
	    }
	
	
	 private void resize() {
		 int count = 0 ;
			if(loginExists) {
				count++ ;
			}
			if(emailExists){
				count++ ;
			}
			if(passwordNotMatch){
				count++ ;
			}
			if(passwordNotFormatted){
				count++ ;
			}
			
			if(count==0){
				error.setSclass("error");
			} else
			if (count==1) {
				error.setSclass("error one");
			} else
			if(count==2) {
				error.setSclass("error two");
			} else
			if(count==3) {
				error.setSclass("error three");
			} else
			if(count==4) {
				error.setSclass("error four");
			}
		 
	 }
	 
	 
	 
	 // ----------------------------------------------
	 
	 
	 // Logowanie
	 
	 
	 
	 
	 //----------------------
	 
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getAllowRegistration() {
		return allowRegistration;
	}
	@NotifyChange({"allowRegistration"})
	public void setAllowRegistration(Boolean allowRegistration) {
		this.allowRegistration = allowRegistration;
	}

	

	public Boolean getStep1() {
		return step1;
	}

	public void setStep1(Boolean step1) {
		this.step1 = step1;
	}

	public Boolean getStep2() {
		return step2;
	}

	public void setStep2(Boolean step2) {
		this.step2 = step2;
	}

	

	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}

	public String getPassMessage() {
		return passMessage;
	}

	public void setPassMessage(String passMessage) {
		this.passMessage = passMessage;
	}

	public String getRepassMessage() {
		return repassMessage;
	}

	public void setRepassMessage(String repassMessage) {
		this.repassMessage = repassMessage;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public Boolean getEmailExists() {
		return emailExists;
	}
	
	public void setEmailExists(boolean emailExists) {
		this.emailExists = emailExists;
	}

	public Boolean getLoginExists() {
		return loginExists;
	}
	
	public void setLoginExists(boolean loginExists) {
		this.loginExists = loginExists;
	}

	public Boolean getPasswordNotFormatted() {
		return passwordNotFormatted;
	}
	
	public void setPasswordNotFormatted(boolean passwordNotFormatted) {
		this.passwordNotFormatted = passwordNotFormatted;
	}

	public Boolean getPasswordNotMatch() {
		return passwordNotMatch;
	}
	
	public void setPasswordNotMatch(boolean passwordNotMatch) {
		this.passwordNotMatch = passwordNotMatch;
	}

	public String getRegisterImage() {
		return registerImage;
	}

	public void setRegisterImage(String registerImage) {
		this.registerImage = registerImage;
	}

	
	
	
	
}
