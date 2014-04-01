package pl.krzaq.metalscrap.bind;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;

import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;
import pl.krzaq.metalscrap.utils.Constants;

public class UserPassChangeBind {

	@Wire("#error")
	private Div error ;
	
	@Wire("#email")
	private Textbox email ;
	
	private boolean emailInvalid = false ;
	private boolean allowRequest = false ;
	private boolean requestSent = false ;
	
	private String message = "" ;
	
	
	
	
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
				
			} 
			else 
			{
				allowRequest = false ;
				message = "W systemie nie znaleziono użytkownika o podanym adresie email" ;
				emailInvalid = false ;
			}
			
		}
		else
		{
			allowRequest = false ;
			message = "Pole adresu nie może być puste" ;
			emailInvalid = false ;
		}
		
		if(emailInvalid) {
			error.setSclass("one error");
		}
		
	}
	
	@AfterCompose
	 @NotifyChange({"message", "emailInvalid", "allowRequest", "requestSent"})
	    public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
	        Selectors.wireComponents(view, this, false);
	       
	    	
	    	emailInvalid = false ;
	    	allowRequest = false ;
	    	message="" ;
	    	requestSent = false ;
	    	
	        
	        //wire event listener
//	      Selectors.wireEventListeners(view, this);
	    }
	
	
}
