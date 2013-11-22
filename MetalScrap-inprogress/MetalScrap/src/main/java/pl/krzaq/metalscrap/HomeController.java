package pl.krzaq.metalscrap;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.access.ContextBeanFactoryReference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.krzaq.metalscrap.dao.UserDAO;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.impl.RESTLoginServiceImpl;





/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private RESTLoginServiceImpl restLoginServiceImpl ;
	
	@Autowired
	private UserDAO userDAO ;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "index";
	}
	
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		model.addAttribute("user", ( (User) userDAO.getUserByLogin(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())).getFirstName());
		return "home" ;
	}
	
	@RequestMapping(value = "/login_fail", method = RequestMethod.GET)
	public String login_fail(Locale locale, Model model) {
		
		return "login_fail" ;
	}
	
	
	@RequestMapping(value="/{page}", method=RequestMethod.GET)
	public String page(@PathVariable String page) {
		return page ;
	}
	
	/*@RequestMapping( value="/loginservice/{login}/{password}", method=RequestMethod.GET)
	public @ResponseBody User user(@PathVariable String login, @PathVariable String password) {
		
		return restLoginServiceImpl.login(login, password) ;
		
		
	}*/
	
	
}
