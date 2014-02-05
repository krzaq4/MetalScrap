package pl.krzaq.metalscrap;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.collections.MultiHashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.krzaq.metalscrap.converter.AuctionTimeLeftConverter;
import pl.krzaq.metalscrap.dao.UserDAO;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.model.UserOffer;
import pl.krzaq.metalscrap.service.impl.RESTLoginServiceImpl;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;





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
	
	
	/*@RequestMapping(value="/{page}", method=RequestMethod.GET)
	public String page(@PathVariable String page) {
		return page ;
		
	}*/
	
	@RequestMapping( value="/auctions", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> auctionList(@RequestParam Integer status, @RequestParam String dateFrom, @RequestParam String dateTo) {
		Map<String, Object> map = new LinkedHashMap<String, Object>() ;
		boolean operationStatus = false ;
		String operationMessage = "ERROR" ;
		MultiHashMap mmap = new MultiHashMap() ;
		
		try {
		
			Date from = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SS", Locale.getDefault()).parse(dateFrom) ;
			Date to = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SS", Locale.getDefault()).parse(dateTo) ;
			
			List<Long> ids = ServicesImpl.getAuctionService().findIds(ServicesImpl.getAuctionService().findStatusByCode(Integer.valueOf(status)), from, to) ;
			
			for (Long id:ids) {
				mmap.put("id", id) ;
			}
			
			map.put("ids", mmap) ;
			operationStatus = true ;
			operationMessage = "OK" ;
		
		} catch(Exception ex) {
			operationStatus = false ;
			operationMessage = ex.getLocalizedMessage() ;
		}
		map.put("operationStatus", operationStatus) ;
		map.put("operationMessage", operationMessage) ;
		return map ;
	}
	
	
	@RequestMapping( value="/auction", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> auction(@RequestParam Integer id) {
		
		Map<String, Object> map = new LinkedHashMap<String, Object>() ;
		boolean operationStatus = false ;
		String operationMessage = "ERROR" ;
		try {
		
			AuctionTimeLeftConverter cv = new AuctionTimeLeftConverter() ;
		
			Auction a = ServicesImpl.getAuctionService().findById(Long.valueOf(id)) ;
			boolean started = (a.getStatus().getCode().equals(AuctionStatus.STATUS_STARTED)) ;
		
			String timeLeft = (String) cv.coerceToUi(a, null);
		
			map.put("name", a.getName()) ;
			map.put("stat", a.getStatus().getCode()) ;
			map.put("started", started) ;
			map.put("dateFrom", a.getStartDate().toString()) ;
			map.put("dateTo", a.getEndDate().toString()) ;
			map.put("timeLeft", timeLeft) ;
			if(a.getBestUserOffer()!=null)
				map.put("currentOffer", a.getBestUserOffer().getPrice().toString()) ;
			else map.put("currentOffer", "--") ;
		
			operationStatus = true ;
			operationMessage = "OK" ;
		
		} catch(Exception ex){
			operationStatus = false ;
			operationMessage = ex.getLocalizedMessage() ;
			
		}
		map.put("operationStatus", operationStatus) ;
		map.put("operationMessage", operationMessage) ;
		return map ;
		
	}
	

	@RequestMapping( value="/auction", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> auction(@RequestParam String name, @RequestParam String dateFrom, @RequestParam String dateTo) {
		Map<String, Object> map = new LinkedHashMap<String, Object>() ;
		boolean operationStatus = false ;
		String operationMessage = "ERROR" ;
		try {
			Auction a = new Auction() ;
			a.setName(name);		
			a.setStartDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SS", Locale.getDefault()).parse(dateFrom));
			a.setEndDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SS", Locale.getDefault()).parse(dateTo));
			ServicesImpl.getAuctionService().save(a);
			operationStatus = true ;
			operationMessage = "OK" ;
		} catch (ParseException e) {
			operationStatus = false ;
			operationMessage = e.getLocalizedMessage() ;
		}
		map.put("operationStatus", operationStatus) ;
		map.put("operationMessage", operationMessage) ;
		return map ;
	}

	
	@RequestMapping( value="/auction", method=RequestMethod.PUT)
	public @ResponseBody Map<String, Object> auction(@RequestParam Integer id, @RequestParam String name, @RequestParam String dateFrom, @RequestParam String dateTo) {
		Map<String, Object> map = new LinkedHashMap<String, Object>() ;
		boolean operationStatus = false ;
		String operationMessage = "ERROR" ;
		try {
			Auction a = ServicesImpl.getAuctionService().findById(Long.valueOf(id)) ;
			a.setName(name);		
			a.setStartDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SS", Locale.getDefault()).parse(dateFrom));
			a.setEndDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SS", Locale.getDefault()).parse(dateTo));
			ServicesImpl.getAuctionService().update(a);
			operationStatus = true ;
			operationMessage = "OK" ;			
		} catch(Exception ex) {
			operationStatus = false ;
			operationMessage = ex.getLocalizedMessage() ;
			
		}
		map.put("operationStatus", operationStatus) ;
		map.put("operationMessage", operationMessage) ;
		return map ;
		
	}
	
	
	@RequestMapping( value="/offer", method=RequestMethod.POST)
	public Map<String, Object> addOffer(@RequestParam Integer auction_id, @RequestParam String value) {
		Map<String, Object> map = new LinkedHashMap<String, Object>() ;
		boolean operationStatus = false ;
		String operationMessage = "ERROR" ;
		try {
			
			UserOffer uo = new UserOffer() ;
			Auction a = ServicesImpl.getAuctionService().findById(Long.valueOf(auction_id)) ;
			
			if (value.contains(".") || value.contains(" ")) {
				value.replace(".", ",") ;
				value.replace(" ", "") ;
					
			}
			
			uo.setAuction(a);
			if (Double.valueOf(value).compareTo(a.getBestUserOffer().getPrice())>0) {
				uo.setDateIssued(new Date());
				uo.setPrice(Double.valueOf(value));
				uo.setUser(null);
				a.setBestUserOffer(uo);
				ServicesImpl.getUserOfferService().save(uo);
			}
			
			
			operationStatus = true ;
			operationMessage = "OK" ;
		} catch(Exception ex) {
			operationStatus = false ;
			operationMessage = ex.getLocalizedMessage() ;
			
		}
		
		map.put("operationStatus", operationStatus) ;
		map.put("operationMessage", operationMessage) ;
		return map ;
		
	}
	
	

	
	
}
