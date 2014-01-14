package pl.krzaq.metalscrap.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;

import pl.krzaq.metalscrap.model.AttachementFile;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.Commodity;
import pl.krzaq.metalscrap.model.CommodityType;
import pl.krzaq.metalscrap.service.Services;
import pl.krzaq.metalscrap.service.impl.AuctionServiceImpl;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class AuctionNew extends HomePage{

	
	
	
	@Override
	public void doAfterCompose(Page arg0, Component[] arg1) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(arg0, arg1);
		
		Auction currentAuction = (Auction) arg0.getAttribute("auction") ;
		
		if (currentAuction!=null){
			
			Category cat = currentAuction.getCategory() ;
			
			List<Category> model = new ArrayList<Category>() ;
			
			if (cat!=null && cat.getParent()!=null){
				Category previous = new Category(" << Powrót", "Powrót do nadrzêdnej kategorii", cat.getParent().getParent()) ;
				model = ServicesImpl.getCategoryService().findSubCategories(cat.getParent()) ;
				model.add(0,previous) ;
				
			} else {
				model = ServicesImpl.getCategoryService().findRootCategories() ;
			}
			
			int i = 0 ;
			/*if (cat!=null && cat.getParent()!=null) {
				i = 1 ;
			} */
			
			int selectedCatIndex = -1 ;
			if (cat!=null) {
			for (Category c:model) {
				if (cat.getName().equalsIgnoreCase(c.getName()) && cat.getDescription().equalsIgnoreCase(c.getDescription()) ){
					selectedCatIndex = i ;
				}
				i++ ;
			}
			}
			arg0.setAttribute("auctionCategories", model) ;
			arg0.setAttribute("selectedCategoryIndex", selectedCatIndex) ;
			
		}
		
		HttpSession ses = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
		
		// zdjêcia
		
		final Div overflow = new Div() ;
		overflow.setSclass("galleryOverflow");
		overflow.setId("galleryOverflow");
		overflow.setVisible(false) ;
		Image overflowImage = new Image() ;
		overflowImage.setId("overflowImage");
		overflow.appendChild(overflowImage) ;
		
		Button closeOverflow = new Button("X") ;
		closeOverflow.setSclass("overflowClose");
		closeOverflow.addEventListener("onClick", new EventListener<Event>(){

			@Override
			public void onEvent(Event event) throws Exception {
				
				overflow.setVisible(false) ;
				
			}
			
			
			
		}) ;
		
		overflow.appendChild(closeOverflow) ;
		
		
		overflow.setPage(arg0);
		
		
		if(ses.getAttribute("files")!=null) {
			
			List<AttachementFile> files = (ArrayList<AttachementFile>) ses.getAttribute("files") ;
			Listbox grid = (Listbox) arg0.getFellow("photos") ;
			AnnotateDataBinder binder = (AnnotateDataBinder) arg0.getAttribute("binder") ;
			int i=1 ;
			int selectedPhotoIndex = -1 ;
			for (AttachementFile af:files) {
				if (af.getMain()) {
					selectedPhotoIndex = i ;
				}
				Listitem li = new Listitem() ;
				Listcell lc = new Listcell() ;
				File imgFile = new File(af.getPath()) ;
				AImage aimg = new AImage(imgFile) ;
				Image img = new Image() ;
				img.setContent(aimg);
				final Image orig = (Image) img.clone() ;
				
				img.addEventListener("onClick", new EventListener<Event>(){
					
					@Override
					public void onEvent(Event event) throws Exception {
						
						if(event.getName().equalsIgnoreCase("onClick")) {
							
							Image main = (Image) event.getTarget() ;
							overflow.removeChild(overflow.getFellow("overflowImage"));
							
							orig.setId("overflowImage") ;
							overflow.appendChild(orig) ;
							overflow.setVisible(true) ;
						}
						
					}
					
				}) ;
				
				
				img.setWidth("50%");
				img.setHeight("50%");
				
				lc.appendChild(img) ;
				li.appendChild(lc) ;
				
				grid.getItems().add(li) ;	
				i++ ;
				
			}
			
			arg0.setAttribute("selectedPhotoIndex", selectedPhotoIndex) ;
			
			
		}
		
		
		
		
		
		// parametry aukcji
		
		
	}


	@Override
	public boolean doCatch(Throwable arg0) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void doFinally() throws Exception {
		System.out.println("doFinally") ;
		super.doFinally();
		
	}

	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
	
		
		super.doInit(page, arg1) ;
		
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest() ; 
		
		Auction auction = new Auction() ;
		
		List<Commodity> commodities = new ArrayList<Commodity>(); 
		auction.setCommodities(commodities);
		
		List<AttachementFile> files = new ArrayList<AttachementFile>() ; 
		
		HttpSession ses = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
		
		List<Category> auctionCategories = ServicesImpl.getCategoryService().findRootCategories() ;
		
		// Tryb edycji
		
		if (request.getParameter("id")!=null) {
			
			Long id = Long.valueOf(request.getParameter("id")) ;
			
			
			auction = ServicesImpl.getAuctionService().findWithCollection(id) ;
			
			List<Image> imgs = new ArrayList<Image>() ;
			int i=1 ;
			int selectedPhotoIndex = -1 ;
			for (AttachementFile af:ServicesImpl.getAttachementFileService().findByAuction(auction)) {
				if (af.getMain()) {
					selectedPhotoIndex = i ;
				}
				
				Image im = new Image() ;
				File fi = new File(af.getPath()) ;
				org.zkoss.image.AImage cnt = new org.zkoss.image.AImage(fi) ; 
				im.setContent(cnt);
				
				
				imgs.add(im) ;
				i++ ;
			}
			
			ses.setAttribute("files", ServicesImpl.getAttachementFileService().findByAuction(auction));
			
			if (auction.getCategory()!=null) {
				
				page.setAttribute("selectedCategory", auction.getCategory()) ;
				
			}
			
			
				page.setAttribute("selectedPhotoIndex", selectedPhotoIndex) ;
			
			
		} 
		
		
		
		
		
		page.setAttribute("auctionCategories", auctionCategories) ;
		page.setAttribute("auction", auction) ;
		page.setAttribute("commodity", new Commodity() ) ;
		page.setAttribute("paymentMethods", ServicesImpl.getPaymentMethodService().findAll()) ;
		page.setAttribute("deliveryTypes", ServicesImpl.getDeliveryTypeService().findAll()) ;
		
		
		System.out.println("Page init") ;
		
		/*this.getPageData(page);
		this.binder = new AnnotateDataBinder(page) ;
		binder.loadAll();*/
	}






	

	
	
	
	
	
}
