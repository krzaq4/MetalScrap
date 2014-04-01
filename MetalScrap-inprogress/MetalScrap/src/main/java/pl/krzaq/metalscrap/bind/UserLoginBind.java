package pl.krzaq.metalscrap.bind;

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

public class UserLoginBind {

	@Wire("#error")
	private Div error ;
	
	
	// -----------------------------
	
	
	// Logowanie
	
	@Wire("#j_username")
	private Textbox jUserName; 
	
	@Wire("#j_password")
	private Textbox jPassword ;
	
		
		private boolean jUserInvalid = false ;
		
		private boolean jPassInvalid = false ;
		
		private boolean remindPassword = false ;
		
		private boolean allowLogin = false ;
		
		private String jPassMessage = "" ;
		
		private String jUserMessage = "" ;
		
		@Command
		@NotifyChange({ "jUserInvalid", "jUserMessage", "allowLogin"})
		public void checkJUser() {
			
			String jUser = jUserName.getValue() ;
			if (jUser!=null && jUser.length()>0) {
				jUserInvalid = false ;
				jUserMessage = "" ;
				jUserName.setSclass("default correctValue");
			}
			else
			{
				jUserInvalid = true ;
				jUserMessage = "Nazwa użytkownika nie może być pusta" ;
				jUserName.setSclass("default wrongValue");
			}
			
			allowLogin = !jUserInvalid && !jPassInvalid;
			resize() ;
			
		}
		
		@Command
		@NotifyChange({"jPassInvalid", "jPassMessage", "allowLogin"})
		public void checkJPass() {
			
			String jPass = jPassword.getValue() ;
			
			if (jPass!=null && jPass.length()>0) {
				jPassInvalid = false ;
				jPassMessage = "" ;
				jPassword.setSclass("default correctValue");
				
			}
			else
			{
				jPassInvalid = true ;
				jPassMessage = "Hasło użytkownika nie może być puste" ;
				jPassword.setSclass("default wrongValue");
			}
			
			allowLogin = !jUserInvalid && !jPassInvalid;
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
				
				if(jUserInvalid){
					count++ ;
				}
				if(jPassInvalid) {
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
		 @NotifyChange({"jUserMessage", "jPassMessage", "jUserInvalid", "jPassInvalid", "allowLogin"})
		    public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		        Selectors.wireComponents(view, this, false);
		       
		    	
		    	allowLogin = false ;
		    	jUserMessage = "" ;
		    	jPassMessage = "" ;
		    	jUserInvalid = false ;
		    	jPassInvalid = false ;
		        
		    	
		        
		        //wire event listener
//		      Selectors.wireEventListeners(view, this);
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

		public boolean isjUserInvalid() {
			return jUserInvalid;
		}

		public void setjUserInvalid(boolean jUserInvalid) {
			this.jUserInvalid = jUserInvalid;
		}

		public boolean isjPassInvalid() {
			return jPassInvalid;
		}

		public void setjPassInvalid(boolean jPassInvalid) {
			this.jPassInvalid = jPassInvalid;
		}

		public boolean isRemindPassword() {
			return remindPassword;
		}

		public void setRemindPassword(boolean remindPassword) {
			this.remindPassword = remindPassword;
		}

		public boolean isAllowLogin() {
			return allowLogin;
		}

		public void setAllowLogin(boolean allowLogin) {
			this.allowLogin = allowLogin;
		}

		public String getjPassMessage() {
			return jPassMessage;
		}

		public void setjPassMessage(String jPassMessage) {
			this.jPassMessage = jPassMessage;
		}

		public String getjUserMessage() {
			return jUserMessage;
		}

		public void setjUserMessage(String jUserMessage) {
			this.jUserMessage = jUserMessage;
		}
		
		 
		 
		 
		 
		
}
