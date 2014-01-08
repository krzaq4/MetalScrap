package pl.krzaq.metalscrap.events;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
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
import pl.krzaq.metalscrap.model.Commodity;
import pl.krzaq.metalscrap.model.CommodityType;
import pl.krzaq.metalscrap.model.Company;
import pl.krzaq.metalscrap.model.DeliveryType;
import pl.krzaq.metalscrap.model.PaymentMethod;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;
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
		
		String pref = p.getId() ;
	
		User currentUser = userDAO.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName()) ;
		Company currentCompany = currentUser.getCompany() ;
		if (ServicesImpl.getAuctionService().findByNumber(auction.getNumber())!=null) {
			
			throw new WrongValueException(p.getFellow("auction_number"), "Aukcja o podanym numerze istnieje ju¿ w systemie") ;
			
		} else {
			
		
		
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
		
		int i = 0 ;
		for (AttachementFile af:files) {
			
			af.setAuction(auction);	
		}
		
		auction.getFiles().addAll(files) ;
		
		auction.setStatus(ServicesImpl.getAuctionService().findStatusByCode(AuctionStatus.STATUS_NEW));
		
		// zapis aukcji
		
				auctionDAO.save(auction);
				
		ses.removeAttribute("files");
		
		// okienko potwierdzaj¹ce i przekierowanie do edycji aukcji
		
		final String id = String.valueOf(auction.getId()) ;
		Messagebox.show("Aukcja utworzona", "Informacja", Messagebox.OK, "", new EventListener<Event>(){

			@Override
			public void onEvent(Event arg0) throws Exception {
				
				Executions.getCurrent().sendRedirect("/secured/auctions/new.zul?id="+id) ;
				
			}
			
			
		}) ;
		
		}
		
	}
	

public void registerCompanyUser(Company company, User user) {
	try {
	user.setCompany(company);
	user.getRoles().add(roleDAO.findRoleByName(Constants.ROLE_USER)) ;
	company.getUsers().add(user) ;
	
	
		
	
	user.setPassword(Utilities.md5(user.getPassword()));
	
	companyDAO.saveCompany(company);
	
	
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
}


public void redirectToAuctionEdit(Long id){
	
	Executions.getCurrent().sendRedirect("/secured/auctions/new.zul?id="+String.valueOf(id));
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
		
		Messagebox.show("Zaznaczy³aœ/eœ "+selectedCount+" aukcji. Czy napewno chcesz usun¹æ wybrane pozycje?", "Uwaga!", Messagebox.YES|Messagebox.CANCEL, "", new EventListener<Event>(){

			@Override
			public void onEvent(Event arg0) throws Exception {
				if(arg0.getName().equalsIgnoreCase("onOK")) {
					
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
		
	}
	
	}
}


public void changeSelectedAuctionsStatus(Listbox lbx, AnnotateDataBinder binder) {
	
	final Page page = lbx.getPage() ;
	
	
	if(page.getAttribute("selectedAuctions")!=null) {
		
		final List<Auction> selectedAuctions = (ArrayList<Auction>) page.getAttribute("selectedAuctions") ;
	
	}
	
	
}

public void endSelectedAuctions(final Listbox lbx, final AnnotateDataBinder binder) {

	final Page page = lbx.getPage() ;
	
	
	if(page.getAttribute("selectedAuctions")!=null) {
		
		final List<Auction> selectedAuctions = (ArrayList<Auction>) page.getAttribute("selectedAuctions") ;
		
	
	
	int selectedCount = lbx.getSelectedCount() ;
	
	if (selectedCount>1){
		
		Messagebox.show("Zaznaczy³aœ/eœ "+selectedCount+" aukcji. Czy napewno chcesz zakoñczyæ wybrane aukcje?", "Uwaga!", Messagebox.YES|Messagebox.CANCEL, "", new EventListener<Event>(){

			@Override
			public void onEvent(Event arg0) throws Exception {
				
				if(arg0.getName().equalsIgnoreCase("onOK")) {
					
				List<Auction> newModel = new ArrayList<Auction>() ;
				
				for (Auction a:selectedAuctions){
					
					a.setStatus(ServicesImpl.getAuctionService().findStatusByCode(AuctionStatus.STATUS_FINISHED));
					ServicesImpl.getAuctionService().update(a);
					newModel.add(a) ;
					
				}	
				
				ListModelList lml = ( (ListModelList) lbx.getListModel()); 
				lml.clear();
				lml.addAll(newModel) ;
				lbx.setModel(lml);
				
				page.setAttribute("selectedAuctions",null) ;
				
				binder.loadComponent(lbx);
				binder.loadComponent(page.getFellow("del_auction"));
				binder.loadComponent(page.getFellow("end_auction"));
				binder.loadComponent(page.getFellow("change_status"));
				
				}
			}
			
			
			
		}) ;
		
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
	
	List<Auction> result = ServicesImpl.getAuctionService().filter(searchPhrase, searchStatus, searchPayment, searchDelivery, searchStartDate, searchEndDate) ;
	
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
	
	
	// Usuwanie wiêcej niz jedn¹ pozycjê
	if(lbx.getSelectedCount()>1) {
		
		final List<Commodity> selected = new ArrayList<Commodity>() ;
		
		Iterator<Listitem> it = lbx.getSelectedItems().iterator() ;
		
		while(it.hasNext()) {
			
			selected.add((Commodity)it.next().getValue()) ;
			
		}
		
		int selectedCount = lbx.getSelectedCount() ;
		
		Messagebox.show("Czy napewno usun¹æ wybrane "+selectedCount+" pozycji/e?", "Usuñ pozycje", Messagebox.YES|Messagebox.CANCEL, "", new EventListener<Event>(){

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
		
		
		
		Messagebox.show("Czy napewno usun¹æ pozycjê?", "Usuñ pozycjê", Messagebox.YES|Messagebox.CANCEL, "", new EventListener<Event>(){

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
