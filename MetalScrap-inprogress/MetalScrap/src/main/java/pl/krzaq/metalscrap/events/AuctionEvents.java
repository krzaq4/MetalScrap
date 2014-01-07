package pl.krzaq.metalscrap.events;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Popup;
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
import pl.krzaq.metalscrap.model.Commodity;
import pl.krzaq.metalscrap.model.CommodityType;
import pl.krzaq.metalscrap.model.Company;
import pl.krzaq.metalscrap.model.User;
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
	
	//-----------------------------------------------------------------------
	
@SuppressWarnings("unchecked")
public void saveNewAuction(Auction auction, Page p) {
		
		String pref = p.getId() ;
	
		User currentUser = userDAO.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName()) ;
		Company currentCompany = currentUser.getCompany() ;
		
		
		
		
		
		//usuwanie atrybut�w sesji z zapamietanymi danymi aukcji
		
		HttpSession ses = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
		
		Enumeration<String> keys = ses.getAttributeNames();
		
		while(keys.hasMoreElements()){
			
			String attr = (String) keys.nextElement() ;
			if (attr.substring(0, pref.length()).equalsIgnoreCase(pref)) {
				
				ses.removeAttribute(attr);
				
			}
			
		}
		
		List<Image> files = (ArrayList<Image>) ses.getAttribute("files") ;
		List<AttachementFile> attchs = new ArrayList<AttachementFile>() ;
		int i = 0 ;
		for (Image img:files) {
			
			attchs.add(new AttachementFile(pref+String.valueOf(i), img.getSrc(), auction, false));
			i++ ;
		}
		
		auction.getFiles().addAll(attchs) ;
		ses.removeAttribute("files");
		
		
		// zapis aukcji
		
				auctionDAO.save(auction);
		
		// okienko potwierdzaj�ce i przekierowanie do edycji aukcji
		
		final String id = String.valueOf(auction.getId()) ;
		Messagebox.show("Aukcja utworzona", "Informacja", Messagebox.OK, "", new EventListener<Event>(){

			@Override
			public void onEvent(Event arg0) throws Exception {
				
				Executions.getCurrent().sendRedirect("/secured/auctions/new.zul?id="+id) ;
				
			}
			
			
		}) ;
		
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
	
	
	// Usuwanie wi�cej niz jedn� pozycj�
	if(lbx.getSelectedCount()>1) {
		
		final List<Commodity> selected = new ArrayList<Commodity>() ;
		
		Iterator<Listitem> it = lbx.getSelectedItems().iterator() ;
		
		while(it.hasNext()) {
			
			selected.add((Commodity)it.next().getValue()) ;
			
		}
		
		int selectedCount = lbx.getSelectedCount() ;
		
		Messagebox.show("Czy napewno usun�� wybrane "+selectedCount+" pozycji/e?", "Usu� pozycje", Messagebox.YES|Messagebox.CANCEL, "", new EventListener<Event>(){

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
		
		
		
		Messagebox.show("Czy napewno usun�� pozycj�?", "Usu� pozycj�", Messagebox.YES|Messagebox.CANCEL, "", new EventListener<Event>(){

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
