package pl.krzaq.metalscrap.bind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Textbox;

import pl.krzaq.metalscrap.converter.LanguageISOConverter;
import pl.krzaq.metalscrap.model.Address;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class UserAccountBind {

	@Wire("#userMenu")
	private Menubar menu ;
	
	@Wire("#useraccount")
	private Page page ;
	
	
	// Rejestracja
	
		@Wire("#username")
		private Textbox username ;
		
		@Wire("#email")
		private Textbox email ;
		
		@Wire("#password")
		private Textbox password ;
		
		@Wire("#repassword")
		private Textbox repassword ;
		
		@Wire("#error")
		private Div error ;
	
	
	private LangConverter langConverter ;	
		
	private List<String> countries ;	
		
	private User user ;
	
	private Address main ;
	
	private Address additional ;
	
	private boolean useAdditional ;
	
	private List<Auction> observed ;
	
	private List<Auction> won ;
	
	private List<Auction> lost ;
	
	private List<Auction> active ;
	
	private List<Auction> owned ;
	
	
	
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		
		Selectors.wireComponents(view, this, false);
		
		this.page = menu.getPage() ;
		
		// pobranie aktualnego użytkownika
		this.user = (User) page.getAttribute("currentUser") ;
		
		
		// pobranie adresów użytkownika
		if (this.user.getMainAddress()!=null) {
			this.main = user.getMainAddress() ;
		} else {
			this.main = new Address() ;
		}
		
		if(this.user.getContactAddress()!=null) {
			this.additional = this.user.getContactAddress() ;
		} else {
			this.additional = null ;
		}
		
		if(this.additional!=null) {
			this.useAdditional = true ;
		} else {
			this.useAdditional = false ;
		}
		
		
		// pobranie list aukcji
		
		observed = ServicesImpl.getAuctionService().findByObserver(user) ;
		
		won = ServicesImpl.getAuctionService().findWonByUser(user) ;
		
		lost  = ServicesImpl.getAuctionService().findLostByUser(user) ;
		
		active = ServicesImpl.getAuctionService().findActiveByUser(user) ;
		
		owned = ServicesImpl.getAuctionService().findOwnedByUser(user) ;
		
		
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		countries = new ArrayList<String>() ;
		countries.addAll(Arrays.asList(Locale.getISOCountries()));
		
		langConverter = new LangConverter();
		
	}
	
	
	
	
private Boolean allowRegistration = false ;
	
	private boolean emailExists = false ;
	private boolean loginExists = false ;
	private boolean passwordNotFormatted = false ;
	private boolean passwordNotMatch = false ;
	
	private boolean step1 = true ;
	private boolean step2 = false ;
	
	private String loginMessage ="";
	private String emailMessage ="";
	private String passMessage ="";
	private String repassMessage ="";
	private String rePassword="" ;
	
	@Command
	@NotifyChange({"loginExists", "allowRegistration", "loginMessage"})
	public void checkLogin() {
		
		if (ServicesImpl.getUserService().getUserByLogin(user.getLogin()) != null ) {
			allowRegistration = false ;
			loginExists = true ;
			loginMessage = "Podana nazwa użytkownika jest już zajęta" ;
			username.setSclass("default wrongValue");
		} else {
			allowRegistration = !loginExists && !emailExists && !passwordNotFormatted && !passwordNotMatch ;
			loginExists = false ;
			loginMessage = "" ;
			username.setSclass("default correctValue");
		}
		
		resize() ;
		
	}
	
	@Command
	@NotifyChange({ "emailExists", "allowRegistration", "emailMessage"})
	public void checkEmail() {
		
		if (ServicesImpl.getUserService().getUserByEmail(user.getEmail()) != null ) {
			allowRegistration = false ;
			emailExists = true ;
			emailMessage = "Podany adres e-mail jest już zajęty" ;
			email.setSclass("default wrongValue");
		} 
		/*else
		if (!user.getEmail().matches("([_A-Za-z0-9-]+)(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})")) {
			allowRegistration = false ;
			emailExists = true ;
			message = "To nie jest poprawny adres e-mail" ;
			email.setSclass("default wrongValue");
		}*/
		else {
			allowRegistration = !loginExists && !emailExists && !passwordNotFormatted && !passwordNotMatch ;
			emailExists = false ;
			emailMessage = "" ;
			email.setSclass("default correctValue");
		}
		resize() ;
		
	}
	
	@Command
	@NotifyChange({ "passwordNotFormatted", "allowRegistration", "passMessage"})
	public void checkPassword() {
		
		if( user.getPassword().matches("\\b(?=.{6,})(?=.*[a-z])(?=.*[A-Z])(?=.*[_-]).*$\\b")) {
			passwordNotFormatted = false ;
			allowRegistration =  !loginExists && !emailExists && !passwordNotFormatted && !passwordNotMatch ;
			
			passMessage="" ;
			password.setSclass("default correctValue");
		} else {
			allowRegistration = false ;
			passwordNotFormatted = true ;
			passMessage = "Hasło musi składać się z przynajmniej 6 znaków oraz zawierać chociaż jedną małą, jedną dużą literę i cyfrę" ;
			password.setSclass("default wrongValue");
		}
		resize() ;
		
	}
	
	@Command
	@NotifyChange({"passwordNotMatch", "allowRegistration", "repassMessage"})
	public void checkPasswordMatch() {
		
		if(!user.getPassword().equals(rePassword)) {
			allowRegistration = false ;
			passwordNotMatch = true ;
			repassMessage = "Hasła różnią się" ;
			repassword.setSclass("default wrongValue");
		} else {
			allowRegistration = true ;
			passwordNotMatch= false ;
			repassMessage = "" ;
			repassword.setSclass("default correctValue");
		}
		resize() ;
		
	}
	
	
	@Command
	public void checkPostalFormat(@BindingParam("val") String value, @BindingParam("cmp") Textbox tbox, @ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		
		tbox=(Textbox) event.getTarget() ;
		value=event.getValue();
		String regex = "/[0-9]{2}-{1}[0-9]{3}/" ;
		StringBuilder sb = new StringBuilder() ;
		tbox.setDisabled(true);
		int position = event.getStart() ;
		
		if(value.length()==1) {
			sb.append(value) ;
			sb.append("_-___") ;				
		} else {
		
		if(position<value.length())
			value=value.substring(0, position).concat(value.substring(position+1, value.length())) ;
		
			char[] content = value.toCharArray() ;
			
			//if(position==3) {
				sb.append(content.length>=1?content[0]:"_") ;
				sb.append(content.length>=2?content[1]:"_") ;
				sb.append("-") ;
				if(position==3) {
					sb.append(content.length>=3?content[2]:"_") ;
				} else {
					sb.append(content.length>=4?content[3]:"_") ;
				}
				
				sb.append(content.length>=5?content[4]:"_") ;
				sb.append(content.length>=6?content[5]:"_") ;
			//}
			
			
		}
		
		tbox.setValue(sb.toString()) ;
		if(position==3) {
			tbox.setSelectionRange(position+1, position+1);
		} else {
			tbox.setSelectionRange(position, position);
		}
		
		tbox.setDisabled(false);
		//tbox.set
		
		
		
		
		
	}
	
	
	
	private void resize() {
		 int count = 0 ;
			if(loginExists) {
				count++ ;
			}
			if(emailExists){
				count++ ;
			}
			if(passwordNotMatch){
				count++ ;
			}
			if(passwordNotFormatted){
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
			} else
			if(count==3) {
				error.setSclass("error three");
			} else
			if(count==4) {
				error.setSclass("error four");
			}
		 
	 }
	
	
	@Command
	public void selectMenuitem(@BindingParam("item") Menuitem item) {
		item.setSclass("userAccount visited");
	}


	public Menubar getMenu() {
		return menu;
	}


	public void setMenu(Menubar menu) {
		this.menu = menu;
	}


	public Page getPage() {
		return page;
	}


	public void setPage(Page page) {
		this.page = page;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Address getMain() {
		return main;
	}


	public void setMain(Address main) {
		this.main = main;
	}


	public Address getAdditional() {
		return additional;
	}


	public void setAdditional(Address additional) {
		this.additional = additional;
	}


	public boolean isUseAdditional() {
		return useAdditional;
	}


	public void setUseAdditional(boolean useAdditional) {
		this.useAdditional = useAdditional;
	}


	public List<Auction> getObserved() {
		return observed;
	}


	public void setObserved(List<Auction> observed) {
		this.observed = observed;
	}


	public List<Auction> getWon() {
		return won;
	}


	public void setWon(List<Auction> won) {
		this.won = won;
	}


	public List<Auction> getLost() {
		return lost;
	}


	public void setLost(List<Auction> lost) {
		this.lost = lost;
	}


	public List<Auction> getActive() {
		return active;
	}


	public void setActive(List<Auction> active) {
		this.active = active;
	}


	public List<Auction> getOwned() {
		return owned;
	}


	public void setOwned(List<Auction> owned) {
		this.owned = owned;
	}

	public Textbox getUsername() {
		return username;
	}

	public void setUsername(Textbox username) {
		this.username = username;
	}

	public Textbox getEmail() {
		return email;
	}

	public void setEmail(Textbox email) {
		this.email = email;
	}

	public Textbox getPassword() {
		return password;
	}

	public void setPassword(Textbox password) {
		this.password = password;
	}

	public Textbox getRepassword() {
		return repassword;
	}

	public void setRepassword(Textbox repassword) {
		this.repassword = repassword;
	}

	public Div getError() {
		return error;
	}

	public void setError(Div error) {
		this.error = error;
	}

	public Boolean getAllowRegistration() {
		return allowRegistration;
	}

	public void setAllowRegistration(Boolean allowRegistration) {
		this.allowRegistration = allowRegistration;
	}

	public boolean isEmailExists() {
		return emailExists;
	}

	public void setEmailExists(boolean emailExists) {
		this.emailExists = emailExists;
	}

	public boolean isLoginExists() {
		return loginExists;
	}

	public void setLoginExists(boolean loginExists) {
		this.loginExists = loginExists;
	}

	public boolean isPasswordNotFormatted() {
		return passwordNotFormatted;
	}

	public void setPasswordNotFormatted(boolean passwordNotFormatted) {
		this.passwordNotFormatted = passwordNotFormatted;
	}

	public boolean isPasswordNotMatch() {
		return passwordNotMatch;
	}

	public void setPasswordNotMatch(boolean passwordNotMatch) {
		this.passwordNotMatch = passwordNotMatch;
	}

	public boolean isStep1() {
		return step1;
	}

	public void setStep1(boolean step1) {
		this.step1 = step1;
	}

	public boolean isStep2() {
		return step2;
	}

	public void setStep2(boolean step2) {
		this.step2 = step2;
	}

	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}

	public String getPassMessage() {
		return passMessage;
	}

	public void setPassMessage(String passMessage) {
		this.passMessage = passMessage;
	}

	public String getRepassMessage() {
		return repassMessage;
	}

	public void setRepassMessage(String repassMessage) {
		this.repassMessage = repassMessage;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public List<String> getCountries() {
		return countries;
	}

	public void setCountries(List<String> countries) {
		this.countries = countries;
	}

	
	public LangConverter getLangConverter() {
		return langConverter;
	}

	public void setLangConverter(LangConverter langConverter) {
		this.langConverter = langConverter;
	}

	
	
	// --------------------------------------------------------------------
	
	




	private class LangConverter implements Converter{
		
		public LangConverter(){
			
		}

		@Override
		public Object coerceToUi(Object beanProp, Component component,
				BindContext ctx) {
			
			String localeCode = (String) beanProp ;
			Locale loc = new Locale(localeCode,localeCode) ;
			HttpSession session = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
			Locale currentLocale = (Locale) session.getAttribute(Attributes.PREFERRED_LOCALE) ;
			
			String country = loc.getDisplayLanguage(currentLocale) ;
			return country ;
		}

		@Override
		public Object coerceToBean(Object compAttr, Component component,
				BindContext ctx) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
	}
	
	
	
}
