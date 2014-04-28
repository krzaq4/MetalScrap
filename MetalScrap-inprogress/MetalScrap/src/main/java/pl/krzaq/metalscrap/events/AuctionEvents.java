package pl.krzaq.metalscrap.events;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.util.resource.Labels;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import pl.krzaq.metalscrap.dao.AddressDAO;
import pl.krzaq.metalscrap.dao.AuctionDAO;
import pl.krzaq.metalscrap.dao.CompanyDAO;
import pl.krzaq.metalscrap.dao.DeliveryTypeDAO;
import pl.krzaq.metalscrap.dao.PaymentMethodDAO;
import pl.krzaq.metalscrap.dao.RoleDAO;
import pl.krzaq.metalscrap.dao.UserDAO;
import pl.krzaq.metalscrap.model.AttachementFile;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.Commodity;
import pl.krzaq.metalscrap.model.CommodityType;
import pl.krzaq.metalscrap.model.Company;
import pl.krzaq.metalscrap.model.DeliveryType;
import pl.krzaq.metalscrap.model.PaymentMethod;
import pl.krzaq.metalscrap.model.Property;
import pl.krzaq.metalscrap.model.PropertyAttribute;
import pl.krzaq.metalscrap.model.PropertyAttributeValue;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.model.UserOffer;
import pl.krzaq.metalscrap.utils.Constants;
import pl.krzaq.metalscrap.utils.Utilities;

public class AuctionEvents {

	@Autowired
	private AuctionDAO auctionDAO ;
	
	@Autowired
	private CompanyDAO companyDAO ;
	
	@Autowired
	private PaymentMethodDAO paymentMethodDAO ;
	
	@Autowired
	private DeliveryTypeDAO deliveryTypeDAO ;
	
	@Autowired
	private AddressDAO addressDAO ;
	
	@Autowired
	private UserDAO userDAO ;
	
	@Autowired
	private RoleDAO roleDAO ;
	
	
	@Value("${upload.path}")
	private String uploadPath ;
	
	//-----------------------------------------------------------------------
	
@SuppressWarnings("unchecked")
public void saveNewAuction(Auction auction, Page p) {
		
		Component wrongValueComponent = null;
		String wrongValueMessage = "" ;
		
		String pref = p.getId() ;
	
		/*if (auction.getId()!=null) {
			auction = Utilities.getServices().getAuctionService().findWithCollection(auction.getId()) ;
		}*/
		
		User currentUser = userDAO.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName()) ;
		currentUser = Utilities.getServices().getUserService().getUserById(currentUser.getId()) ;
		Company currentCompany = currentUser.getCompany() ;
		
		if (auction.getId()==null && Utilities.getServices().getAuctionService().findByNumber(auction.getNumber())!=null) {
			wrongValueComponent = p.getFellow("auction_number") ;
			wrongValueMessage = "Aukcja o podanym numerze istnieje już w systemie" ;
			throw new WrongValueException(wrongValueComponent, wrongValueMessage) ;
			
		} else 
		if(((Listbox)p.getFellow("auction_category")).getSelectedCount()==0 ){
			wrongValueComponent = p.getFellow("auction_category") ;
			wrongValueMessage = "Wybierz kategorię" ;
			throw new WrongValueException(wrongValueComponent, wrongValueMessage) ;
		} else 
		if (validateForm(p)) {
		
			int selectedPhotoIndex = -1 ;
			
		if (((Listbox)p.getFellow("photos")).getSelectedItem()==null && ((Listbox)p.getFellow("photos")).getItems().size()>0) {
			
			throw new WrongValueException((Listbox)p.getFellow("photos"), Labels.getLabel("auction.auctionselectmainphoto")) ;
		} else {
			
			selectedPhotoIndex = ((Listbox)p.getFellow("photos")).getSelectedIndex() ; 
			
		}
				
				
		//usuwanie atrybutów sesji z zapamietanymi danymi aukcji
		
		HttpSession ses = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
		
		Enumeration<String> keys = ses.getAttributeNames();
		
		while(keys.hasMoreElements()){
			
			String attr = (String) keys.nextElement() ;
			if (attr.substring(0, pref.length()).equalsIgnoreCase(pref)) {
				
				ses.removeAttribute(attr);
				
			}
			
		}
		
		List<AttachementFile> files = (ArrayList<AttachementFile>) ses.getAttribute("files") ;
		if(files!=null && files.size()>0) {
		int i = 1 ;
		for (AttachementFile af:files) {
			if (selectedPhotoIndex>0 && selectedPhotoIndex==i){
				af.setMain(true);
			}
			
			af.setAuction(auction);	
			i++ ;
		}
		auction.getFiles().clear(); 
		auction.getFiles().addAll(files) ;
		
				
		ses.removeAttribute("files");
		} else if (files!= null && files.size()==0) {
			
			for (AttachementFile at:auction.getFiles()) {
				at.setAuction(null);
			}
			auctionDAO.update(auction);
			auction.getFiles().clear(); 
			
		}
		
		
		auction.setStatus(Utilities.getServices().getAuctionService().findStatusByCode(AuctionStatus.STATUS_NEW));
		
		auction.setOwnerUser(currentUser);
		currentUser.getAuctions().add(auction) ;
		Utilities.getServices().getUserService().update(currentUser);
		// zapis aukcji
		
		//Utilities.validate(p.getFellow("auction_category_attributes"), true)  ;
		
		List<Property> props = Utilities.populateProperties(p.getFellow("auction_category_attributes"), Utilities.getServices().getCategoryService().findById(auction.getCategory().getId())) ;
		
		auction.setProperties(props);
		
		
		if (auction.getId()!=null) {
			auctionDAO.update(auction);
		} else {
			auctionDAO.save(auction);
		}
		
				
		
		
		// okienko potwierdzające i przekierowanie do edycji aukcji
		
		final String id = String.valueOf(auction.getId()) ;
		Messagebox.show("Aukcja utworzona", "Informacja", Messagebox.OK, "", new EventListener<Event>(){

			@Override
			public void onEvent(Event arg0) throws Exception {
				
				Executions.getCurrent().sendRedirect("/secured/auctions/new.zul?id="+id) ;
				
			}
			
			
		}) ;
		
		}
		
	}
	


public void addToObserved(Auction auction, Button btn) {
	Page p = btn.getPage();
	//auction.getObeservers().add(e)
	User currentUser = 	(User) p.getAttribute("currentUser") ; //Utilities.getServices().getUserService().getLoggedinUser();
	auction.getObeservers().add(currentUser) ;
	
	List<Auction> observed = Utilities.getServices().getAuctionService().findByObserver(currentUser) ;
	String message = "Dodano aukcję "+auction.getName()+" do obserwowanych" ;
	
	
	//Utilities.getServices().getAuctionService().update(auction);
	
	if (observed==null || observed.size()==0) {
		observed = new ArrayList<Auction>() ;
		
	} 
	
	if (!observed.contains(auction)) {
		observed.add(auction) ;
		currentUser.getObserved().add(auction);
		
		//Utilities.getServices().getUserService().update(currentUser);
		Utilities.getServices().getAuctionService().update(auction);
		
		
	} else {
		message="Aukcja znajduje się już na liście aukcji obserwowanych" ; 
	}
	
	
	
	Messagebox.show(message) ;
	
}

public void onClickRefreshAuctionForm(Auction auction, Button but, AnnotateDataBinder binder) {
	
	Page page = but.getPage() ;
	
	
	
	Label currentPrice = (Label) page.getFellow("current_price") ;
	Label tillEnd = (Label) page.getFellow("till_end") ;
	Button placeBid = (Button) page.getFellow("place_bid") ;
	//Row status_row = (Row) page.getFellow("auction_status_row") ;
	Grid offers_history = (Grid) page.getFellow("offers_history") ;
	Label offers_qty = (Label) page.getFellow("offers_qty") ;
	
	List<UserOffer> offers = Utilities.getServices().getUserOfferService().findByAuction(auction) ;
	Collections.sort(offers);
	if (offers!=null && offers.size()>0) {
		
		page.setAttribute("currentOffer", offers.get(offers.size()-1)) ;
		Collections.sort(offers,Collections.reverseOrder());
		page.setAttribute("offersHistory", offers) ;
		page.setAttribute("offersQty", offers.size()) ;
	}
	
	
	//binder.loadComponent(status_row);
	binder.loadComponent(placeBid);
	binder.loadComponent(tillEnd);
	binder.loadComponent(currentPrice);
	binder.loadComponent(offers_history);
	binder.loadComponent(offers_qty);
	
	
	
}


public void onClickPlaceBid(Decimalbox dbox, Auction auction, AnnotateDataBinder binder) {
	
	Page page = dbox.getPage() ;
	UserOffer currentOffer = (UserOffer)dbox.getPage().getAttribute("currentOffer") ;
	BigDecimal price = dbox.getValue() ;
	Timestamp current = new Timestamp(new Date().getTime()) ;
	if(current.compareTo(auction.getEndDate())>=0){
		
		Messagebox.show("Aukcja zakończyła się") ;
		
		Label currentPrice = (Label) page.getFellow("current_price") ;
		Label tillEnd = (Label) page.getFellow("till_end") ;
		Button placeBid = (Button) page.getFellow("place_bid") ;
		Grid offers_history = (Grid) page.getFellow("offers_history") ;
		Label offers_qty = (Label) page.getFellow("offers_qty") ;
		
		
		binder.loadComponent(placeBid);
		binder.loadComponent(tillEnd);
		binder.loadComponent(currentPrice);
		binder.loadComponent(offers_history);
		binder.loadComponent(offers_qty);
		
		
	} else
	if (price!=null && (currentOffer==null || currentOffer.getPrice()<price.doubleValue() )) {
	
		UserOffer newOffer = new UserOffer() ;
		newOffer.setAuction(auction);
		newOffer.setDateIssued(new Date());
		newOffer.setPrice(price.doubleValue());
		newOffer.setUser(Utilities.getServices().getUserService().getLoggedinUser());
		auction.getUserOffers().add(newOffer) ;
		
		//Utilities.getServices().getUserOfferService().save(newOffer);
		List<UserOffer> offers = auction.getUserOffers() ; //Utilities.getServices().getUserOfferService().findByAuction(auction) ;
		Collections.sort(offers, Collections.reverseOrder());
		auction.setBestUserOffer(newOffer);
		
		dbox.getPage().setAttribute("offersHistory", offers) ;
	
		dbox.getPage().setAttribute("currentOffer", newOffer) ;
		
		Utilities.getServices().getAuctionService().update(auction);
		binder.loadComponent(dbox.getPage().getFellow("current_price"));
		binder.loadComponent(dbox.getPage().getFellow("offers_history"));
	}  else {
		
		throw new WrongValueException(dbox, "Cena musi być wyższa niż aktualna oferta") ;
		
	}
}


private boolean validateForm(Page p) {

	Collection<Component> children = p.getFellows() ;
	
	Iterator<Component> it =  children.iterator() ;
	boolean valid = true ;
	
	while(it.hasNext()) {
		
		valid = valid && validateChildren(it.next().getChildren()) ;
		
	}
	
	return valid ;
	
}

private boolean validateChildren(List<Component> parents) throws WrongValueException{
	
	boolean valid = true ;
	
	if (parents!=null || parents.size()>0){
	
		for (Component parent:parents) {
		
		if (parent instanceof Textbox) {
			
			if (!((Textbox) parent).isValid()){
				valid = false ;
				((Textbox) parent).getText() ;
			}
			
			
			
		} else
		if (parent instanceof Combobox){
			
			if (!((Combobox) parent).isValid()) {
				valid = false ;
				((Combobox) parent).getSelectedItem() ;
			}
			
			
		} else
		if (parent instanceof Datebox) {
			
			
			Date value = ((Datebox) parent).getValue() ;
			if (!((Datebox) parent).isValid()) {
				valid = false ;
				((Datebox) parent).getValue() ;
			}
			
		} else {
			
			validateChildren(parent.getChildren()) ;
			
		}
		
		}
	}
	
	return valid ;
	
}



public void registerCompanyUser(Company company, User user) {
	try {
	//user.setCompany(company);
	user.getRoles().add(roleDAO.findRoleByName(Constants.ROLE_USER)) ;
	//company.getUsers().add(user) ;
	
	
		
	
	user.setPassword(Utilities.md5(user.getPassword()));
	userDAO.saveUser(user);
	Map<String, Object> model = new HashMap<String, Object>() ;
	model.put("title", "Potwierdzenie rejestracji") ;
	model.put("registrationconfirmationlabel", "Rejestracja na platformie licytuj.to!") ;
	model.put("welcometoplatformlabel", "Witamy na platformie!") ;
	model.put("loginlabel", "Login:") ;
	model.put("login", user.getLogin()) ;
	model.put("name", user.getFirstName()+" "+user.getLastName()) ;
	model.put("namelabel", "Imię i nazwisko:") ;
	model.put("confirmationlinkcaption", "Potwierdzenie rejestracji") ;
	model.put("confirmationlink", "http://google.pl") ;
	model.put("confirmationlinklabel", "Link do potwierdzenia") ;
	model.put("platformnamelabel", "Licytuj.to - platforma aukcyjna") ;
	model.put("platforminfo", "Witamy na platformie") ;
	model.put("platforminfo2", "Dziękujemy za rejestrację") ;
	Utilities.getServices().getMailService().sendUserMail("mail_registration_confirmation.ftl", model, "Rejestracja", user);
	//companyDAO.saveCompany(company);
	
	
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch(ConstraintViolationException cve){
		
		
	}
	
}


public void redirectToAuctionEdit(Long id){
	
	Executions.getCurrent().sendRedirect("/secured/auctions/new.zul?id="+String.valueOf(id));
}

public void redirectToAuctionView(Auction auction) {
	
	Executions.getCurrent().sendRedirect("/auction.zul?id="+String.valueOf(auction.getId()));
}


public void populateEqual(){
	try {
	List<Property> list = Utilities.getServices().getPropertyService().findAll() ;
	for(Property p:list){
		p.setEqualIdentifier(Utilities.hash(Utilities.HASH_METHOD_MD5, p.getName().concat(p.getDescription())));
		Utilities.getServices().getPropertyService().update(p);
	}
	}catch(NoSuchAlgorithmException nsa) {}
}

public void onSelectAuctionCategory(Listitem item, AnnotateDataBinder binder) {
	
	Page page = item.getPage() ;
	Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
	Category selectedCategory = item.getValue() ;
	
	if (selectedCategory.getId()==null) {  // powr�t do kategorii nadrz�dnej
		
		if (selectedCategory.getParent()!=null) {
			
			List<Category> subCategories = Utilities.getServices().getCategoryService().findSubCategoriesByLang(selectedCategory.getParent(), locale.getLanguage()) ;
			Category previous = new Category(Labels.getLabel("auction.auctioncategory.back"), "Powrót do nadrzędnej kategorii", selectedCategory.getParent().getParent()) ;
			subCategories.add(0, previous) ;
			
			ListModelList lml = (ListModelList) ((Listbox)page.getFellow("auction_category")).getModel() ;
			lml.clear();
			lml.addAll(subCategories) ;
			
			binder.loadComponent(((Listbox)page.getFellow("auction_category")));
			
		} else {
			
			List<Category> subCategories = Utilities.getServices().getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
			
			
			
			ListModelList lml = (ListModelList) ((Listbox)page.getFellow("auction_category")).getModel() ;
			lml.clear();
			lml.addAll(subCategories) ;
			
			binder.loadComponent(((Listbox)page.getFellow("auction_category")));
			
		}
	} 
	else
	if (selectedCategory.getChildren()!=null && selectedCategory.getChildren().size()>0) {
		
		List<Category> subCategories = Utilities.getServices().getCategoryService().findSubCategoriesByLang(selectedCategory, locale.getLanguage());
		Category previous = new Category(Labels.getLabel("auction.auctioncategory.back"), "Powrót do nadrzędnej kategorii", selectedCategory.getParent()) ;
		subCategories.add(0, previous) ;
		
		ListModelList lml = (ListModelList) ((Listbox)page.getFellow("auction_category")).getModel() ;
		lml.clear();
		lml.addAll(subCategories) ;
		
		binder.loadComponent(((Listbox)page.getFellow("auction_category")));
	} 
	
	Groupbox vbox = (Groupbox) page.getFellow("auction_category_attributes") ;
	vbox.getChildren().clear(); 
	
	
	List<Property> props = selectedCategory.getProperties() ;
	
	for (Property prop:props){
		
		String name = prop.getName() ;
		String equality = prop.getEqualIdentifier();
		Label lab = new Label() ;
		lab.setSclass("normalLabel");
		lab.setValue(name);
		lab.setId(equality);
		Vbox propBox = new Vbox() ;
		propBox.appendChild(lab) ;
		if(prop.getAttributes()!=null && prop.getAttributes().size()>0) {
			
			List<PropertyAttribute> attrs = prop.getAttributes() ;
			for (PropertyAttribute attr:attrs){
				
				
				Hbox hbox = new Hbox() ;
				Label lab2 = new Label(attr.getName()) ;
				hbox.appendChild(lab2) ;
				if (attr.getType().equals(PropertyAttribute.TYPE_TEXT)) {
					Textbox txt = new Textbox() ;
					txt.setConstraint("no empty");
					hbox.appendChild(txt) ;
				}
				else
				if (attr.getType().equals(PropertyAttribute.TYPE_DATE)) {
					Datebox dat = new Datebox() ;
					
					hbox.appendChild(dat) ;
				}
				else
				if (attr.getType().equals(PropertyAttribute.TYPE_DECIMAL)) {
					Decimalbox dec = new Decimalbox() ;
					hbox.appendChild(dec) ;
				}
				else
				if (attr.getType().equals(PropertyAttribute.TYPE_SELECT)) {
					Listbox list = new Listbox() ;
					list.setMold("select");
					
					if(attr.getValues()!=null && attr.getValues().size()>0) {
					
						List<PropertyAttributeValue> vals = attr.getValues() ;
						
						for (PropertyAttributeValue val:vals) {
							
							Listitem li = new Listitem() ;
							li.setValue(val);
							li.setLabel(val.getValue());
							list.appendChild(li) ;
							
						}
						
					}
					
					hbox.appendChild(list) ;
				}
				else
				if (attr.getType().equals(PropertyAttribute.TYPE_MULTISELECT)) {
					
					Vbox vb = new Vbox() ;
					
					if(attr.getValues()!=null && attr.getValues().size()>0) {
						
						List<PropertyAttributeValue> vals = attr.getValues() ;
						
						for (PropertyAttributeValue val:vals) {
							
							Checkbox cb = new Checkbox() ;
							cb.setLabel(val.getValue());
							vb.appendChild(cb) ;
							
						}
						
					}
					hbox.appendChild(vb) ;
					
				}
				
				propBox.appendChild(hbox) ;
			}
			
			
		}
		vbox.appendChild(propBox) ;
	}
	
}


public void onSelectAuctions(Listbox lbx, AnnotateDataBinder binder) {
	
	Page page = lbx.getPage() ;
	List<Auction> selectedAuctions = new ArrayList<Auction>() ;
	
	
	
	for (Listitem li:lbx.getSelectedItems()){
		
		selectedAuctions.add((Auction)li.getValue()) ;
		
	}
	
	page.setAttribute("selectedAuctions", selectedAuctions) ;
		
	binder.loadComponent(page.getFellow("del_auction"));
	binder.loadComponent(page.getFellow("end_auction"));
	binder.loadComponent(page.getFellow("change_status"));
	
}


public void deleteSelectedAuctions(final Listbox lbx, final AnnotateDataBinder binder) {
	
	final Page page = lbx.getPage() ;
	
	
	if(page.getAttribute("selectedAuctions")!=null) {
		
		final List<Auction> selectedAuctions = (ArrayList<Auction>) page.getAttribute("selectedAuctions") ;
		
	
	
	int selectedCount = lbx.getSelectedCount() ;
	
	if (selectedCount>1){
		
		Messagebox.show("Zaznaczyłeś/aś "+selectedCount+" aukcji. Czy napewno chcesz usunąć wybrane pozycje?", "Uwaga!", Messagebox.CANCEL|Messagebox.YES, "", new EventListener<Event>(){

			@Override
			public void onEvent(Event arg0) throws Exception {
				if(arg0.getName().equalsIgnoreCase("onYes")) {
					
				for (Auction toDel:selectedAuctions){
					
					/*for (User u:toDel.getObeservers()){
						u.getObserved().remove(toDel) ;
					}*/
					
					Utilities.getServices().getAuctionService().delete(toDel);
				}
					
				ListModelList lml = ( (ListModelList) lbx.getListModel()); 
				lml.removeAll(selectedAuctions) ;
				lbx.setModel(lml);
				
				page.setAttribute("selectedAuctions",null) ;
				
				binder.loadComponent(lbx);
				binder.loadComponent(page.getFellow("del_auction"));
				binder.loadComponent(page.getFellow("end_auction"));
				binder.loadComponent(page.getFellow("change_status"));
				
				}
			}
			
			
			
		}) ;
		
	} else {
		
		for (Auction toDel:selectedAuctions){
			Utilities.getServices().getAuctionService().delete(Utilities.getServices().getAuctionService().findById(toDel.getId()));
		}
			
		ListModelList lml = ( (ListModelList) lbx.getListModel()); 
		lml.removeAll(selectedAuctions) ;
		lbx.setModel(lml);
		
		page.setAttribute("selectedAuctions",null) ;
		
		binder.loadComponent(lbx);
		binder.loadComponent(page.getFellow("del_auction"));
		binder.loadComponent(page.getFellow("end_auction"));
		binder.loadComponent(page.getFellow("change_status"));
		
	}
	
	}
}


public void changeSelectedStatus(Window win, AnnotateDataBinder binder) {
	
	Comboitem selectedItem = ((Combobox) win.getFellow("status")).getSelectedItem() ;
	AuctionStatus selectedStatus = null ;
	if(selectedItem!=null){
		selectedStatus = (((Combobox) win.getFellow("status")).getSelectedItem() !=null) ? (AuctionStatus)((Combobox) win.getFellow("status")).getSelectedItem().getValue() : null ;
	} 
	
	if (selectedStatus!=null) {
	
	List<Auction> selectedAuctions = (List<Auction>) win.getPage().getAttribute("selectedAuctions") ;
	
	for (Auction a: selectedAuctions) {
		
		a.setStatus(selectedStatus);
		Utilities.getServices().getAuctionService().update(a);
		
	}
	
	filterAuctionList((Listbox)win.getPage().getFellow("auctionList"), binder);
	
	binder.loadComponent((Listbox)win.getPage().getFellow("auctionList"));
	win.detach();
	
	} else {
		
		throw new WrongValueException(win.getFellow("status"), "Wybierz status") ;
		
	}
}

public void changeSelectedAuctionsStatus(Listbox lbx, AnnotateDataBinder binder) {
	
	final Page page = lbx.getPage() ;
	
	Map<String, Object> args = new HashMap<String, Object>() ;
	
	
	
	if(page.getAttribute("selectedAuctions")!=null) {
		
		final List<Auction> selectedAuctions = (ArrayList<Auction>) page.getAttribute("selectedAuctions") ;
		
		int selectedCount = selectedAuctions.size() ;
		
		//changeStatusWindow.setAttribute("auctionCount", selectedCount) ;
		
		StringBuffer sb = new StringBuffer() ;
		sb.append("<div style='padding:5px 5px 5px 5px'><table class='TFtable' border=0 style='padding: 5px 5px 5px 5px'><tr><th>Nr aukcji</th><th>Nazwa aukcji</th></tr>") ;
		
		for (Auction a:selectedAuctions) {
			sb.append("<tr><td>"+a.getNumber()+"</td><td>"+a.getName()+"</td></tr>") ;			
		}
		
		sb.append("</table></div>") ;
		
		args.put("auctionCount", selectedCount) ;
		args.put("auctionDetails", sb.toString()) ;
		ListModelList lml = new ListModelList(Utilities.getServices().getAuctionService().findAllStatuses()) ;
		args.put("statuses", lml) ;
		
		Window changeStatusWindow = (Window) Executions.createComponents("/secured/auctions/windows/change_status.zul", null, args) ;
		((Combobox)changeStatusWindow.getFellow("status")).setItemRenderer(new ComboitemRenderer(){

			@Override
			public void render(Comboitem item, Object arg1, int arg2)
					throws Exception {
				
				item.setLabel( ( (AuctionStatus) arg1).getName());
				item.setValue(arg1);
				
			}
			
			
		});
		
		//changeStatusWindow.setAttribute("selectedStatus", new AuctionStatus()) ;
		changeStatusWindow.setPage(page);
		
		//changeStatusWindow.setAttribute("auctionDetails", sb.toString()) ;
		
		changeStatusWindow.doModal();
		binder.loadComponent(changeStatusWindow.getFellow("selected"));
	
	} 
	
	
}

public void endSelectedAuctions(final Listbox lbx, final AnnotateDataBinder binder) {

	final Page page = lbx.getPage() ;
	
	
	if(page.getAttribute("selectedAuctions")!=null) {
		
		final List<Auction> selectedAuctions = (ArrayList<Auction>) page.getAttribute("selectedAuctions") ;
		
	
	
	int selectedCount = lbx.getSelectedCount() ;
	
	if (selectedCount>1){
		
		Messagebox.show("Zaznaczyłeś/aś "+selectedCount+" aukcji. Czy napewno chcesz zakończyć wybrane aukcje?", "Uwaga!", Messagebox.CANCEL|Messagebox.YES, "", new EventListener<Event>(){

			@Override
			public void onEvent(Event arg0) throws Exception {
				
				if(arg0.getName().equalsIgnoreCase("onYes")) {
					
				
				
				for (Auction a:selectedAuctions){
					
					
					
					a.setStatus(Utilities.getServices().getAuctionService().findStatusByCode(AuctionStatus.STATUS_FINISHED));
					Utilities.getServices().getAuctionService().update(a);
					
					
				}	
				
				filterAuctionList(lbx, binder) ; 
				
				page.setAttribute("selectedAuctions",null) ;
				
				binder.loadComponent(lbx);
				binder.loadComponent(page.getFellow("del_auction"));
				binder.loadComponent(page.getFellow("end_auction"));
				binder.loadComponent(page.getFellow("change_status"));
				
				}
			}
			
			
			
		}) ;
		
	} else {
		

		for (Auction a:selectedAuctions){
			
			
			
			a.setStatus(Utilities.getServices().getAuctionService().findStatusByCode(AuctionStatus.STATUS_FINISHED));
			Utilities.getServices().getAuctionService().update(a);
			
			
		}	
		
		filterAuctionList(lbx, binder) ; 
		
		page.setAttribute("selectedAuctions",null) ;
		
		binder.loadComponent(lbx);
		binder.loadComponent(page.getFellow("del_auction"));
		binder.loadComponent(page.getFellow("end_auction"));
		binder.loadComponent(page.getFellow("change_status"));
		
	}
	
	}
}

public void filterAuctionList(Listbox lbx, AnnotateDataBinder binder) {
	
	Page page = lbx.getPage() ;
	String searchPhrase = ((Textbox)page.getFellow("phraseSearch")).getValue() ;
	PaymentMethod searchPayment = ((Combobox) page.getFellow("paymentSearch")).getSelectedItem().getValue() ;
	AuctionStatus searchStatus = ((Combobox) page.getFellow("statusSearch")).getSelectedItem().getValue() ;
	DeliveryType searchDelivery = ((Combobox) page.getFellow("deliverySearch")).getSelectedItem().getValue() ;
	Date searchStartDate = ((Datebox) page.getFellow("startDateSearch")).getValue() ;
	Date searchEndDate = ((Datebox) page.getFellow("endDateSearch")).getValue() ;
	
	List<Auction> result = Utilities.getServices().getAuctionService().filter(searchPhrase, searchStatus, searchPayment, searchDelivery, searchStartDate, searchEndDate) ;
	
	ListModelList lml = ((ListModelList) lbx.getListModel()) ;
	lml.clear();
	lml.addAll(result) ;
	lbx.setModel(lml);
	binder.loadComponent(lbx);
	
}


public void addPosition(Commodity commodity, Component c) {
	
	Page p = c.getPage() ;
	
	Auction a = (Auction) p.getAttribute("auction") ;
	Commodity nc = new Commodity() ;
	nc.setName(commodity.getName());
	nc.setCommodityType(commodity.getCommodityType());
	nc.setAuction(a);
	nc.setQuantity(commodity.getQuantity());
	
	
	
	a.getCommodities().add(nc) ;
	
	p.setAttribute("auction", a) ;
	p.setAttribute("commodity", new Commodity()) ;
	refreshPositionsList(p) ;
	
	
	
	
}


public void updatePosition(Commodity commodity, Component c) {
	
	Page p = c.getPage() ;
	
	Auction a = (Auction) p.getAttribute("auction") ;
	Commodity nc = new Commodity() ;
	nc.setName(commodity.getName());
	nc.setCommodityType(commodity.getCommodityType());
	nc.setAuction(a);
	nc.setQuantity(commodity.getQuantity());
	
	
	
	//a.getCommodities().s.add(nc) ;
	
	a.getCommodities().set(a.getCommodities().indexOf(commodity), nc) ;
	
	p.setAttribute("auction", a) ;
	p.setAttribute("commodity", new Commodity()) ;
	refreshPositionsList(p) ;
	
	
	
	
}



public void delPosition(Listbox lbx) {
	
	
	final Page p = lbx.getPage();
	
	
	// Usuwanie więcej niz jedną pozycję
	if(lbx.getSelectedCount()>1) {
		
		final List<Commodity> selected = new ArrayList<Commodity>() ;
		
		Iterator<Listitem> it = lbx.getSelectedItems().iterator() ;
		
		while(it.hasNext()) {
			
			selected.add((Commodity)it.next().getValue()) ;
			
		}
		
		int selectedCount = lbx.getSelectedCount() ;
		
		Messagebox.show("Czy napewno usunąć wybrane "+selectedCount+" pozycji/e?", "Usuń pozycje", Messagebox.YES|Messagebox.CANCEL, "", new EventListener<Event>(){

			@Override
			public void onEvent(Event arg0) throws Exception {
				
				if (arg0.getName().equalsIgnoreCase("onyes")) {
				Auction a = (Auction) p.getAttribute("auction") ;
				List<Commodity> coms = a.getCommodities() ;
				coms.removeAll(selected) ;
				a.setCommodities(coms);
				
				p.setAttribute("auction", a) ;
				
				refreshPositionsList(p) ;
				
				}
			}
			
			
		}) ;
		
	// Usuwanie jednej pozycji
	} else if(lbx.getSelectedCount()>0) {
		
		
		final Commodity com = (Commodity) lbx.getSelectedItem().getValue() ;
		
		
		
		Messagebox.show("Czy napewno usunąć pozycję?", "Usuń pozycję", Messagebox.YES|Messagebox.CANCEL, "", new EventListener<Event>(){

			@Override
			public void onEvent(Event arg0) throws Exception {
				
				System.out.println(arg0.getName()) ;
				Auction a = (Auction) p.getAttribute("auction") ;
				List<Commodity> coms = a.getCommodities() ;
				coms.remove(com) ;
				a.setCommodities(coms);
				
				p.setAttribute("auction", a) ;
				
				refreshPositionsList(p) ;
				
			}
			
			
		}) ;
		
	}
	
		
}


public void editPositionPopup(Popup pop, Listcell cell, AnnotateDataBinder binder) {
	
	Commodity com = ((Listitem) cell.getParent()).getValue() ;
	pop.setAttribute("commodity", com) ;
	binder.loadComponent(pop);
	pop.open(cell.getListbox());
	
	
}


private void refreshPositionsList(Page p) {
	
	AnnotateDataBinder binder = (AnnotateDataBinder) p.getAttribute("binder") ;
	
	binder.loadComponent(p.getFellow("positionsList"));
}




//--------------------------------------------------------------------------------
	
	

	public AuctionDAO getAuctionDAO() {
		return auctionDAO;
	}

	public void setAuctionDAO(AuctionDAO auctionDAO) {
		this.auctionDAO = auctionDAO;
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public PaymentMethodDAO getPaymentMethodDAO() {
		return paymentMethodDAO;
	}

	public void setPaymentMethodDAO(PaymentMethodDAO paymentMethodDAO) {
		this.paymentMethodDAO = paymentMethodDAO;
	}

	public DeliveryTypeDAO getDeliveryTypeDAO() {
		return deliveryTypeDAO;
	}

	public void setDeliveryTypeDAO(DeliveryTypeDAO deliveryTypeDAO) {
		this.deliveryTypeDAO = deliveryTypeDAO;
	}

	public AddressDAO getAddressDAO() {
		return addressDAO;
	}

	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}
	
	
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}


	public RoleDAO getRoleDAO() {
		return roleDAO;
	}


	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	
	
	
}
