package pl.krzaq.metalscrap.pages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;







import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;

import pl.krzaq.metalscrap.model.Address;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.Company;
import pl.krzaq.metalscrap.model.Role;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class Login implements Initiator, InitiatorExt {

	
	private boolean confirm = false ;
	
	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
		// TODO Auto-generated method stub
		//super.doInit(page, arg1);
		// wyświetlanie sub menu
		Boolean isCategoriesVisible = Boolean.valueOf(ServicesImpl.getConfigService().findByKey("auction_categories_visible").getValue()) ;
		Boolean isCommoditiesVisible = Boolean.valueOf(ServicesImpl.getConfigService().findByKey("auction_commodities_visible").getValue());
		boolean userVerificationModeAuto = Boolean.valueOf(ServicesImpl.getConfigService().findByKey("user.verification.mode.auto").getValue()).booleanValue() ;
		
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		Address main = new Address() ;
		Address additional = new Address() ;
		Company company = new Company() ;
		User user = new User() ;
		List<User> users = new ArrayList<User>() ;
		Set<Role> roles = new HashSet<Role>() ;
		user.setRoles(roles);
		company.setUsers(users);
		company.setAddressMain(main);
		company.setAddressAdditional(additional);
		page.setAttribute("user", user) ;
		page.setAttribute("company", company) ;
		
		boolean isUser = false;
		boolean isAdmin = false;
		boolean isSuperAdmin = false;
		
		List<Category> categories = ServicesImpl.getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
		List<Auction> auctions = ServicesImpl.getAuctionService().findByStatus(ServicesImpl.getAuctionService().findStatusByCode(AuctionStatus.STATUS_STARTED)) ;
		
		page.setAttribute("auctionsSubMenu", false);
		page.setAttribute("companiesSubMenu", false);
		
		page.setAttribute("isCommoditiesVisible", isCommoditiesVisible) ;
		page.setAttribute("isCategoriesVisible", isCategoriesVisible) ;
		
		page.setAttribute("categoryAuctions", auctions) ;
		page.setAttribute("categoryModel", categories) ;
		page.setAttribute("selectedCategory", null) ;
		
		page.setAttribute("isUser", isUser);
		page.setAttribute("isAdmin", isAdmin);
		page.setAttribute("isSuperAdmin", isSuperAdmin);
		
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest() ;
		String token = request.getParameter("confirmation") ;
		
		
		boolean confirmation = false ;
		boolean completed = false ;
		if(token!=null) {
			confirmation = true ;
			page.setAttribute("token", token) ;
			
			List<User> allUsers = ServicesImpl.getUserService().getUsers() ;
			for(User u:allUsers) {
				String utoken = u.getToken() ;
				if(utoken.equals(token) && !u.getStatus().equals(User.STATUS_CONFIRMED)) {
					user = u ;
					this.confirm = true ;
					break ;
				}
			}
			
			if(confirm) {
				if(userVerificationModeAuto) {
					user.setStatus(User.STATUS_CONFIRMED);
					user.setCompleted(false);
				} else {
					user.setStatus(User.STATUS_VERIFIED);
					user.setCompleted(false);
				}
				
				
				
				ServicesImpl.getUserService().update(user);
				confirmation = true ;
			}
		}
		
		completed =  (user.getStatus()!=null && user.getStatus().equals(User.STATUS_CONFIRMED)) ;
		page.setAttribute("confirmation", confirmation) ;
		page.setAttribute("confirm", this.confirm) ;
		page.setAttribute("completed", completed) ;
	}

	@Override
	public void doAfterCompose(Page arg0, Component[] arg1) throws Exception {
		// TODO Auto-generated method stub
		//super.doAfterCompose(arg0, arg1);
	}

	@Override
	public void doFinally() throws Exception {
		// TODO Auto-generated method stub
	//	super.doFinally();
	}

	@Override
	public boolean doCatch(Throwable arg0) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	
	
	
}
