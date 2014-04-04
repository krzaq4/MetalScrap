package pl.krzaq.metalscrap.bind;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;

import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class UserLoginBind {

	@Wire("#error")
	private Div error ;
	
	
	// -----------------------------
	
	
	// Logowanie
	
	@Wire("#j_username")
	private Textbox jUserName; 
	
	@Wire("#j_password")
	private Textbox jPassword ;
	
		
		private boolean userInvalid = false ;
		
		private boolean passInvalid = false ;
		
		private boolean remindPassword = false ;
		
		private boolean allowLogin = false ;
		
		private String passMessage = "" ;
		
		private String userMessage = "" ;
		
		@Command
		@NotifyChange({ "userInvalid", "userMessage", "allowLogin"})
		public void checkJUser() {
			
			String jUser = jUserName.getValue() ;
			if (jUser!=null && jUser.length()>0) {
				
				if(ServicesImpl.getUserService().getUserByLogin(jUser)!=null) {
					userInvalid = false ;
					userMessage = "" ;
					jUserName.setSclass("default correctValue");
				} else {
					userInvalid = true ;
					userMessage = "Nie ma użytkownika o podanym adresie e-mail" ;
					jUserName.setSclass("default wrongValue");
				}
				
			}
			else
			{
				userInvalid = true ;
				userMessage = "Nazwa użytkownika nie może być pusta" ;
				jUserName.setSclass("default wrongValue");
			}
			
			allowLogin = !userInvalid && !passInvalid;
			resize() ;
			
		}
		
		@Command
		@NotifyChange({"passInvalid", "passMessage", "allowLogin"})
		public void checkJPass() {
			
			String jPass = jPassword.getValue() ;
			
			if (jPass!=null && jPass.length()>0) {
				passInvalid = false ;
				passMessage = "" ;
				jPassword.setSclass("default correctValue");
				
			}
			else
			{
				passInvalid = true ;
				passMessage = "Hasło użytkownika nie może być puste" ;
				jPassword.setSclass("default wrongValue");
			}
			
			allowLogin = !userInvalid && !passInvalid;
			resize() ;
		}
		
		
		@Command
		public void login() {
			
			String jUser = jUserName.getValue() ;
			String jPass = jPassword.getValue() ;
			
			
			Executions.getCurrent().sendRedirect("../j_spring_security_check?j_username="+jUser+"&j_password="+jPass);
			
		}
		
		//------------------------
	
		
		
		 private void resize() {
			 int count = 0 ;
				
				if(userInvalid){
					count++ ;
				}
				if(passInvalid) {
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
				} 
			 
		 }
		 
		 
		 @AfterCompose
		 @NotifyChange({"userMessage", "passMessage", "userInvalid", "passInvalid", "allowLogin"})
		    public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		        
			 	allowLogin = false ;
		    	userMessage = "" ;
		    	passMessage = "" ;
		    	userInvalid = false ;
		    	passInvalid = false ;
		    	
			 Selectors.wireComponents(view, this, false);
		       
		    	
		    	
		        
		    	
		        
		        //wire event listener
//		      Selectors.wireEventListeners(view, this);
		    }
		 
		 
		 @Init
		 @NotifyChange({"allowLogin", "userMessage", "userInvalid", "passMessage", "passInvalid"})
		 public void init() {
			 
			 	allowLogin = false ;
		    	userMessage = "" ;
		    	passMessage = "" ;
		    	userInvalid = false ;
		    	passInvalid = false ;
		    	
			 
		 }

		public Div getError() {
			return error;
		}

		public void setError(Div error) {
			this.error = error;
		}

		public Textbox getjUserName() {
			return jUserName;
		}

		public void setjUserName(Textbox jUserName) {
			this.jUserName = jUserName;
		}

		public Textbox getjPassword() {
			return jPassword;
		}

		public void setjPassword(Textbox jPassword) {
			this.jPassword = jPassword;
		}

		

		public boolean getRemindPassword() {
			return remindPassword;
		}

		public void setRemindPassword(boolean remindPassword) {
			this.remindPassword = remindPassword;
		}

		public boolean getAllowLogin() {
			return allowLogin;
		}

		public void setAllowLogin(boolean allowLogin) {
			this.allowLogin = allowLogin;
		}

		public boolean getUserInvalid() {
			return userInvalid;
		}

		public void setUserInvalid(boolean userInvalid) {
			this.userInvalid = userInvalid;
		}

		public boolean getPassInvalid() {
			return passInvalid;
		}

		public void setPassInvalid(boolean passInvalid) {
			this.passInvalid = passInvalid;
		}

		public String getPassMessage() {
			return passMessage;
		}

		public void setPassMessage(String passMessage) {
			this.passMessage = passMessage;
		}

		public String getUserMessage() {
			return userMessage;
		}

		public void setUserMessage(String userMessage) {
			this.userMessage = userMessage;
		}

		
		 
		 
		 
		 
		
}
