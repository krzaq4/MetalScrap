package pl.krzaq.metalscrap.bind;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;

import pl.krzaq.metalscrap.model.Address;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class UserAccountBind {

	@Wire("#userMenu")
	private Menubar menu ;
	
	private Page page ;
	
	
	private User user ;
	
	private Address main ;
	
	private Address additional ;
	
	private boolean useAdditional ;
	
	private List<Auction> observed ;
	
	private List<Auction> won ;
	
	private List<Auction> lost ;
	
	private List<Auction> active ;
	
	private List<Auction> owned ;
	
	
	
	@Init
	public void init() {
		// pobranie page'a
		this.page = menu.getPage() ;
		
		// pobranie aktualnego użytkownika
		this.user = (User) page.getAttribute("user") ;
		
		
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
		
		
	}
	
	
	@Command
	public void selectMenuitem(@BindingParam("item") Menuitem item) {
		item.setSclass("userAccount visited");
	}
	
	
}
