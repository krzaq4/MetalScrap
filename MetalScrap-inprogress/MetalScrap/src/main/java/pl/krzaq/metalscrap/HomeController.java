package pl.krzaq.metalscrap;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.access.ContextBeanFactoryReference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.krzaq.metalscrap.converter.AuctionTimeLeftConverter;
import pl.krzaq.metalscrap.dao.UserDAO;
import pl.krzaq.metalscrap.model.Address;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.model.UserOffer;
import pl.krzaq.metalscrap.service.impl.RESTLoginServiceImpl;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;
import pl.krzaq.metalscrap.utils.Utilities;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;




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
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String root(Locale locale, Model model) {
		model.addAttribute("user", ( (User) userDAO.getUserByLogin(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())).getLogin());
		return "home" ;
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		model.addAttribute("user", ( (User) userDAO.getUserByLogin(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())).getLogin());
		return "home" ;
	}
	
	@RequestMapping(value = "/login_fail", method = RequestMethod.GET)
	public String login_fail(Locale locale, Model model) {
		
		return "login_fail" ;
	}
	
	
	@RequestMapping(value="/{path}/{page}", method=RequestMethod.GET)
	public String page(@PathVariable String page, @PathVariable String path) {
		return path+"/"+page ;
		
	}
	
	
	
	/**
	 * Rest WS method to use for get user info. 
	 * 
	 * Path: /auctions
	 * Method: GET
	 * 
	 * Request parameters:
	 * 
	 * status - Integer - AuctionStatus code
	 * from - Date - Date from 
	 * to - Date - Date to
	 * category_id - Integer - Category id
	 *    
	 * 
	 * @param  json Object of type Map<String, Object> representing json, sent in post request
	 * @return      json, representing status of operation and user info: 
	 *              [ { 'category_id':<i>category_id</i>, 
	 *               'name':<i>name</i>, 'description':<i>description</i>, 'parent_id':<i>parent_id</i> },  {...}, ... ,  {...} ]
	 */
	
	@RequestMapping( value="/auctions", method=RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> auctionList(@RequestParam(value="status") Integer status, @RequestParam(value="from") String dateFrom, @RequestParam(value="to") String dateTo, @RequestParam(value="category_id") Integer category_id) {
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>() ;
		AuctionTimeLeftConverter cv = new AuctionTimeLeftConverter() ;
		
		try {
		Date from = null ;
		Date to = null ;
			
		if(dateFrom!=null && dateFrom.length()>0)
			from = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SS", Locale.getDefault()).parse(dateFrom) ;
		if(dateTo!=null && dateTo.length()>0)
			to = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SS", Locale.getDefault()).parse(dateTo) ;
		
			List<Auction> auctions = new ArrayList<Auction>() ;
			AuctionStatus auctionStatus = null ;
			if(status!=null)
				auctionStatus = ServicesImpl.getAuctionService().findStatusByCode(status) ;
			if (category_id!=null) {
				
				if (auctionStatus!=null)
					auctions = ServicesImpl.getAuctionService().findByCategoryDown(ServicesImpl.getCategoryService().findById(Long.valueOf(category_id)), auctionStatus);
				else
					auctions = ServicesImpl.getAuctionService().findByCategoryDown(ServicesImpl.getCategoryService().findById(Long.valueOf(category_id)));
			
				} else {
					if (auctionStatus!=null)
						auctions = ServicesImpl.getAuctionService().findByStatus(ServicesImpl.getAuctionService().findStatusByCode(status)) ;
					else 
						auctions = ServicesImpl.getAuctionService().findAll() ;
				}
			
			for (Auction a:auctions) {
				
				Map<String, Object> map = new LinkedHashMap<String, Object>() ;
				boolean started = (a.getStatus().getCode().equals(AuctionStatus.STATUS_STARTED)) ;
				String timeLeft = (String) cv.coerceToUi(a, null);
				
				if ((from!=null && a.getStartDate().compareTo(from)>=0) && (to!=null && a.getEndDate().compareTo(to)<=0) ){
					map.put("name", a.getName()) ;
					map.put("stat", a.getStatus().getCode()) ;
					map.put("started", started) ;
					map.put("dateFrom", a.getStartDate().toString()) ;
					map.put("dateTo", a.getEndDate().toString()) ;
					map.put("timeLeft", timeLeft) ;
					if (a.getBestUserOffer()!=null)
						map.put("price", a.getBestUserOffer().getPrice().intValue()) ;
					else 
						map.put("price", " - brak oferty - ") ;
					if(a.getCategory()!=null)
						map.put("category_id", a.getCategory().getId().intValue()) ;
					else
						map.put("category_id", 0) ;
					
					result.add(map) ;
				} else if (from==null || to==null) {
					map.put("name", a.getName()) ;
					map.put("stat", a.getStatus().getCode()) ;
					map.put("started", started) ;
					map.put("dateFrom", a.getStartDate().toString()) ;
					map.put("dateTo", a.getEndDate().toString()) ;
					map.put("timeLeft", timeLeft) ;
					if (a.getBestUserOffer()!=null)
						map.put("price", a.getBestUserOffer().getPrice().intValue()) ;
					else 
						map.put("price", " - brak oferty - ") ;
					if(a.getCategory()!=null)
						map.put("category_id", a.getCategory().getId().intValue()) ;
					else
						map.put("category_id", 0) ;
					
					result.add(map) ;
				}
			}
			
			
			
		
		} catch(Exception ex) {
			logger.error(ex.getLocalizedMessage());
		}
		
		return result ;
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
	
	@RequestMapping( value="/auctionStatus", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> auctionStatus(@RequestParam Integer id) {
		Map<String, Object> map = new LinkedHashMap<String, Object>() ;
		boolean operationStatus = false ;
		String operationMessage = "ERROR" ;
		try {
			Auction a = ServicesImpl.getAuctionService().findById(Long.valueOf(id)) ;
			Integer code = a.getStatus().getCode() ;
			map.put("status", code) ;
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
	
	@RequestMapping( value="/auctionStatus", method=RequestMethod.PUT)
	public @ResponseBody Map<String, Object> auctionStatus(@RequestParam Integer id, @RequestParam Integer status) {
		Map<String, Object> map = new LinkedHashMap<String, Object>() ;
		boolean operationStatus = false ;
		String operationMessage = "ERROR" ;
		try {
			Auction a = ServicesImpl.getAuctionService().findById(Long.valueOf(id)) ;
			AuctionStatus stat = ServicesImpl.getAuctionService().findStatusByCode(status) ; 
			a.setStatus(stat);
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
	
	@RequestMapping( value="/auctionStatusList", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> auctionStatusList() {
		Map<String, Object> map = new LinkedHashMap<String, Object>() ;
		Map<String, Object> mmap = new HashMap<String, Object>() ;
		boolean operationStatus = false ;
		String operationMessage = "ERROR" ;
		try {
			
			
			List<AuctionStatus> statuses = ServicesImpl.getAuctionService().findAllStatuses() ;
			for (AuctionStatus status:statuses){
				
				mmap.put("status", status.getCode()) ;
				
			}
			map.put("statusList", mmap) ;
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
	
	
	@RequestMapping( value="/offer", method=RequestMethod.POST, consumes={"application/json"})
	public @ResponseBody Map<String, Object> addOffer(@RequestBody Map<String, Object> json) {
		Map<String, Object> map = new LinkedHashMap<String, Object>() ;
		boolean operationStatus = false ;
		String operationMessage = "ERROR" ;
		
		 
		
		Integer auction_id = (Integer) json.get("auction_id");
		String value = (String) json.get("value");
		
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
	
	
	/**
	 * Rest WS method to use for user registation. 
	 * 
	 * Path: /user
	 * Method: POST
	 * 
	 * Consumes JSON Object, i.e.:
	 * 
	 * {'login':<i>login</i>, 'firstName':<i>firstName</i>, 'lastName':<i>lastName</i>,
	 *  'email':<i>email</i>, 'password':<i>password</i>,
	 *  'hashMethod':<i>md5|sha</i>, 'countryCode':<i>countryCode</i>, 'street':<i>street</i>,
	 *  'houseNo':<i>houseNo</i>, 'flatNo':<i>flatNo</i>, 'postCode':<i>postCode</i>, 'city':<i>city</i>,
	 *  'province':<i>province</i>}
	 *    
	 * 
	 * @param  json Object of type Map<String, Object> representing json, sent in post request
	 * @return      json, representing status of operation: {'operationStatus': true|false, 'operationMessage':'OK', '[error message accordingly to thrown exception]}
	 */
	
	@RequestMapping( value="/user", method=RequestMethod.POST, consumes={"application/json"})
	public @ResponseBody Map<String, Object> registerUser(@RequestBody Map<String, Object> json) {
		Map<String, Object> map = new LinkedHashMap<String, Object>() ;
		boolean operationStatus = false ;
		String operationMessage = "ERROR" ;
		
		try{
			
			String login = (String) json.get("login") ;
			String firstName = (String) json.get("firstName") ;
			String lastName = (String) json.get("lastName") ;
			String email = (String) json.get("email") ;
			String password = (String) json.get("password") ;
			String hashMethod = (String) json.get("hashMethod") ;
			String countryCode = (String) json.get("countryCode") ;
			String street = (String) json.get("street") ;
			String houseNo = (String) json.get("houseNo") ;
			String flatNo = (String) json.get("flatNo") ;
			String postCode = (String) json.get("postCode") ;
			String city = (String) json.get("city") ;
			String province = (String) json.get("province") ;
			
			User user = new User() ;
			Address mainAddress = new Address() ;
			
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setLogin(login);
			user.setEmail(email);
			
			user.setPassword(Utilities.hash(hashMethod, password));
			
			
			Locale locale = new Locale(countryCode, countryCode) ;
			
			mainAddress.setCity(city);
			mainAddress.setCountry(locale.getCountry());
			mainAddress.setFlatNo(flatNo);
			mainAddress.setHouseNo(houseNo);
			mainAddress.setDistrict(province);
			mainAddress.setPostal(postCode);
			mainAddress.setStreet(street);
			
			user.setMainAddress(mainAddress);
			
			if (ServicesImpl.getUserService().getUserByLogin(login)==null && ServicesImpl.getUserService().getUserByEmail(email)==null) {
				
				ServicesImpl.getUserService().save(user);
				operationStatus = true ;
				operationMessage = "OK" ;
				
			}
			
			
			
			
		} catch(Exception ex) {
			operationStatus = false ;
			operationMessage = ex.getLocalizedMessage() ;
		}
		
		map.put("operationStatus", operationStatus) ;
		map.put("operationMessage", operationMessage) ;
		return map ;
		
	}

	
	/**
	 * Rest WS method to use for get user info. 
	 * 
	 * Path: /user
	 * Method: GET
	 * 
	 * Consumes JSON Object, i.e.:
	 * 
	 * {'user_id':<i>user_id</i>, 'token':<i>token</i>}
	 *    
	 * 
	 * @param  json Object of type Map<String, Object> representing json, sent in post request
	 * @return      json, representing status of operation and user info: 
	 *              {'operationStatus': true|false, 'operationMessage':'OK', '[error message accordingly to thrown exception],
	 *               'login':<i>login</i>, 'firstName':<i>firstName</i>, 'lastName':<i>lastName</i>,
	 *  			 'email':<i>email</i>, 'country':<i>country</i>, 
	 *  			 'street':<i>street</i>, 'houseNo':<i>houseNo</i>, 'flatNo':<i>flatNo</i>, 'postCode':<i>postCode</i>, 
	 *  			 'city':<i>city</i>, 'province':<i>province</i>}
	 */
	
	@RequestMapping( value="/user", method=RequestMethod.GET, consumes={"application/json"})
	public @ResponseBody Map<String, Object> getUser(@RequestBody Map<String, Object> json) {
		Map<String, Object> map = new LinkedHashMap<String, Object>() ;
		boolean operationStatus = false ;
		String operationMessage = "ERROR" ;
		
		try{
			
			Long id = Long.valueOf((Integer) json.get("user_id"));
			String token = (String) json.get("token") ;
			User user = ServicesImpl.getUserService().getUserById(id);
			
			
			
			String userToken = Utilities.generateToken(user) ;
			
			if(userToken.equals(token)) {
				
				map.put("login", user.getLogin()) ;
				map.put("firstName", user.getFirstName()) ;
				map.put("lastName", user.getLastName()) ;
				map.put("email", user.getEmail()) ;
				map.put("country", user.getMainAddress().getCountry()) ;
				map.put("street", user.getMainAddress().getStreet()) ;
				map.put("houseNo", user.getMainAddress().getHouseNo()) ;
				map.put("flatNo", user.getMainAddress().getFlatNo()) ;
				map.put("postCode", user.getMainAddress().getPostal()) ;
				map.put("city", user.getMainAddress().getCity()) ;
				map.put("province", user.getMainAddress().getDistrict()) ;
				
				operationStatus = true ;
				operationMessage = "OK" ;
				
			}
			
			
		} catch(Exception ex){
			operationStatus = false ;
			operationMessage = ex.getLocalizedMessage() ;
		}
		
		map.put("operationStatus", operationStatus) ;
		map.put("operationMessage", operationMessage) ;
		return map ;
		
		}
	
	
	
	/**
	 * Rest WS method to use for get user info. 
	 * 
	 * Path: /user
	 * Method: GET
	 * 
	 * Consumes JSON Object, i.e.:
	 * 
	 * {'user_id':<i>user_id</i>, 'token':<i>token</i>, 'login':<i>login</i>, 'firstName':<i>firstName</i>, 'lastName':<i>lastName</i>,
	 *  'email':<i>email</i>, 'country':<i>country</i>, 'street':<i>street</i>, 'houseNo':<i>houseNo</i>, 'password':<i>password</i>, 'hashMethod':<i>hashMethod</i>
	 *  'flatNo':<i>flatNo</i>, 'postCode':<i>postCode</i>,'city':<i>city</i>, 'province':<i>province</i>}}
	 *    
	 * 
	 * @param  json Object of type Map<String, Object> representing json, sent in post request
	 * @return      json, representing status of operation and user info: 
	 *              {'operationStatus': true|false, 'operationMessage':<i>operationMessage</i>}
	 */
	
	@RequestMapping( value="/user", method=RequestMethod.PUT, consumes={"application/json"})
	public @ResponseBody Map<String, Object> updateUser(@RequestBody Map<String, Object> json) {
		Map<String, Object> map = new LinkedHashMap<String, Object>() ;
		boolean operationStatus = false ;
		String operationMessage = "ERROR" ;
		
		try{
			
			Long id = Long.valueOf((Integer) json.get("user_id"));
			String token = (String) json.get("token") ;
			User user = ServicesImpl.getUserService().getUserById(id);
			
			
			
			String userToken = Utilities.generateToken(user) ;
			
			if(userToken.equals(token)) {
				
				String login = (String) json.get("login") ;
				String firstName = (String) json.get("firstName") ;
				String lastName = (String) json.get("lastName") ;
				String email = (String) json.get("email") ;
				String password = (String) json.get("password") ;
				String hashMethod = (String) json.get("hashMethod") ;
				String countryCode = (String) json.get("countryCode") ;
				String street = (String) json.get("street") ;
				String houseNo = (String) json.get("houseNo") ;
				String flatNo = (String) json.get("flatNo") ;
				String postCode = (String) json.get("postCode") ;
				String city = (String) json.get("city") ;
				String province = (String) json.get("province") ;
				
				Address mainAddress = user.getMainAddress() ;
				
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setLogin(login);
				user.setEmail(email);
				
				user.setPassword(Utilities.hash(hashMethod, password));
				
				
				Locale locale = new Locale(countryCode, countryCode) ;
				
				mainAddress.setCity(city);
				mainAddress.setCountry(locale.getCountry());
				mainAddress.setFlatNo(flatNo);
				mainAddress.setHouseNo(houseNo);
				mainAddress.setDistrict(province);
				mainAddress.setPostal(postCode);
				mainAddress.setStreet(street);
				
				user.setMainAddress(mainAddress);
				
				if (ServicesImpl.getUserService().getUserByLogin(login)==null && ServicesImpl.getUserService().getUserByEmail(email)==null) {
					
					ServicesImpl.getUserService().update(user);
					operationStatus = true ;
					operationMessage = "OK" ;
					
				}
				
				
				
			}
			
			
		} catch(Exception ex){
			operationStatus = false ;
			operationMessage = ex.getLocalizedMessage() ;
		}
		
		map.put("operationStatus", operationStatus) ;
		map.put("operationMessage", operationMessage) ;
		return map ;
		
		}
	
	
	
	/**
	 * Rest WS method to use for get user info. 
	 * 
	 * Path: /categories
	 * Method: GET
	 * 
	 * Consumes JSON Object, i.e.:
	 * 
	 * {'parent_id':<i>parent_id|null</i>}}
	 *    
	 * 
	 * @param  json Object of type Map<String, Object> representing json, sent in post request
	 * @return      json, representing status of operation and user info: 
	 *              [ { 'category_id':<i>category_id</i>, 
	 *               'name':<i>name</i>, 'description':<i>description</i>, 'parent_id':<i>parent_id</i> },  {...}, ... ,  {...} ]
	 */
	
	@RequestMapping( value="/categories/{parent_id}", method=RequestMethod.GET)
	public @ResponseBody List<Map<String,Object>> getCategories(@PathVariable Integer parent_id) {
		
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>() ;
		boolean operationStatus = false ;
		String operationMessage = "ERROR" ;
		Locale locale = Locale.getDefault() ;
		try{
			
			Long lid = Long.valueOf(parent_id) ;
			List<Category> cats = new ArrayList<Category>(); 
			if (lid!=null && lid!=0) {
				cats = ServicesImpl.getCategoryService().findSubCategoriesByLang(ServicesImpl.getCategoryService().findById(lid), locale.getLanguage()) ;
			} else {
				cats = ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
			}
			
			
			
			
			
			
			for (Category c: cats) {
				
				Map<String, Object> vals = new LinkedHashMap<String, Object>() ;
				
				vals.put("category_id", c.getId().intValue()) ;
				vals.put("name", c.getName()) ;
				vals.put("description", c.getDescription()) ;
				vals.put("parent_id", c.getParent()!=null?c.getParent().getId().intValue():0) ;
				
				map.add(vals) ;
			}
			
			
			
			operationStatus = true ;
			operationMessage = "OK" ;
			
		}catch(Exception ex){
			operationStatus = false ;
			operationMessage = ex.getLocalizedMessage() ;
		}
				
		
		
		
		return map ;
		
	}
	
	
	
	/**
	 * Rest WS method to use for get user info. 
	 * 
	 * Path: /sendMail
	 * Method: POST
	 * 
	 * Consumes JSON Object, i.e.:
	 * 
	 * { sender:'', title:'', mailTo:'', firstName:'', lastName:'', email:'', subject:'', message:'' }
	 *    
	 * 
	 * @param  json Object of type Map<String, Object> representing json, sent in post request
	 * @return      json, representing status of operation and user info: 
	 *              [ { 'category_id':<i>category_id</i>, 
	 *               'name':<i>name</i>, 'description':<i>description</i>, 'parent_id':<i>parent_id</i> },  {...}, ... ,  {...} ]
	 */
	@RequestMapping( value="/sendMail", method=RequestMethod.PUT, consumes={"application/json"})
	public @ResponseBody Map<String, Object> sendMail(@RequestBody Map<String, Object> json) {
		Map<String, Object> map = new LinkedHashMap<String, Object>() ;
		boolean operationStatus = false ;
		String operationMessage = "ERROR" ;
		
		String sender = (String) json.get("sender") ;
		String to = (String) json.get("mailTo") ;
		String firstName = (String) json.get("firstName") ;
		String lastName = (String) json.get("lastName") ;
		String email = (String) json.get("email") ;
		String subject = (String) json.get("subject") ;
		String message = (String) json.get("message") ;		
		String title = (String) json.get("title") ;
		
		
		Map<String, Object> model = new HashMap<String, Object>() ;
		
		model.put("firstName", firstName) ;
		model.put("lastName", lastName) ;
		model.put("email", email) ;
		model.put("subject", subject) ;
		model.put("message", message) ;
		model.put("sender", sender) ;
		
		
		
		
		try{
			
		
			ServicesImpl.getMailService().sendMail("mail_contact_form_mail.ftl", model, title, to);
			
		} catch(Exception ex){}
		
		return map ;
	}
	
	
	
}
