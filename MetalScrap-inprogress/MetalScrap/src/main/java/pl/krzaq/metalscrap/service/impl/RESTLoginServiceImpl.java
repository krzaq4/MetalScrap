package pl.krzaq.metalscrap.service.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;





import pl.krzaq.metalscrap.dao.UserDAO;
import pl.krzaq.metalscrap.service.RESTLoginService;





public class RESTLoginServiceImpl implements RESTLoginService, ApplicationContextAware {

	
	private static final Logger logger = LoggerFactory.getLogger(RESTLoginServiceImpl.class);
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDAO userDAO ;
	
	private ApplicationContext applicationContext ;
	
	@Override	
	public void login(String login, String password) {
		
		logger.info("Attempting to authenticate user "+login);
		Authentication toAuthenticate = new UsernamePasswordAuthenticationToken(login, password);
		logger.info("User credentials");
		try {
		
	
		Authentication authenticated = authenticationManager.authenticate(toAuthenticate) ;
		
		logger.info("Authenticated");
		SecurityContextHolder.getContext().setAuthentication(authenticated);
		logger.info("Authenticated in context");
		
		
		
		} catch(BadCredentialsException bce) {
			logger.error("Wrong user or password");
			
		
		}
		
		
		
	}


	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}


	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}


	public static Logger getLogger() {
		return logger;
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext ;
		
	}
	
	

	

}
