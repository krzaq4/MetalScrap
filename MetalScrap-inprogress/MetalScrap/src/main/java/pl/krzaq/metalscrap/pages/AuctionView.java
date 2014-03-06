package pl.krzaq.metalscrap.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.SelectorParam;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import pl.krzaq.metalscrap.converter.AuctionTimeLeftConverter;
import pl.krzaq.metalscrap.model.AttachementFile;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.UserOffer;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class AuctionView extends HomePage{

	private Auction auction ;
	private String timeLeft ;
	private Page page ;
	private Label label ;
	
	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
		
		super.doInit(page, arg1);
		
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest() ;
		
		if (request.getParameter("id")!=null) {
			
			Long id = Long.valueOf( (String) request.getParameter("id")) ;
			Auction auction = ServicesImpl.getAuctionService().findById(id) ;
			
			List<AttachementFile> attachements = ServicesImpl.getAttachementFileService().findByAuction(auction) ;
			
			List<Image> images = new ArrayList<Image>() ;
			
			for (AttachementFile attachement:attachements) {
				
				File file = new File(attachement.getPath()) ;
				AImage image = new AImage(file) ;
				Image img = new Image() ;
				img.setContent(image);
				images.add(img) ;
			}
			
			
			List<UserOffer> offers = ServicesImpl.getUserOfferService().findByAuction(auction) ;
			Collections.sort(offers);
			
			if (offers!=null && offers.size()>0) {
				
				page.setAttribute("currentOffer", offers.get(offers.size()-1)) ;
				Collections.sort(offers, Collections.reverseOrder()) ;
				page.setAttribute("offersHistory", offers) ;
				page.setAttribute("offersQty", offers.size()) ;
			} else {
				page.setAttribute("offersQty", 0) ;
				page.setAttribute("currentOffer", null) ;
			}
			
			
			DateTime now = new DateTime(new Date()) ;
			DateTime end = new DateTime(auction.getEndDate()) ;
			Duration d = new Duration(now, end);
			
			StringBuffer toGo = new StringBuffer() ;
			Long daysToGo = d.getStandardDays() ;
			toGo.append(daysToGo+" dni, ") ;
			
			Period p = d.toPeriod().minusDays(daysToGo.intValue()) ;
			toGo.append(p.getHours()+" godz., "+p.getMinutes()+" min., "+p.getSeconds()+" sek.") ; 
			
			
			
			Category selectedCategory = auction.getCategory() ;
			
			page.setAttribute("selectedCategory", selectedCategory) ;
			page.setAttribute("toGo", toGo.toString()) ;
			page.setAttribute("offers", offers) ;
			
			page.setAttribute("auction", auction) ;
			this.auction = auction ;
			page.setAttribute("images", images) ;
			this.page = page ;
			
			
		} else {
			
			Messagebox.show("Niew³aœciwe wywo³anie strony.", "B³¹d", Messagebox.OK, "", new EventListener<Event>(){

				@Override
				public void onEvent(Event arg0) throws Exception {
					
					Executions.getCurrent().sendRedirect("/home");
					
				}
				
			}) ;
		}
		
	}

	@Override
	public void doAfterCompose(Page page, Component[] arg1) throws Exception {
		
		super.doAfterCompose(page, arg1);
		this.label = (Label)this.page.getFellow("label") ;
		
		
	}

	@Override
	public boolean doCatch(Throwable arg0) throws Exception {
		// TODO Auto-generated method stub
		return false ;
	}

	@Override
	public void doFinally() throws Exception {
		// TODO Auto-generated method stub
		super.doFinally();
	}

	
	
	@Init
	public void doit() {
		
		System.out.println("do") ;
		
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest() ;
		AuctionTimeLeftConverter atc = new AuctionTimeLeftConverter() ;
		if (request.getParameter("id")!=null) {
			
			Long id = Long.valueOf( (String) request.getParameter("id")) ;
			Auction auction = ServicesImpl.getAuctionService().findById(id) ;
			this.setAuction(auction);
			this.setTimeLeft((String) atc.coerceToUi(auction, null));
		}
	}
	
	
	
		@Command("update")
		@NotifyChange(value={"timeLeft"})
	  public void update() {
			AuctionTimeLeftConverter atc = new AuctionTimeLeftConverter() ;
			this.setTimeLeft( (String) atc.coerceToUi(auction, null) );
			
			 
		}

		
	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	public String getTimeLeft() {
		
		return timeLeft;
	}

	
	
	
	public void setTimeLeft(String timeLeft) {	
		this.timeLeft = timeLeft;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}        
	
	
}
