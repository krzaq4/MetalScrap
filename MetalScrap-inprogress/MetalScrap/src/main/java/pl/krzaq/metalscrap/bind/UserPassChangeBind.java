package pl.krzaq.metalscrap.bind;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;

import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;
import pl.krzaq.metalscrap.utils.Constants;
import pl.krzaq.metalscrap.utils.Utilities;

public class UserPassChangeBind {

	@Wire("#error")
	private Div error ;
	
	@Wire("#email")
	private Textbox email ;
	
	@Wire("#pass")
	private Textbox pass ;
	
	@Wire("#repass")
	private Textbox repass ;
	
	private boolean passInvalid = false ;
	private boolean passNotMatch = false ;
	private boolean allowSave = false ;
	
	private boolean emailInvalid = false ;
	private boolean allowRequest = false ;
	private boolean requestSent = false ;
	
	private String passMessage = "" ;
	private String passMatchMessage = "" ;
	private String message = "" ;
	
	private boolean passwordChanged = false ;
	
	
	@Command
	@NotifyChange({"message", "emailInvalid", "requestSent"})
	public void requestPassword(){
		
		User user = ServicesImpl.getUserService().getUserByEmail(email.getValue()) ;
		if(user.getPasswordChange()) {
			
			emailInvalid = true ;
			message = "Prośba o zresetowanie hasła została już wysłana \\n Poszukaj wiadomości z linkiem w skrzynce pocztowej \\n Jeśli nie otrzymałeś wiadomości, skontaktuj się z administratorem " ;
			error.setSclass("three error");
			
		}
		else
		{
			emailInvalid = false ;
			message="" ;
			error.setSclass("error");
			
			user.setPasswordChange(true);
			ServicesImpl.getUserService().update(user);
			
			String hostName = ServicesImpl.getConfigService().findByKey("host.address").getValue() ;
			String remindToken = user.getRemindToken() ;
			
			String link = hostName+"/app/remindpass?user="+remindToken ;
			
			Map<String, Object> mailModel = new HashMap<String, Object>() ;
			mailModel.put("login", user.getLogin()) ;
			mailModel.put("email", user.getEmail()) ;
			mailModel.put("link", link) ;
			
			ServicesImpl.getMailService().sendMail(Constants.MAIL_PASSWORD_REMIND, mailModel, "Przypomnienie hasła", user.getEmail());
			
			requestSent = true ;
			
		}
		
		
	}
	
	
	@Command
	@NotifyChange({"emailInvalid", "allowRequest", "message"})
	public void checkEmail() {
		String emailAddress = email.getValue() ;
		
		if (emailAddress!=null && emailAddress.length()>0) {
		
			if (ServicesImpl.getUserService().getUserByEmail(emailAddress)!=null) {
				
				allowRequest = true ;
				message = "" ;
				emailInvalid = false ;
				email.setSclass("default correctValue");
				
			} 
			else 
			{
				allowRequest = false ;
				message = "W systemie nie znaleziono użytkownika o podanym adresie email" ;
				emailInvalid = true ;
				email.setSclass("default wrongValue");
			}
			
		}
		else
		{
			allowRequest = false ;
			message = "Pole adresu nie może być puste" ;
			emailInvalid = true ;
			email.setSclass("default wrongValue");
		}
		
		if(emailInvalid) {
			error.setSclass("one error");
		} else {
			error.setSclass("error");
		}
		
	}
	
	
	@Command
	@NotifyChange({"passInvalid", "allowSave", "passMessage"})
	public void checkPass() {
		if( pass.getValue().matches("\\b(?=.{6,})(?=.*[a-z])(?=.*[A-Z])(?=.*[_-]).*$\\b")) {
			passInvalid=false ;
			allowSave =  !passInvalid && !passNotMatch ;
			
			passMessage="" ;
			pass.setSclass("default correctValue");
		} else {
			allowSave = false ;
			passInvalid = true ;
			passMessage = "Hasło musi składać się z przynajmniej 6 znaków oraz zawierać chociaż jedną małą, jedną dużą literę i cyfrę" ;
			pass.setSclass("default wrongValue");
		}
		resize() ;
	}
	
	@Command
	@NotifyChange({"passNotMatch", "allowSave", "passMatchMessage"})
	public void checkPassMatch() {
		
		if(!pass.getValue().equals(repass.getValue())) {
			allowSave = false ;
			passNotMatch = true ;
			passMatchMessage = "Hasła różnią się" ;
			repass.setSclass("default wrongValue");
		} else {
			allowSave = true ;
			passNotMatch= false ;
			passMatchMessage = "" ;
			repass.setSclass("default correctValue");
		}
		resize() ;
	}
	
	@Command
	@NotifyChange({"passwordChanged"})
	public void changePassword() {
		
		User user = (User) pass.getPage().getAttribute("user") ;
		user.setPasswordChange(false);
		try {
			// ostatnia modyfiakcja do doklejenia
			user.setPassword(Utilities.hash(Utilities.HASH_METHOD_MD5, pass.getValue()));
			ServicesImpl.getUserService().update(user);
			setPasswordChanged(true) ;
		} catch(NoSuchAlgorithmException ex) {
			setPasswordChanged(false) ;
		}
		
	}
	
	
	@Init
	@NotifyChange({"message", "emailInvalid", "allowRequest", "requestSent", "passwordChanged"})
	public void init(){
		setEmailInvalid(false) ;
    	setAllowRequest(false) ;
    	setMessage("") ;
    	setRequestSent(false) ;
    	setPasswordChanged(false);
	}
	
	@AfterCompose
	 @NotifyChange({"message", "emailInvalid", "allowRequest", "requestSent", "passwordChanged"})
	    public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
	        
	       
	    	setEmailInvalid(false) ;
	    	setAllowRequest(false) ;
	    	setMessage("") ;
	    	setRequestSent(false) ;
	    	setPasswordChanged(false);
	    	Selectors.wireComponents(view, this, false);
	        
	        //wire event listener
//	      Selectors.wireEventListeners(view, this);
	    }


	private void resize() {
		int count = 0 ;
		if(passInvalid) {
			count++ ;
		}
		if(passNotMatch) {
			count++ ;
		}
		
		if(count==0){
			error.setSclass("error");
		}
		else
		if(count==1){
			error.setSclass("one error");
		}
		else
		if(count==2)
		{
			error.setSclass("two error");
		}
		
	}
	
	public Div getError() {
		return error;
	}


	public void setError(Div error) {
		this.error = error;
	}


	public Textbox getEmail() {
		return email;
	}


	public void setEmail(Textbox email) {
		this.email = email;
	}


	public boolean isEmailInvalid() {
		return emailInvalid;
	}

	@NotifyChange({"emailInvalid"})
	public void setEmailInvalid(boolean emailInvalid) {
		this.emailInvalid = emailInvalid;
	}


	public boolean isAllowRequest() {
		return allowRequest;
	}

	@NotifyChange({"allowRequest"})
	public void setAllowRequest(boolean allowRequest) {
		this.allowRequest = allowRequest;
	}


	@NotifyChange({"requestSent"})
	public boolean getRequestSent() {
		return requestSent;
	}

	@NotifyChange({"requestSent"})
	public void setRequestSent(boolean requestSent) {
		this.requestSent = requestSent;
	}


	public String getMessage() {
		return message;
	}

	@NotifyChange({"message"})
	public void setMessage(String message) {
		this.message = message;
	}


	public Textbox getPass() {
		return pass;
	}


	public void setPass(Textbox pass) {
		this.pass = pass;
	}


	public Textbox getRepass() {
		return repass;
	}


	public void setRepass(Textbox repass) {
		this.repass = repass;
	}


	public boolean isPassInvalid() {
		return passInvalid;
	}


	public void setPassInvalid(boolean passInvalid) {
		this.passInvalid = passInvalid;
	}


	public boolean isPassNotMatch() {
		return passNotMatch;
	}


	public void setPassNotMatch(boolean passNotMatch) {
		this.passNotMatch = passNotMatch;
	}


	public boolean isAllowSave() {
		return allowSave;
	}


	public void setAllowSave(boolean allowSave) {
		this.allowSave = allowSave;
	}


	public String getPassMessage() {
		return passMessage;
	}


	public void setPassMessage(String passMessage) {
		this.passMessage = passMessage;
	}


	public String getPassMatchMessage() {
		return passMatchMessage;
	}


	public void setPassMatchMessage(String passMatchMessage) {
		this.passMatchMessage = passMatchMessage;
	}

	@NotifyChange({"passwordChanged"})
	public boolean getPasswordChanged() {
		return passwordChanged;
	}

	@NotifyChange({"passwordChanged"})
	public void setPasswordChanged(boolean passwordChanged) {
		this.passwordChanged = passwordChanged;
	}
	
	
	
	
}
