package pl.krzaq.metalscrap.bind;

import java.net.URLEncoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.codec.Base64;
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

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;
import com.restfb.types.User;

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
	
		
	private User fbUser = new User();
	
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
		 
		 
		 private String hmacSHA256(String data, String key) throws Exception {
		        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
		        Mac mac = Mac.getInstance("HmacSHA256");
		        mac.init(secretKey);
		        byte[] hmacData = mac.doFinal(data.getBytes("UTF-8"));
		        return new String(hmacData);
		    }

		 
		 @Command
		 @NotifyChange({"fbUser"})
		 public void fbLogin() {
			 
			 
			 
			 try {
			 String fbSecretKey = "bcbb7768899326614e0b9f04c69987aa";
		        String fbAppId = "228453697351919";
		        String fbCanvasPage = "https://apps.facebook.com/228453697351919/";
		        String fbCanvasUrl = "http://auction-krzaczynski.rhcloud.com/app/signin/";
		        
		        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest() ;

		        //parse signed_request
		        if(request.getParameter("signed_request") != null) {

		            //it is important to enable url-safe mode for Base64 encoder 
		            Base64 base64 = new Base64();

		            //split request into signature and data
		            String[] signedRequest = request.getParameter("signed_request").split("\\.", 2);

		            //parse signature
		            String sig = new String(base64.decode(signedRequest[0].getBytes("UTF-8")));

		            //parse data and convert to json object
		           // JSONParser par = new JSONParser(new String(base64.decode(signedRequest[1].getBytes("UTF-8")))) ;
		            
		            JsonObject data = new JsonObject(new String(base64.decode(signedRequest[1].getBytes("UTF-8")))) ;
		            //JsonObject data = (JsonObject)JSONParserJSONSerializer.toJSON(new String(base64.decode(signedRequest[1].getBytes("UTF-8"))));

		            //check signature algorithm
		            if(!data.getString("algorithm").equals("HMAC-SHA256")) {
		                //unknown algorithm is used
		               
		            } else

		            //check if data is signed correctly
		            if(!hmacSHA256(signedRequest[1], fbSecretKey).equals(sig)) {
		                //signature is not correct, possibly the data was tampered with
		               
		            } else

		            //check if user authorized the app
		            if(!data.has("user_id") || !data.has("oauth_token")) {
		                //this is guest, create authorization url that will be passed to javascript
		                //note that redirect_uri (page the user will be forwarded to after authorization) is set to fbCanvasUrl
		                String redirectUrl = "https://www.facebook.com/dialog/oauth?client_id=" + fbAppId + 
		                        "&redirect_uri=" + URLEncoder.encode(fbCanvasUrl, "UTF-8") + 
		                        "&scope=publish_stream,offline_access,email";
		            } else {
		                //this is authorized user, get their info from Graph API using received access token
		                String accessToken = data.getString("oauth_token");
		                FacebookClient facebookClient = new DefaultFacebookClient(accessToken);
		                fbUser = facebookClient.fetchObject("me", User.class);
		                
		            }

		        } else {
		            //this page was opened not inside facebook iframe,
		            //possibly as a post-authorization redirect.
		            //do server side forward to facebook app
		            //return new ModelAndView(new RedirectView(fbCanvasPage, true));
		        	Executions.sendRedirect(fbCanvasPage);
		        }

			 } catch(Exception eed) {
				 
			 }
			 
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

		public User getFbUser() {
			return fbUser;
		}

		public void setFbUser(User fbUser) {
			this.fbUser = fbUser;
		}

		
		 
		 
		 
		 
		
}
