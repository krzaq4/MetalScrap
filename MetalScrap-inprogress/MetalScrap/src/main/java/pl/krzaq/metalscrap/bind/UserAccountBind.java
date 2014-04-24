package pl.krzaq.metalscrap.bind;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStreamImpl;
import javax.imageio.stream.ImageOutputStreamImpl;
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
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkex.zul.Fisheyebar;
import org.zkoss.zul.Div;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import pl.krzaq.metalscrap.converter.LanguageISOConverter;
import pl.krzaq.metalscrap.model.Address;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;
import pl.krzaq.metalscrap.utils.Utilities;

public class UserAccountBind {

	@Wire("#userMenu")
	private Menubar menu ;
	
	@Wire("#useraccount")
	private Page page ;
	
	
	// Rejestracja
	
		@Wire("#userPendingVerificationInfo")
		private Div userPendingVerificationInfo;
		
		@Wire("#userCompleteInfo")
		Div userCompleteInfo ;
	
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
		
		@Wire("#avatar")
		private Image avatar ;
		
		@Wire("#avatars")
		private Fisheyebar avatars ;
		
		//=============================
		
		@Wire("#street")
		private Textbox street ;
		
		@Wire("#houseNo")
		private Textbox houseNo ;
		
		@Wire("#postal")
		private Textbox postal ;
		
		@Wire("#city")
		private Textbox city ;
		
		@Wire("#flatNo")
		private Textbox flatNo ;
		
		@Wire("#addstreet")
		private Textbox addStreet ;
		
		@Wire("#addhouseNo")
		private Textbox addHouseNo ;
		
		@Wire("#addpostal")
		private Textbox addPostal ;
		
		@Wire("#addcity")
		private Textbox addCity ;
		
		@Wire("#addflatNo")
		private Textbox addFlatNo ;
		
		@Wire("#country")
		private Listbox country ;
		
		@Wire("#addcountry")
		private Listbox addCountry ;
		
		//=========================================
		
		@Wire("#firstname")
		private Textbox firstName ;
		
		@Wire("#lastname")
		private Textbox lastName ;
		
		@Wire("#mobile")
		private Textbox mobile ;
		
		@Wire("#uploader")
		private Fileupload uploader ;
		
		private org.zkoss.image.AImage image ;
		
		private boolean allowComplete = false ;
		private boolean allowSave = false ;
		
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
		
		private boolean mainStreetChanged = false ;
		private boolean streetChanged = false ;
		
		private boolean mainFlatNoChanged = false ;
		private boolean flatNoChanged = false ;
		
		private String mainHouseNoMsg="" ;
		private boolean mainHouseNoError=false;

		private String houseNoMsg="" ;
		private boolean houseNoError=false;
		
		private String mainCityMsg="" ;
		private boolean mainCityError=false;
		
		private String cityMsg="" ;
		private boolean cityError=false;

		private String mainPostalMsg="" ;
		private boolean mainPostalError=false;
		
		private String postalMsg="" ;
		private boolean postalError=false;
	
		private boolean firstNameError = false ;
		
		private boolean lastNameError = false ;
		
		private boolean mobileError = false ;
		
	
		private LangConverter langConverter ;	
		
		private List<String> countries ;	
		
		private User user ;
	
		private Address main ;
	
		private Address additional ;
	
		private boolean useAdditional  ;
	
		private List<Auction> observed ;
	
		private List<Auction> won ;
	
		private List<Auction> lost ;
	
		private List<Auction> active ;
	
		private List<Auction> owned ;
	
		private String verificationType ;
	
		
	@Command
	public void uploadFile(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event){
		Media media = event.getMedia();
		String dataDir = System.getProperty("jboss.server.data.dir") ;
		String userDir = dataDir.concat("\\platform\\users\\").concat(user.getLogin()) ;
		String avatarDir = userDir.concat("\\avatar") ;
		File avatarDirFolder = new File(avatarDir) ;
		if(avatarDirFolder.listFiles().length<3) {
			
		
			if(media instanceof org.zkoss.image.Image) {
			
				FileOutputStream file;
				String fileName = media.getName()+"."+media.getFormat() ;
				File f = new File(userDir.concat("\\avatar\\"+ fileName )) ;
			
				try {
					f.createNewFile() ;
					file = new FileOutputStream(userDir.concat("\\avatar\\"+fileName ));
					file.write(((Media)media).getByteData());
					file.close();
					user.setAvatarFileName(fileName);
					ServicesImpl.getUserService().update(user);
				
					avatar.setContent(scaleImage(225,225,(AImage)media,f));
					prepareAvatars();
				} catch (FileNotFoundException e) {
					Messagebox.show("Nie udało się zapisać pliku") ;
				} catch (IOException e) {
					Messagebox.show("Błąd dostępu do systemu plików") ;
				}
			
			}
		
		} else {
			Messagebox.show("Osiągnąłeś limit ilości awatarów") ;
		}
	}
	
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
			user.setMainAddress(new Address());
			user.getMainAddress().setCountry("PL");
		}
		
		if(this.user.getContactAddress()!=null) {
			this.additional = this.user.getContactAddress() ;
		} else {
			this.additional = null ;
			user.setContactAddress(null);
			//user.getContactAddress().setCountry("PL");
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
		
		if(user.getCompleted())
			userCompleteInfo.setSclass("error");
		else
			userCompleteInfo.setSclass("one error");
		
		
		
		
		prepareVerificationInfo();
		prepareAvatar();
		prepareAvatars() ;
		
		validateUserData() ;
	}
	
@NotifyChange({"user","allowSave", "allowComplete"})
private void validateUserData() {
		
		Boolean verificationModeAuto = Boolean.valueOf(ServicesImpl.getConfigService().findByKey("user.verification.mode.auto").getValue()) ;
		
		if(!verificationModeAuto || user.getStatus().equals(User.STATUS_PENDING_VERIFICATION)) {
			
			String verificationType = ServicesImpl.getConfigService().findByKey("user.verification.mode.type").getValue() ;
			
			if(verificationType.equals("sms") && user.getStatus().equals(User.STATUS_PENDING_VERIFICATION) ) {
				mobile.setDisabled(true);
				
			} else 
			if(verificationType.equals("letter") && user.getStatus().equals(User.STATUS_PENDING_VERIFICATION)) {
				
				firstName.setDisabled(true);
				lastName.setDisabled(true);
				
				street.setDisabled(true);
				houseNo.setDisabled(true);
				flatNo.setDisabled(true);
				postal.setDisabled(true);
				city.setDisabled(true);
				country.setDisabled(true) ;
				
				if(user.getContactAddress()!=null) {
					addStreet.setDisabled(true);
					addHouseNo.setDisabled(true);
					addFlatNo.setDisabled(true);
					addPostal.setDisabled(true);
					addCity.setDisabled(true);
					addCountry.setDisabled(true) ;
				}
				
			}
			
		} else 
		if (verificationModeAuto || user.getStatus().equals(User.STATUS_VERIFIED)) {
			firstName.setDisabled(false);
			lastName.setDisabled(false);
			mobile.setDisabled(false);
		}
		
	}
	
	@NotifyChange({"user"})
	private void prepareVerificationInfo() {
		Boolean userVerificationAuto = Boolean.valueOf(ServicesImpl.getConfigService().findByKey("user.verification.mode.auto").getValue());	
		verificationType = ServicesImpl.getConfigService().findByKey("user.verification.mode.type").getValue() ;
		if(!userVerificationAuto && user.getStatus().equals(User.STATUS_PENDING_VERIFICATION)) {
			userPendingVerificationInfo.setSclass("ok infor");
		} else 
		if (userVerificationAuto || user.getStatus().equals(User.STATUS_VERIFIED)) {
			userPendingVerificationInfo.setSclass("infor");
		}
	}
	
	@Command
	@NotifyChange({"useAdditional", "user", "allowComplete", "allowSave"})
	public void useAdditional() {
		if(useAdditional) {
			Address ad = new Address() ;
			ad.setCountry("PL");
			user.setContactAddress(ad);
		} else {
			user.setContactAddress(null);
		}
		
		boolean additionalAddressValid = user.getContactAddress()==null || (user.getContactAddress()!=null && !checkHouseNo(null)  && !checkCity(null) &&  !checkPostal(null) ) ;
		boolean mainAddressValid = !checkMainHouseNo(null) &&  !checkMainCity(null)  && !checkMainPostal(null) ;
	
		boolean personalDataValid = !checkFirstName(null) && !checkLastName(null) && !checkMobile(null) ;
	
		allowSave = additionalAddressValid && mainAddressValid && personalDataValid ;
		allowComplete = additionalAddressValid && mainAddressValid && personalDataValid ;
	}
	
	@Command
	public void changeAvatar(){
		
		
		
	}

	
	@Command
	@NotifyChange({"allowSave", "allowComplete"})
	public boolean checkFirstName(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event){
		

		if(firstName.getValue()!=null && firstName.getValue().length()>0 || ( event!=null && firstName.getValue()!=null && event.getValue().length()>0 && !event.getValue().equals(firstName.getValue())) ) {
			if(event!=null) {
				this.setFirstNameError(false) ;
			} else {
				return false ;
			}
		} else {
			if(event!=null) {
				this.setFirstNameError(true) ;
			} else {
				return true ;
			}
		}
		
		
		if (event!=null) {
			boolean additionalAddressValid = user.getContactAddress()==null || (user.getContactAddress()!=null && !checkHouseNo(null)  && !checkCity(null) &&  !checkPostal(null) ) ;
			boolean mainAddressValid = !checkMainHouseNo(null) &&  !checkMainCity(null)  && !checkMainPostal(null) ;
		
			boolean personalDataValid = !checkFirstName(null) && !checkLastName(null) && !checkMobile(null) ;
		
			allowSave = additionalAddressValid && mainAddressValid && personalDataValid ;
			allowComplete = additionalAddressValid && mainAddressValid && personalDataValid ;
		}
		
		
		return firstNameError ;
	}
	
	@Command
	@NotifyChange({"allowSave", "allowComplete"})
	public boolean checkLastName(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		

		if(lastName.getValue()!=null && lastName.getValue().length()>0 || ( event!=null && lastName.getValue()!=null && event.getValue().length()>0 && !event.getValue().equals(lastName.getValue()))) {
			if(event!=null) {
				this.setLastNameError(false) ;
			} else {
				return false ;
			}
		} else {
			if(event!=null) {
				this.setLastNameError(true) ;
			} else {
				return true ;
			}
		}
		
		if (event!=null) {
			boolean additionalAddressValid = user.getContactAddress()==null || (user.getContactAddress()!=null && !checkHouseNo(null)  && !checkCity(null) &&  !checkPostal(null) ) ;
			boolean mainAddressValid = !checkMainHouseNo(null) &&  !checkMainCity(null)  && !checkMainPostal(null) ;
		
			boolean personalDataValid = !checkFirstName(null) && !checkLastName(null) && !checkMobile(null) ;
		
			allowSave = additionalAddressValid && mainAddressValid && personalDataValid ;
			allowComplete = additionalAddressValid && mainAddressValid && personalDataValid ;
		}
		
		
		return lastNameError ;
		
	}
	
	@Command
	@NotifyChange({"allowSave", "allowComplete"})
	public boolean checkMobile(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		
		String confirmationType = ServicesImpl.getConfigService().findByKey("user.verification.mode.type").getValue() ;
		
		if(mobile.getValue()!=null && mobile.getValue().length()>0 && mobile.getValue().matches("(\\b[0-9]+-*\\b)+") || ( event!=null && mobile.getValue()!=null && event.getValue().length()>0 && event.getValue().matches("(\\b[0-9]+-*\\b)+") && !event.getValue().equals(mobile.getValue()))) {
			if(event!=null) {
				this.setMobileError(false);
			} else {
				return false && confirmationType.equals("sms") ;
			}
		} else {
			if(event!=null) {
				this.setMobileError(true);
			} else {
				return true && confirmationType.equals("sms") ;
			}
		}
		
		if(event!=null) {
			this.setMobileError(mobileError && confirmationType.equals("sms"));
		} else {
			return mobileError && confirmationType.equals("sms") ;
		}
		
		
		if (event!=null) {
			boolean additionalAddressValid = user.getContactAddress()==null || (user.getContactAddress()!=null && !checkHouseNo(null)  && !checkCity(null) &&  !checkPostal(null) ) ;
			boolean mainAddressValid = !checkMainHouseNo(null) &&  !checkMainCity(null)  && !checkMainPostal(null) ;
		
			boolean personalDataValid = !checkFirstName(null) && !checkLastName(null) && !checkMobile(null) ;
		
			allowSave = additionalAddressValid && mainAddressValid && personalDataValid ;
			allowComplete = additionalAddressValid && mainAddressValid && personalDataValid ;
		}
		
		
		return mobileError ;
		
		
	}
	
	
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
			allowRegistration =  !emailExists && !passwordNotFormatted && !passwordNotMatch ;
			emailExists = false ;
			emailMessage = "" ;
			email.setSclass("default correctValue");
		}
		resize() ;
		
	}
	
	@Command
	@NotifyChange({ "passwordNotFormatted", "allowRegistration", "passMessage"})
	public boolean checkPassword(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		
		if( user.getPassword().matches("\\b(?=.{6,})(?=.*[a-z])(?=.*[A-Z])(?=.*[_-]).*$\\b")) {
			passwordNotFormatted = false ;
			
			
			passMessage="" ;
			password.setSclass("default correctValue");
		} else {
			allowRegistration = false ;
			passwordNotFormatted = true ;
			passMessage = "Hasło musi składać się z przynajmniej 6 znaków oraz zawierać chociaż jedną małą, jedną dużą literę i cyfrę" ;
			password.setSclass("default wrongValue");
		}
		
		if(event!=null) {
			allowRegistration =   !passwordNotFormatted && !checkPasswordMatch(null) ;
		}
		
		resize() ;
		return passwordNotFormatted; 
	}
	
	@Command
	@NotifyChange({"passwordNotMatch", "allowRegistration", "repassMessage"})
	public boolean checkPasswordMatch(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		
		if(!user.getPassword().equals(rePassword)) {
			
			passwordNotMatch = true ;
			repassMessage = "Hasła różnią się" ;
			repassword.setSclass("default wrongValue");
		} else {
			
			passwordNotMatch= false ;
			repassMessage = "" ;
			repassword.setSclass("default correctValue");
		}
		
		if(event!=null) {
			allowRegistration =   !checkPassword(null) && !passwordNotMatch ;
		}
		
		resize() ;
		return passwordNotMatch ;
	}
	
	
	
	
	@Command
	@NotifyChange({"allowComplete", "allowSave"})
	public boolean checkMainStreet(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		
		if(!street.getValue().equals(user.getMainAddress().getStreet()) || (event!=null && event.getValue().length()>0 && !event.getValue().equals(user.getMainAddress().getStreet()) ) ) {
			if(event!=null) {
				this.setMainStreetChanged(true);
			} else {
				return true ;
			}
			
		} else {
			if(event!=null) {
				this.setMainStreetChanged(false);
			} else {
				return false ;
			}
			
		}
		
		if (event!=null) {
			boolean additionalAddressValid = user.getContactAddress()==null || (user.getContactAddress()!=null && !checkHouseNo(null)  && !checkCity(null) &&  !checkPostal(null) ) ;
			boolean mainAddressValid = !checkMainHouseNo(null) &&  !checkMainCity(null)  && !checkMainPostal(null) ;
		
			boolean personalDataValid = !checkFirstName(null) && !checkLastName(null) && !checkMobile(null) ;
		
			allowSave = additionalAddressValid && mainAddressValid && personalDataValid ;
			allowComplete = additionalAddressValid && mainAddressValid && personalDataValid ;
		}
		
		
		/*if(!checkMainHouseNo(null) && !checkHouseNo(null) && !checkMainCity(null) && !checkCity(null) && !checkMainPostal(null) && !checkPostal(null) && checkStreet(null) && checkMainStreet(null) && checkFlatNo(null) && checkMainFlatNo(null)) {
			allowSave = streetChanged || mainStreetChanged ;
			allowComplete = streetChanged || mainStreetChanged ;
		} else {
			allowSave = flatNoChanged || mainFlatNoChanged ;
			allowComplete = flatNoChanged || mainFlatNoChanged;
		}*/
		
		return mainStreetChanged ;
	}
	
	@Command
	@NotifyChange({"allowComplete", "allowSave"})
	public boolean checkMainFlatNo(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		
		if(!flatNo.getValue().equals(user.getMainAddress().getFlatNo()) || (event!=null && event.getValue().length()>0 && !event.getValue().equals(user.getMainAddress().getFlatNo()) )) {
			if(event!=null) {
				this.setMainFlatNoChanged(true);
			} else {
				return true ;
			}
				
		} else {
			if(event!=null) {
				this.setMainFlatNoChanged(false);
			} else {
				return false ;
			}
			
		}
		
		if(event!=null) {
			boolean additionalAddressValid = user.getContactAddress()==null || (user.getContactAddress()!=null && !checkHouseNo(null)  && !checkCity(null) &&  !checkPostal(null) ) ;
			boolean mainAddressValid = !checkMainHouseNo(null) &&  !checkMainCity(null)  && !checkMainPostal(null) ;
			
			boolean personalDataValid = !checkFirstName(null) && !checkLastName(null) && !checkMobile(null) ;
		
			allowSave = additionalAddressValid && mainAddressValid && personalDataValid ;
			allowComplete = additionalAddressValid && mainAddressValid && personalDataValid ;
		}
		return mainFlatNoChanged ;
	}
	
	@Command
	@NotifyChange({"allowSave", "allowComplete", "mainHouseNoMsg"})
	public boolean checkMainHouseNo(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		
		if(houseNo.getValue()!=null && houseNo.getValue().length()>0 || (event!=null && event.getValue().length()>0 && !event.getValue().equals(user.getMainAddress().getHouseNo()) )) {
			
			
			mainHouseNoMsg = "" ;
			if(event!=null) {
				houseNo.setSclass("correctValue default-s");
				this.setMainHouseNoError(false);
			} else {
				return false ;
			}
			
			
		} else {
			
			
			mainHouseNoMsg = "Numer domu/ posesji nie może być pusty" ;
			if(event!=null) {
				houseNo.setSclass("wrongValue default-s");
				this.setMainHouseNoError(true);
			} else {
				return true ;
			}
			
		}
		
		if(event!=null) {
			boolean additionalAddressValid = user.getContactAddress()==null || (user.getContactAddress()!=null && !checkHouseNo(null)  && !checkCity(null) &&  !checkPostal(null) ) ;
			boolean mainAddressValid = !checkMainHouseNo(null) &&  !checkMainCity(null)  && !checkMainPostal(null) ;
		
			boolean personalDataValid = !checkFirstName(null) && !checkLastName(null) && !checkMobile(null) ;
		
			allowSave = additionalAddressValid && mainAddressValid && personalDataValid ;
			allowComplete = additionalAddressValid && mainAddressValid && personalDataValid ;
		}
		
		if(event!=null)
			resize() ;
		return mainHouseNoError ;
	}
	
	@Command
	@NotifyChange({"allowSave", "allowComplete", "mainCityMsg"})
	public boolean checkMainCity(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		
		if(city.getValue()!=null && city.getValue().length()>0 || (event!=null && event.getValue().length()>0 && !event.getValue().equals(user.getMainAddress().getCity()) )) {
			
			
			mainCityMsg = "" ;
			if(event!=null) {
				city.setSclass("correctValue default-s");
				this.setMainCityError(false);
			} else {
				return false ;
			}
			
			
		} else {
			
			
			mainCityMsg = "Nazwa miejscowości nie może być pusta" ;
			if(event!=null) {
				city.setSclass("wrongValue default-s");
				this.setMainCityError(true);
			} else {
				return true ;
			}
			
		}
		
		if(event!=null) {
			boolean additionalAddressValid = user.getContactAddress()==null || (user.getContactAddress()!=null && !checkHouseNo(null)  && !checkCity(null) &&  !checkPostal(null) ) ;
			boolean mainAddressValid = !checkMainHouseNo(null) &&  !checkMainCity(null)  && !checkMainPostal(null) ;
		
			boolean personalDataValid = !checkFirstName(null) && !checkLastName(null) && !checkMobile(null) ;
		
			allowSave = additionalAddressValid && mainAddressValid && personalDataValid ;
			allowComplete = additionalAddressValid && mainAddressValid && personalDataValid ;
		}
		
		if(event!=null)
			resize() ;
		return mainCityError ;
	}
	
	@Command
	@NotifyChange({"allowSave", "allowComplete", "mainPostalMsg"})
	public boolean checkMainPostal(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		
		if(postal.getValue()!=null && postal.getValue().length()>0 && postal.getValue().matches("[0-9]{2}-{1}[0-9]{3}") || (event!=null && event.getValue().length()>0 && event.getValue().matches("[0-9]{2}-{1}[0-9]{3}") && !event.getValue().equals(user.getMainAddress().getPostal()) )) {
			
			
			mainPostalMsg = "" ;
			if(event!=null) {
				postal.setSclass("correctValue default-s");
				this.setMainPostalError(false);
			} else {
				return false ;
			}
			
			
		} else {
			
			
			mainPostalMsg = "Kod pocztowy nie został poprawnie podany" ;
			if(event!=null) {
				postal.setSclass("wrongValue default-s");
				this.setMainPostalError(true);
			} else {
				return true ;
			}
			
		}
		if(event!=null) {
			boolean additionalAddressValid = user.getContactAddress()==null || (user.getContactAddress()!=null && !checkHouseNo(null)  && !checkCity(null) &&  !checkPostal(null) ) ;
			boolean mainAddressValid = !checkMainHouseNo(null) &&  !checkMainCity(null)  && !checkMainPostal(null) ;
		
			boolean personalDataValid = !checkFirstName(null) && !checkLastName(null) && !checkMobile(null) ;
		
			allowSave = additionalAddressValid && mainAddressValid && personalDataValid ;
			allowComplete = additionalAddressValid && mainAddressValid && personalDataValid ;
		}
		
		if(event!=null)
			resize() ;
		return mainPostalError ;
	}
	
	
	
	
	@Command
	@NotifyChange({"allowSave", "allowComplete", "houseNoMsg"})
	public boolean checkHouseNo(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		
		if(addHouseNo.getValue()!=null && addHouseNo.getValue().length()>0) {
			
			
			houseNoMsg = "" ;
			if(event!=null) {
				addHouseNo.setSclass("correctValue default-s");
				this.setHouseNoError(false);
			} else {
				return false ;
			}
			
			
		} else {
			
			
			houseNoMsg = "Numer domu/ posesji nie może być pusty" ;
			if(event!=null) {
				addHouseNo.setSclass("wrongValue default-s");
				this.setHouseNoError(true);
			} else {
				return true ;
			}
			
		}
		
		if(event!=null) {
			boolean additionalAddressValid = user.getContactAddress()==null || (user.getContactAddress()!=null && !checkHouseNo(null)  && !checkCity(null) &&  !checkPostal(null) ) ;
			boolean mainAddressValid = !checkMainHouseNo(null) &&  !checkMainCity(null)  && !checkMainPostal(null) ;
		
			boolean personalDataValid = !checkFirstName(null) && !checkLastName(null) && !checkMobile(null) ;
		
			allowSave = additionalAddressValid && mainAddressValid && personalDataValid ;
			allowComplete = additionalAddressValid && mainAddressValid && personalDataValid ;
		}
		
		if(event!=null)
			resize() ;
		return houseNoError ;
	}
	
	@Command
	@NotifyChange({"allowSave", "allowComplete", "cityMsg"})
	public boolean checkCity(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		
		if(addCity.getValue()!=null && addCity.getValue().length()>0) {
			
			
			cityMsg = "" ;
			if(event!=null) {
				addCity.setSclass("correctValue default-s");
				this.setCityError(false);
			} else {
				return false ;
			}
			
			
		} else {
			
			
			cityMsg = "Nazwa miejscowości nie może być pusta" ;
			if(event!=null) {
				addCity.setSclass("wrongValue default-s");
				this.setCityError(true);
			} else {
				return true ;
			}
			
		}
		
		if(event!=null) {
			boolean additionalAddressValid = user.getContactAddress()==null || (user.getContactAddress()!=null && !checkHouseNo(null)  && !checkCity(null) &&  !checkPostal(null) ) ;
			boolean mainAddressValid = !checkMainHouseNo(null) &&  !checkMainCity(null)  && !checkMainPostal(null) ;
		
			boolean personalDataValid = !checkFirstName(null) && !checkLastName(null) && !checkMobile(null) ;
		
			allowSave = additionalAddressValid && mainAddressValid && personalDataValid ;
			allowComplete = additionalAddressValid && mainAddressValid && personalDataValid ;
		}
		
		if(event!=null)
			resize() ;
		return cityError ;
	}
	
	@Command
	@NotifyChange({"allowSave", "allowComplete", "postalMsg"})
	public boolean checkPostal(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		
		if(addPostal.getValue()!=null && addPostal.getValue().length()>0 && addPostal.getValue().matches("[0-9]{2}-{1}[0-9]{3}")) {
			
			
			postalMsg = "" ;
			if(event!=null) {
				addPostal.setSclass("correctValue default-s");
				this.setPostalError(false);
			} else {
				return false ;
			}
			
			
		} else {
			
			
			postalMsg = "Kod pocztowy nie został poprawnie podany" ;
			if(event!=null) {
				addPostal.setSclass("wrongValue default-s");
				this.setPostalError(true);
			} else {
				return true ;
			}
			
		}
		
		if(event!=null) {
			boolean additionalAddressValid = user.getContactAddress()==null || (user.getContactAddress()!=null && !checkHouseNo(null)  && !checkCity(null) &&  !checkPostal(null) ) ;
			boolean mainAddressValid = !checkMainHouseNo(null) &&  !checkMainCity(null)  && !checkMainPostal(null) ;
		
			boolean personalDataValid = !checkFirstName(null) && !checkLastName(null) && !checkMobile(null) ;
		
			allowSave = additionalAddressValid && mainAddressValid && personalDataValid ;
			allowComplete = additionalAddressValid && mainAddressValid && personalDataValid ;
		}
		
		if(event!=null)
			resize() ;
		return postalError ;
	}
	
	@Command
	@NotifyChange({"allowComplete", "allowSave"})
	public boolean checkStreet(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		
		if(!addStreet.getValue().equals(user.getMainAddress().getStreet())) {
			if(event!=null) {
				this.setStreetChanged(true);
			} else {
				return true ;
			}
			
		} else {
			if(event!=null) {
				this.setStreetChanged(false);
			} else {
				return false ;
			}
			
		}
		
		if(event!=null) {
			boolean additionalAddressValid = user.getContactAddress()==null || (user.getContactAddress()!=null && !checkHouseNo(null)  && !checkCity(null) &&  !checkPostal(null) ) ;
			boolean mainAddressValid = !checkMainHouseNo(null) &&  !checkMainCity(null)  && !checkMainPostal(null) ;
		
			boolean personalDataValid = !checkFirstName(null) && !checkLastName(null) && !checkMobile(null) ;
		
			allowSave = additionalAddressValid && mainAddressValid && personalDataValid ;
			allowComplete = additionalAddressValid && mainAddressValid && personalDataValid ;
		}
		
		return streetChanged ;
	}
	
	@Command
	@NotifyChange({"allowComplete", "allowSave"})
	public boolean checkFlatNo(@ContextParam(ContextType.TRIGGER_EVENT) InputEvent event) {
		
		if(!addFlatNo.getValue().equals(user.getMainAddress().getFlatNo())) {
			if(event!=null) {
				this.setFlatNoChanged(true);
			} else {
				return true ;
			}
			
		} else {
			if(event!=null){
				this.setFlatNoChanged(false);
			} else {
				return false ;
			}
			
		}
		
		if(event!=null) {
			boolean additionalAddressValid = user.getContactAddress()==null || (user.getContactAddress()!=null && !checkHouseNo(null)  && !checkCity(null) &&  !checkPostal(null) ) ;
			boolean mainAddressValid = !checkMainHouseNo(null) &&  !checkMainCity(null)  && !checkMainPostal(null) ;
		
			boolean personalDataValid = !checkFirstName(null) && !checkLastName(null) && !checkMobile(null) ;
		
			allowSave = additionalAddressValid && mainAddressValid && personalDataValid ;
			allowComplete = additionalAddressValid && mainAddressValid && personalDataValid ;
		}
		
		return flatNoChanged ;
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
			if(mainHouseNoError){
				count++ ;
			}
			if(houseNoError){
				count++ ;
			}
			if(mainCityError){
				count++ ;
			}
			if(cityError) {
				count++ ;
			}
			if(mainPostalError){
				count++ ;
			}
			if(postalError){
				count++ ;
			}
			
			if(count==0){
				error.setSclass("error");
			} else
			{
				error.setSclass("error one");
			}
		 
	 }
	
	
	@Command
	public void changePassword() {
		try {
			user.setPassword(Utilities.hash(Utilities.HASH_METHOD_MD5, user.getPassword()));
			ServicesImpl.getUserService().update(user);
			Messagebox.show("Hasło zostało zmienione") ;
		} catch(NoSuchAlgorithmException ex){
			Messagebox.show("Nie udało się zmienić hasła") ;
		}
	}
	
	@Command
	public void updateUser(){
		
		ServicesImpl.getUserService().update(user);
		Messagebox.show("Dane zostały zapisane") ;
	}
	
	
	@Command
	public void completeUser() {
		
		Boolean userVerificationModeAuto = Boolean.valueOf(ServicesImpl.getConfigService().findByKey("user.verification.mode.auto").getValue()) ;
		String userVerificationModeType = ServicesImpl.getConfigService().findByKey("user.verification.mode.type").getValue() ;
		
		//rodzaj weryfikacji użytkownika
		
		if(userVerificationModeAuto) {
			
			user.setCompleted(true);
			user.setStatus(User.STATUS_VERIFIED);
			
		} else {
			user.setStatus(User.STATUS_PENDING_VERIFICATION);
			user.setCompleted(true);
		}
		
		ServicesImpl.getUserService().update(user);
		
		if(userVerificationModeAuto) {
			Messagebox.show("Dane zostały zapisane") ;
		} else {
			Messagebox.show("Dane zostały zapisane. Będziesz mógł w pełni korzystać z serwisu po weryfikacji swojego konta") ;
		}
		
		userCompleteInfo.setSclass("error");
		prepareVerificationInfo();
	}
	
	@Command
	public void selectMenuitem(@BindingParam("item") Menuitem item) {
		item.setSclass("userAccount visited");
	}


	
	




	private void prepareAvatar(){
		
	try {
		
		if(user.getAvatarFileName()!=null) {
			File afile = new File(Utilities.getUserDataFolder(user).concat("\\avatar\\").concat(user.getAvatarFileName())) ;
			AImage img = new AImage(afile) ;
			
			AImage newImg = scaleImage(225, 225, img, afile) ;
			avatar.setContent(newImg);
			
		} 
		/*else {
			File afile = new File("/images/noavatar.jpg") ;
			AImage img = new AImage(afile) ;
			avatar.setContent(img);
		}*/
		
		
		
	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	
	}
	
	private void prepareAvatars(){
		try {
		String userDir = Utilities.getUserDataFolder(user) ;
		String avatarDir = userDir.concat("\\avatar") ;
		File avatarDirFolder = new File(avatarDir) ;
		File[] files = avatarDirFolder.listFiles() ;
		if(files.length>1){
			avatars.getChildren().clear();
			for(final File file:files){
					Image img = new Image() ;
					
					AImage im = new AImage(file) ;
					img.setContent(im);
					img = Utilities.scaleImage(img, 100,100) ;
					//AImage img = scaleImage(100, 100, new AImage(file), file) ;
					Fisheye fe = new Fisheye() ;
					//fe.setId(file.getAbsolutePath());
					
					fe.addEventListener("onClick", new EventListener<Event>(){

						@Override
						public void onEvent(Event arg0) throws Exception {
							AImage fullImg = scaleImage(225, 225, new AImage(file), file) ;
							avatar.setContent(fullImg);
							user.setAvatarFileName(file.getName());
							ServicesImpl.getUserService().update(user);
							
						}
						
					}) ;
					
					fe.setImageContent(img.getContent()); //.setImageContent(img);
					avatars.appendChild(fe);
					avatars.setVisible(true) ;
			}
			
		} else {
			avatars.setVisible(false) ;
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Command
	public void deleteAvatar() {
		
		String userDir = Utilities.getUserDataFolder(user) ;
		final String avatarDir = userDir.concat("\\avatar") ;
		final File avatarDirFolder = new File(avatarDir) ;
		
		
		
		final File[] files = avatarDirFolder.listFiles() ;
		if(files.length>=1){
		
			Messagebox.show("Czy napewno chcesz usunąć awatar?", "Usuń awatar", Messagebox.YES|Messagebox.NO, null, new EventListener<Event>(){

				@Override
				public void onEvent(Event arg0) throws Exception {
					
					if(arg0.getName().equalsIgnoreCase("onYes")){
						String toDelete = avatarDir.concat("\\").concat(((AImage) avatar.getContent()).getName()) ;
						if(files.length>1) {
							
							int fNo = 0 ;
							boolean foundNext = false ;
							for(File f:files) {
								if(f.getName().equals(avatar.getContent().getName())) {
									foundNext = true ;
									break ;
								}
								fNo++ ;
							}
							
							
							if (foundNext) {
								
								if (fNo<files.length-1) {
									
									AImage nextAvatar = new AImage(files[fNo+1]) ;
									avatar.setContent(nextAvatar);
									
								} else if(fNo-1>=0) {
									AImage nextAvatar = new AImage(files[fNo-1]) ;
									avatar.setContent(nextAvatar);
								} else {
									AImage nextAvatar = null ;
									avatar.setContent(nextAvatar);
								}
								
								
							} else {
								AImage nextAvatar = null ;
								avatar.setContent(nextAvatar);
							}
							
							
							
							
						} else {
							//avatar.redraw(new StringWriter());
							AImage nextAvatar = null ;
							avatar.setContent(nextAvatar);
						}
						
						
						File toDeleteFile = new File(toDelete) ;
						toDeleteFile.delete() ;
						prepareAvatars();
					}
					
				}
				
			}) ;
			
			
		}
		
	}
	
	private AImage scaleImage(int x, int y, AImage img, File afile) {
		
		String userDir = Utilities.getUserDataFolder(user) ;
		String avatarDir = userDir.concat("\\avatar") ;
		File avatarDirFolder = new File(avatarDir) ;
		
		AImage res = null;
		BigDecimal realWidth = new BigDecimal(img.getWidth()) ;
		BigDecimal realHeight = new BigDecimal(img.getHeight()) ;
		
		BigDecimal aspectRatio = realWidth.divide(realHeight, 2, RoundingMode.FLOOR) ;
		
		BufferedImage out = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB) ;
		
		if(aspectRatio.compareTo(new BigDecimal(1))>0) {
			
			// landscape
			try {
				BigDecimal widthPercent = new BigDecimal(x).divide(realWidth, 2, RoundingMode.FLOOR) ;
				
			
			if (widthPercent.compareTo(new BigDecimal(1))>=0) {
				BigDecimal newWidth = realWidth ;
				BigDecimal newHeight = realHeight ;
				BigDecimal heightOffset = new BigDecimal(y).subtract(realHeight) ;
				heightOffset = heightOffset.divide(new BigDecimal(2), 2, RoundingMode.FLOOR) ;
				
				BigDecimal widthOffset = new BigDecimal(x).subtract(realWidth) ;
				widthOffset = widthOffset.divide(new BigDecimal(2), 2, RoundingMode.FLOOR) ;
				
				Graphics2D g = (Graphics2D) out.getGraphics() ;
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(1, 1, x-1, y-1);
				
				java.awt.Image fil = ImageIO.read(img.getStreamData()) ;
				
				g.drawImage(fil, widthOffset.intValue(), heightOffset.intValue(), newWidth.intValue(), newHeight.intValue(), null);
				
				File newFile = new File(avatarDir.concat("\\").concat(afile.getName())) ;
				
				OutputStream os = new ByteArrayOutputStream() ;
				
				ImageIO.write(out, img.getFormat(), os) ;
				
				res = new AImage(newFile) ;
				
				//newFile.delete();
				
			} else {
				
				BigDecimal newWidth = realWidth.multiply(widthPercent) ;
				BigDecimal newHeight = realHeight.multiply(widthPercent) ;
				
				BigDecimal heightOffset = new BigDecimal(y).subtract(newHeight) ;
				heightOffset = heightOffset.divide(new BigDecimal(2), 2, RoundingMode.FLOOR) ;
				BigDecimal widthOffset = new BigDecimal(1) ;
				
				Graphics2D g = (Graphics2D) out.getGraphics() ;
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(1, 1, x-1, y-1);
				
				java.awt.Image fil = ImageIO.read(img.getStreamData()) ;
				
				g.drawImage(fil, widthOffset.intValue(), heightOffset.intValue(), newWidth.intValue(), newHeight.intValue(), null);
				
				File newFile = new File(avatarDir.concat("\\").concat(afile.getName())) ;
				
				ImageIO.write(out, img.getFormat(), newFile) ;
				
				res = new AImage(newFile) ;
				//newFile.delete();
				
				
				
				//g.
				
			}
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} else
		if (aspectRatio.compareTo(new BigDecimal(1))==0){
			
			//square
			try{
			BigDecimal heightPercent = new BigDecimal(y).divide(realHeight, 2, RoundingMode.FLOOR) ;
			
			if (heightPercent.compareTo(new BigDecimal(1))>=0) {
				
				BigDecimal newWidth= realWidth ;
				BigDecimal newHeight = realHeight ;
				
				BigDecimal heightOffset = new BigDecimal(y).subtract(realHeight) ;
				heightOffset = heightOffset.divide(new BigDecimal(2)) ;
				
				BigDecimal widthOffset = new BigDecimal(x).subtract(realWidth) ;
				widthOffset = widthOffset.divide(new BigDecimal(2), 2, RoundingMode.FLOOR) ;
				
				Graphics2D g = (Graphics2D) out.getGraphics() ;
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(1, 1, x-1, y-1);
				
				java.awt.Image fil = ImageIO.read(img.getStreamData()) ;
				
				g.drawImage(fil, widthOffset.intValue(), heightOffset.intValue(), newWidth.intValue(), newHeight.intValue(), null);
				
				File newFile = new File(avatarDir.concat("\\").concat(afile.getName())) ;
				
				ImageIO.write(out, img.getFormat(), newFile) ;
				
				res = new AImage(newFile) ;
				//newFile.delete();
				
				
			} else {
				
				
				BigDecimal newWidth = realWidth.multiply(heightPercent) ;
				BigDecimal newHeight = realHeight.multiply(heightPercent) ;
				
				BigDecimal widthOffset = new BigDecimal(y).subtract(newWidth) ;
				widthOffset = widthOffset.divide(new BigDecimal(2), 2, RoundingMode.FLOOR) ;
				BigDecimal heightOffset = new BigDecimal(y).subtract(newHeight) ;
				heightOffset = heightOffset.divide(new BigDecimal(2), 2, RoundingMode.FLOOR) ;
				
				Graphics2D g = (Graphics2D) out.getGraphics() ;
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(1, 1, x-1, y-1);
				
				java.awt.Image fil = ImageIO.read(img.getStreamData()) ;
				
				g.drawImage(fil, widthOffset.intValue(), heightOffset.intValue(), newWidth.intValue(), newHeight.intValue(), null);
				
				File newFile = new File(avatarDir.concat("\\").concat(afile.getName())) ;
				
				ImageIO.write(out, img.getFormat(), newFile) ;
				
				res = new AImage(newFile) ;
				//newFile.delete();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		} else {
			
			//portrait
			
			try {
			BigDecimal heightPercent = new BigDecimal(y).divide(realHeight, 2, RoundingMode.FLOOR) ;
			
			if (heightPercent.compareTo(new BigDecimal(1))>=0) {
				
				BigDecimal newHeight = realHeight ;
				BigDecimal newWidth = realWidth ;
				
				BigDecimal heightOffset = new BigDecimal(y).subtract(realHeight) ;
				heightOffset = heightOffset.divide(new BigDecimal(2), 2, RoundingMode.FLOOR) ;
				
				BigDecimal widthOffset = new BigDecimal(x).subtract(realWidth) ;
				widthOffset = widthOffset.divide(new BigDecimal(2), 2, RoundingMode.FLOOR) ;
				
				Graphics2D g = (Graphics2D) out.getGraphics() ;
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(1, 1, x-1, y-1);
				
				java.awt.Image fil = ImageIO.read(img.getStreamData()) ;
				
				g.drawImage(fil, widthOffset.intValue(), heightOffset.intValue(), newWidth.intValue(), newHeight.intValue(), null);
				
				File newFile = new File(avatarDir.concat("\\").concat(afile.getName())) ;
				
				ImageIO.write(out, img.getFormat(), newFile) ;
				
				res = new AImage(newFile) ;
				//newFile.delete();
				
				
			} else {
				
				BigDecimal newWidth = realWidth.multiply(heightPercent) ;
				BigDecimal newHeight = realHeight.multiply(heightPercent) ;
				
				BigDecimal widthOffset = new BigDecimal(y).subtract(newWidth) ;
				widthOffset = widthOffset.divide(new BigDecimal(2), 2, RoundingMode.FLOOR) ;
				BigDecimal heightOffset = new BigDecimal(1) ;
				
				Graphics2D g = (Graphics2D) out.getGraphics() ;
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(1, 1, x-1, y-1);
				
				java.awt.Image fil = ImageIO.read(img.getStreamData()) ;
				
				g.drawImage(fil, widthOffset.intValue(), heightOffset.intValue(), newWidth.intValue(), newHeight.intValue(), null);
				
				File newFile = new File(avatarDir.concat("\\").concat(afile.getName())) ;
				
				ImageIO.write(out, img.getFormat(), newFile) ;
				
				res = new AImage(newFile) ;
				//newFile.delete();
				
			}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return res ;
		
	}
	
	
	//-----------------------------------------------
	
	
	
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
	
	

	public Textbox getStreet() {
		return street;
	}

	public void setStreet(Textbox street) {
		this.street = street;
	}

	public Textbox getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(Textbox houseNo) {
		this.houseNo = houseNo;
	}

	public Textbox getPostal() {
		return postal;
	}

	public void setPostal(Textbox postal) {
		this.postal = postal;
	}

	public Textbox getCity() {
		return city;
	}

	public void setCity(Textbox city) {
		this.city = city;
	}

	public Textbox getAddStreet() {
		return addStreet;
	}

	public void setAddStreet(Textbox addStreet) {
		this.addStreet = addStreet;
	}

	public Textbox getAddHouseNo() {
		return addHouseNo;
	}

	public void setAddHouseNo(Textbox addHouseNo) {
		this.addHouseNo = addHouseNo;
	}

	public Textbox getAddPostal() {
		return addPostal;
	}

	public void setAddPostal(Textbox addPostal) {
		this.addPostal = addPostal;
	}

	public Textbox getAddCity() {
		return addCity;
	}

	public void setAddCity(Textbox addCity) {
		this.addCity = addCity;
	}

	public boolean isAllowComplete() {
		return allowComplete;
	}

	public void setAllowComplete(boolean allowComplete) {
		this.allowComplete = allowComplete;
	}

	public boolean isAllowSave() {
		return allowSave;
	}

	public void setAllowSave(boolean allowSave) {
		this.allowSave = allowSave;
	}

	public String getMainHouseNoMsg() {
		return mainHouseNoMsg;
	}

	public void setMainHouseNoMsg(String mainHouseNoMsg) {
		this.mainHouseNoMsg = mainHouseNoMsg;
	}

	public boolean isMainHouseNoError() {
		return mainHouseNoError;
	}

	@NotifyChange({"mainHouseNoError"})
	public void setMainHouseNoError(boolean mainHouseNoError) {
		this.mainHouseNoError = mainHouseNoError;
	}

	public String getHouseNoMsg() {
		return houseNoMsg;
	}

	public void setHouseNoMsg(String houseNoMsg) {
		this.houseNoMsg = houseNoMsg;
	}

	public boolean isHouseNoError() {
		return houseNoError;
	}

	@NotifyChange({"houseNoError"})
	public void setHouseNoError(boolean houseNoError) {
		this.houseNoError = houseNoError;
	}

	public String getMainCityMsg() {
		return mainCityMsg;
	}

	public void setMainCityMsg(String mainCityMsg) {
		this.mainCityMsg = mainCityMsg;
	}

	public boolean isMainCityError() {
		return mainCityError;
	}

	@NotifyChange({"mainCityError"})
	public void setMainCityError(boolean mainCityError) {
		this.mainCityError = mainCityError;
	}

	public String getCityMsg() {
		return cityMsg;
	}

	public void setCityMsg(String cityMsg) {
		this.cityMsg = cityMsg;
	}

	public boolean isCityError() {
		return cityError;
	}

	@NotifyChange({"cityError"})
	public void setCityError(boolean cityError) {
		this.cityError = cityError;
	}

	public String getMainPostalMsg() {
		return mainPostalMsg;
	}

	public void setMainPostalMsg(String mainPostalMsg) {
		this.mainPostalMsg = mainPostalMsg;
	}

	public boolean isMainPostalError() {
		return mainPostalError;
	}

	@NotifyChange({"mainPostalError"})
	public void setMainPostalError(boolean mainPostalError) {
		this.mainPostalError = mainPostalError;
	}

	public String getPostalMsg() {
		return postalMsg;
	}

	public void setPostalMsg(String postalMsg) {
		this.postalMsg = postalMsg;
	}

	public boolean isPostalError() {
		return postalError;
	}

	@NotifyChange({"postalError"})
	public void setPostalError(boolean postalError) {
		this.postalError = postalError;
	}

	

	public Div getUserPendingVerificationInfo() {
		return userPendingVerificationInfo;
	}

	public void setUserPendingVerificationInfo(Div userPendingVerificationInfo) {
		this.userPendingVerificationInfo = userPendingVerificationInfo;
	}


	public String getVerificationType() {
		return verificationType;
	}


	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}



	public Div getUserCompleteInfo() {
		return userCompleteInfo;
	}

	public void setUserCompleteInfo(Div userCompleteInfo) {
		this.userCompleteInfo = userCompleteInfo;
	}


	
	
	//--------------------------------------------
	
	
	public Image getAvatar() {
		return avatar;
	}

	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}

	public Textbox getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(Textbox flatNo) {
		this.flatNo = flatNo;
	}

	public Textbox getAddFlatNo() {
		return addFlatNo;
	}

	public void setAddFlatNo(Textbox addFlatNo) {
		this.addFlatNo = addFlatNo;
	}

	public org.zkoss.image.AImage getImage() {
		return image;
	}


	public void setImage(org.zkoss.image.AImage image) {
		this.image = image;
	}


	public boolean isMainStreetChanged() {
		return mainStreetChanged;
	}

	@NotifyChange({"mainStreetChanged"})
	public void setMainStreetChanged(boolean mainStreetChanged) {
		this.mainStreetChanged = mainStreetChanged;
	}

	public boolean isStreetChanged() {
		return streetChanged;
	}

	@NotifyChange({"streetChanged"})
	public void setStreetChanged(boolean streetChanged) {
		this.streetChanged = streetChanged;
	}

	public boolean isMainFlatNoChanged() {
		return mainFlatNoChanged;
	}

	@NotifyChange({"mainFlatNoChanged"})
	public void setMainFlatNoChanged(boolean mainFlatNoChanged) {
		this.mainFlatNoChanged = mainFlatNoChanged;
	}

	public boolean isFlatNoChanged() {
		return flatNoChanged;
	}

	@NotifyChange({"flatNoChanged"})
	public void setFlatNoChanged(boolean flatNoChanged) {
		this.flatNoChanged = flatNoChanged;
	}
	
	public Textbox getFirstName() {
		return firstName;
	}

	public void setFirstName(Textbox firstName) {
		this.firstName = firstName;
	}

	public Textbox getLastName() {
		return lastName;
	}

	public void setLastName(Textbox lastName) {
		this.lastName = lastName;
	}

	public Textbox getMobile() {
		return mobile;
	}

	public void setMobile(Textbox mobile) {
		this.mobile = mobile;
	}

	public boolean isFirstNameError() {
		return firstNameError;
	}

	@NotifyChange({"firstNameError"})
	public void setFirstNameError(boolean firstNameError) {
		this.firstNameError = firstNameError;
	}

	public boolean isLastNameError() {
		return lastNameError;
	}

	@NotifyChange({"lastNameError"})
	public void setLastNameError(boolean lastNameError) {
		this.lastNameError = lastNameError;
	}

	public boolean isMobileError() {
		return mobileError;
	}

	@NotifyChange({"mobileError"})
	public void setMobileError(boolean mobileError) {
		this.mobileError = mobileError;
	}

	
	public Listbox getCountry() {
		return country;
	}

	public void setCountry(Listbox country) {
		this.country = country;
	}

	public Listbox getAddCountry() {
		return addCountry;
	}

	public void setAddCountry(Listbox addCountry) {
		this.addCountry = addCountry;
	}
	
	
	//--------------------------------------------

	





	





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
			
			String country = loc.getDisplayCountry(currentLocale) ;
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
