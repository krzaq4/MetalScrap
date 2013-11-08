package pl.krzaq.metalscrap.service.impl;



import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.xml.MarshallingView;

import pl.krzaq.metalscrap.HomeController;
import pl.krzaq.metalscrap.service.RESTLoginService;
import pl.krzaq.metalscrap.utils.ResponseMessage;
import pl.krzaq.metalscrap.utils.Constants;

public class RESTLoginServiceImpl implements RESTLoginService {

	
	private static final Logger logger = LoggerFactory.getLogger(RESTLoginServiceImpl.class);
	
	@Inject
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	
	@Override	
	public ResponseMessage login(String login, String password) {
		
		logger.info("Attempting to authenticate user "+login);
		ResponseMessage result = new ResponseMessage() ;
		Authentication toAuthenticate = new UsernamePasswordAuthenticationToken(login, password);
		try {
			
		Authentication authenticated = authenticationManager.authenticate(toAuthenticate) ;
		SecurityContextHolder.getContext().setAuthentication(authenticated);
		result.setStatus(Constants.AUTHENTICATION_SUCCESS);
		result.setMsg("User "+login+" succesfully logged in!");
		} catch(BadCredentialsException bce) {
			logger.info("Wrong user or password");
			result.setStatus(Constants.AUTHENTICATION_FAILED);
			result.setMsg("Username or password incorrect");
		}
		
		
		
		return result ;
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
	
	

	

}
