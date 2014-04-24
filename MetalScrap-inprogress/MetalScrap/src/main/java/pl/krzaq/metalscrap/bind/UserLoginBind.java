package pl.krzaq.metalscrap.bind;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.jboss.resteasy.core.Headers;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;
import com.restfb.types.Photo;
import com.restfb.types.User;

import pl.krzaq.metalscrap.model.Role;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;
import pl.krzaq.metalscrap.utils.ApplicationContextProvider;
import pl.krzaq.metalscrap.utils.Utilities;

public class UserLoginBind {

	@Wire("#error")
	private Div error ;
	
	
	// -----------------------------
	
	
	// Logowanie
	
	@Wire("#j_username")
	private Textbox jUserName; 
	
	@Wire("#j_password")
	private Textbox jPassword ;
	
	@Wire("#fbsecret")
	private Textbox fbsecret ;
	
	private Page page ;
		
	private User fbUser = new User();
	
	private boolean loginFailed = false ;
	
		private boolean userInvalid = false ;
		
		private boolean passInvalid = false ;
		
		private boolean remindPassword = false ;
		
		private boolean allowLogin = false ;
		
		private String passMessage = "" ;
		
		private String userMessage = "" ;
		
		
		@Command
		@NotifyChange({"fbUser"})
		public void fbAccess() {
			
			String accessToken = fbsecret.getValue() ;
			FacebookClient fbc = new DefaultFacebookClient(accessToken) ;
			fbUser = fbc.fetchObject("me", User.class) ;
			
			pl.krzaq.metalscrap.model.User fbAppUser = ServicesImpl.getUserService().getUserDAO().getUserbyFbId(fbUser.getId()) ;
			if(fbAppUser!=null) {
				// registered
				
				String password = fbAppUser.getPassword() ;
				Set<Role> roles = fbAppUser.getRoles() ;
				
				Set<SimpleGrantedAuthority> authorities = new LinkedHashSet<SimpleGrantedAuthority>();
				//Set<GrantedAuthorityImpl> authorities = new HashSet<GrantedAuthorityImpl>() ;
				for (Role role:roles) {
					if(role!=null) {
						authorities.add(new SimpleGrantedAuthority(role.getName().toUpperCase())) ;
					}
					
				}
				//login
				org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(fbAppUser.getLogin(), password, authorities) ;
				Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
				//profile photo update
				updateProfilePhoto(fbUser, fbAppUser) ;
				
				Executions.sendRedirect("user/myaccount");
						
			} else {
				// not registered
				fbAppUser = new pl.krzaq.metalscrap.model.User() ;
				
				if(fbUser.getUsername()!=null) {
					// use eusername as login
					fbAppUser.setLogin(fbUser.getUsername());
				} else {
					// use email as login
					fbAppUser.setLogin(fbUser.getEmail());
				}
				
				fbAppUser.setEmail(fbUser.getEmail());
				
				if(ServicesImpl.getUserService().getUserByLogin(fbAppUser.getLogin())!=null 
						|| ServicesImpl.getUserService().getUserByLogin(fbAppUser.getEmail())!=null							 
							|| ServicesImpl.getUserService().getUserByEmail(fbAppUser.getEmail())!=null){
					
					//login or email already taken
					fbUser = new User() ;
					Messagebox.show("Nazwa lub e-mail użytkownika jest już zajęty") ;
				} else {
					// login and email is free to go
					try {
					
					if(fbUser.getFirstName()!=null) {
						fbAppUser.setFirstName(fbUser.getFirstName());
					} else {
						fbAppUser.setFirstName("");
					}
					
					if(fbUser.getLastName()!=null) {
						fbAppUser.setLastName(fbUser.getLastName());
					} else {
						fbAppUser.setLastName("");
					}
					fbAppUser.setCompleted(false);
					fbAppUser.setStatus(pl.krzaq.metalscrap.model.User.STATUS_CONFIRMED);
					
					Set<Role> roles = new HashSet<Role>() ;
					roles.add(ServicesImpl.getUserService().getRoleByName("ROLE_USER"));
					
					fbAppUser.setRoles(roles);
					fbAppUser.setPassword(Utilities.hash(Utilities.HASH_METHOD_MD5, fbAppUser.getLogin().concat(fbAppUser.getEmail())));
					Calendar cal = new GregorianCalendar() ;
					fbAppUser.setFbId(fbUser.getId());
					fbAppUser.setCreatedOn(cal.getTime());
					ServicesImpl.getUserService().save(fbAppUser);
					
					String dataDir = System.getProperty("jboss.server.data.dir") ;
					dataDir = dataDir.concat("/platform/users");
					
					File dataDirFolder = new File(dataDir) ;
					
					dataDirFolder.mkdir();
					
					String userDir = dataDir.concat("/").concat(fbAppUser.getLogin()) ;
					
					File userDirFolder = new File(userDir) ;
					
					userDirFolder.mkdir();
					
					File avatarDirFolder = new File(userDir.concat("/avatar")) ;
					
					avatarDirFolder.mkdir() ;
					
					
					updateProfilePhoto(fbUser, fbAppUser) ;
					
					String password = fbAppUser.getPassword() ;
					
					
					Set<SimpleGrantedAuthority> authorities = new LinkedHashSet<SimpleGrantedAuthority>();
					//Set<GrantedAuthorityImpl> authorities = new HashSet<GrantedAuthorityImpl>() ;
					for (Role role:roles) {
						if(role!=null) {
							authorities.add(new SimpleGrantedAuthority(role.getName().toUpperCase())) ;
						}
						
					}
					
					org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(fbAppUser.getLogin(), password, authorities) ;
					Authentication auth = 
							  new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

							SecurityContextHolder.getContext().setAuthentication(auth);
					
					Executions.sendRedirect("user/myaccount");
					} catch(NoSuchAlgorithmException ex) {
						Messagebox.show("Błąd rejestracji użytkownika FB") ;
					}
				}
				
				
			}
			
		}
		
		private void updateProfilePhoto(User fbUser, pl.krzaq.metalscrap.model.User fbAppUser) {
			String dataDir = System.getProperty("jboss.server.data.dir") ;
			dataDir = dataDir.concat("/platform/users");
			String userDir = dataDir.concat("/").concat(fbAppUser.getLogin()) ;
			String url = "http://graph.facebook.com/"+fbUser.getId()+"/picture?type=large";
			
			try {
				URL u = new URL(url) ;
				URLConnection uu = u.openConnection() ;
				String redirUrl = uu.getHeaderField("Location") ;
				URL u2 = new URL(redirUrl) ;
				URLConnection uu2 =u2.openConnection() ;
				InputStream is = uu2.getInputStream();
				byte[] bytes = new byte[is.available()] ;
				is.read(bytes) ;
				ByteArrayInputStream bis = new ByteArrayInputStream(bytes); 
				String fileName = userDir.concat("/avatar/fb") ;
				FileOutputStream fos = new FileOutputStream(fileName) ;
				fos.write(bytes);
				fos.close();
				fbAppUser.setAvatarFileName("fb");
				
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
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
		 @NotifyChange({"userMessage", "passMessage", "userInvalid", "passInvalid", "allowLogin", "loginFailed"})
		    public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		        
			 	allowLogin = false ;
		    	userMessage = "" ;
		    	passMessage = "" ;
		    	userInvalid = false ;
		    	passInvalid = false ;
		    	
			 Selectors.wireComponents(view, this, false);
		       
			 this.page = jUserName.getPage() ;
			 
			 
		    String status = (String) this.page.getAttribute("status") ;
		    if(status!=null && status.equals("failure")) {
		    	loginFailed = true ;
		    	error.setSclass("one error");
		    	
		    }
		    	
		        
		    	
		        
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

		public Textbox getFbsecret() {
			return fbsecret;
		}

		public void setFbsecret(Textbox fbsecret) {
			this.fbsecret = fbsecret;
		}

		public Page getPage() {
			return page;
		}

		public void setPage(Page page) {
			this.page = page;
		}

		public boolean isLoginFailed() {
			return loginFailed;
		}

		public void setLoginFailed(boolean loginFailed) {
			this.loginFailed = loginFailed;
		}
		
		
		
		 
		 
		 
		 
		
}
