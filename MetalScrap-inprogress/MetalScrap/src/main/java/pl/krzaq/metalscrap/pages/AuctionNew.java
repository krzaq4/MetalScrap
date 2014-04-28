package pl.krzaq.metalscrap.pages;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import org.zkoss.util.resource.Labels;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import pl.krzaq.metalscrap.model.AttachementFile;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.Commodity;
import pl.krzaq.metalscrap.model.CommodityType;
import pl.krzaq.metalscrap.model.Property;
import pl.krzaq.metalscrap.model.PropertyAttribute;
import pl.krzaq.metalscrap.model.PropertyAttributeValue;
import pl.krzaq.metalscrap.service.Services;
import pl.krzaq.metalscrap.service.impl.AuctionServiceImpl;
import pl.krzaq.metalscrap.utils.Utilities;

public class AuctionNew extends HomePage{

	
	
	
	@Override
	public void doAfterCompose(Page arg0, Component[] arg1) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(arg0, arg1);
		
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
				
				
		
		Auction currentAuction = (Auction) arg0.getAttribute("auction") ;
		
		if (currentAuction!=null){
			
			Category cat = currentAuction.getCategory() ;
			
			List<Category> model = new ArrayList<Category>() ;
			
			if (cat!=null && cat.getParent()!=null){
				Category previous = new Category(Labels.getLabel("auction.auctioncategory.back.$"), Labels.getLabel("auction.auctioncategory.back.description"), cat.getParent().getParent()) ;
				model = Utilities.getServices().getCategoryService().findSubCategoriesByLang(cat.getParent(), locale.getLanguage()) ;
				model.add(0,previous) ;
				
			} else {
				model = Utilities.getServices().getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
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
		
		final HttpSession ses = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
		
		// zdjęcia
		
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
		
		if (ses.getAttribute("existingfiles")!=null && ses.getAttribute("newAuctionPage")!=null && !((Boolean) ses.getAttribute("newAuctionPage"))) {
			List<AttachementFile> files = (ArrayList<AttachementFile>) ses.getAttribute("existingfiles") ;
			final Listbox grid = (Listbox) arg0.getFellow("photos") ;
			final AnnotateDataBinder binder = (AnnotateDataBinder) arg0.getAttribute("binder") ;
			int i=0 ;
			int selectedPhotoIndex = -1 ;
			for (final AttachementFile af:files) {
				if (af.getMain()) {
					selectedPhotoIndex = i ;
				}
				
				
				final Listitem li = new Listitem() ;
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
				
				Listcell lc2 = new Listcell() ;
				
				Button delImage = new Button(Labels.getLabel("common.delete")) ;
				delImage.addEventListener("onClick", new EventListener<Event>(){

					@Override
					public void onEvent(Event event) throws Exception {
						grid.getItems().remove(li) ;
						List<AttachementFile> afiles = (ArrayList<AttachementFile>) ses.getAttribute("files") ;
						if (afiles!=null && afiles.size()>0) {
							afiles.remove(af) ;
							ses.setAttribute("files", afiles);
							//binder.loadComponent(grid);
						}
						
					}
					
				}) ;
				
				lc2.appendChild(delImage) ;
				
				lc.appendChild(img) ;
				li.appendChild(lc) ;
				li.appendChild(lc2) ;
				
				grid.getItems().add(li) ;	
				i++ ;
				
			}
			
			arg0.setAttribute("selectedPhotoIndex", selectedPhotoIndex) ;
			
			
		} else 
		if(ses.getAttribute("files")!=null) {
			
			List<AttachementFile> files = (ArrayList<AttachementFile>) ses.getAttribute("files") ;
			final Listbox grid = (Listbox) arg0.getFellow("photos") ;
			final AnnotateDataBinder binder = (AnnotateDataBinder) arg0.getAttribute("binder") ;
			int i=0 ;
			int selectedPhotoIndex = -1 ;
			for (final AttachementFile af:files) {
				if (af.getMain()) {
					selectedPhotoIndex = i ;
				}
				
				
				final Listitem li = new Listitem() ;
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
				
				Listcell lc2 = new Listcell() ;
				
				Button delImage = new Button(Labels.getLabel("common.delete")) ;
				delImage.addEventListener("onClick", new EventListener<Event>(){

					@Override
					public void onEvent(Event event) throws Exception {
						grid.getItems().remove(li) ;
						List<AttachementFile> afiles = (ArrayList<AttachementFile>) ses.getAttribute("files") ;
						if (afiles!=null && afiles.size()>0) {
							afiles.remove(af) ;
							ses.setAttribute("files", afiles);
							//binder.loadComponent(grid);
						}
						
					}
					
				}) ;
				
				lc2.appendChild(delImage) ;
				
				lc.appendChild(img) ;
				li.appendChild(lc) ;
				li.appendChild(lc2) ;
				
				grid.getItems().add(li) ;	
				i++ ;
				
			}
			
			arg0.setAttribute("selectedPhotoIndex", selectedPhotoIndex) ;
			
			
		} else {
			arg0.setAttribute("selectedPhotoIndex", -1) ;
		}
		
		
		
		
		
		// parametry aukcji
		if(currentAuction.getId()!=null)
			buildProperties(currentAuction, arg0) ;
		
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
		
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest() ; 
		
		Auction auction = new Auction() ;
		
		List<Commodity> commodities = new ArrayList<Commodity>(); 
		auction.setCommodities(commodities);
		
		List<AttachementFile> files = new ArrayList<AttachementFile>() ; 
		
		HttpSession ses = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
		
		List<Category> auctionCategories = Utilities.getServices().getCategoryService().findRootCategoriesByLang(locale.getLanguage()) ;
		
		// Tryb edycji
		
		if (request.getParameter("id")!=null) {
			
			Long id = Long.valueOf(request.getParameter("id")) ;
			
			
			auction = Utilities.getServices().getAuctionService().findWithCollection(id) ;
			
			List<Image> imgs = new ArrayList<Image>() ;
			int i=0 ;
			int selectedPhotoIndex = -1 ;
			for (AttachementFile af:Utilities.getServices().getAttachementFileService().findByAuction(auction)) {
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
			
			ses.setAttribute("existingfiles", Utilities.getServices().getAttachementFileService().findByAuction(auction));
			
			if (auction.getCategory()!=null) {
				
				page.setAttribute("selectedCategory", auction.getCategory()) ;
				
			}
			
			
				page.setAttribute("selectedPhotoIndex", selectedPhotoIndex) ;
			
				ses.setAttribute("newAuctionPage", false);
			
		} else ses.setAttribute("newAuctionPage", true);
		
		
		
		
		
		page.setAttribute("auctionCategories", auctionCategories) ;
		page.setAttribute("auction", auction) ;
		page.setAttribute("commodity", new Commodity() ) ;
		page.setAttribute("paymentMethods", Utilities.getServices().getPaymentMethodService().findAll()) ;
		page.setAttribute("deliveryTypes", Utilities.getServices().getDeliveryTypeService().findAll()) ;
		
		
		System.out.println("Page init") ;
		
		/*this.getPageData(page);
		this.binder = new AnnotateDataBinder(page) ;
		binder.loadAll();*/
	}






	private void buildProperties(Auction auction, Page page) {
		
		Category category = Utilities.getServices().getCategoryService().findById(auction.getCategory().getId()) ;
		Groupbox vbox = (Groupbox) page.getFellow("auction_category_attributes") ;
		vbox.getChildren().clear(); 
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		List<Property> auctionProps = auction.getProperties() ;
		List<Property> props =  category.getProperties();
		
		int propNo = 0 ;
		
		for (Property prop:props){
			
			String name = prop.getName() ;
			Label lab = new Label() ;
			lab.setSclass("normalLabel");
			lab.setValue(name);
			Vbox propBox = new Vbox() ;
			propBox.appendChild(lab) ;
			if(prop.getAttributes()!=null && prop.getAttributes().size()>0) {
				
				List<PropertyAttribute> auctionAttrs = auctionProps.get(propNo).getAttributes() ;
				List<PropertyAttribute> attrs = prop.getAttributes() ;
				int attrNo = 0 ;
				
				for (PropertyAttribute attr:attrs){
					
					
					Hbox hbox = new Hbox() ;
					Label lab2 = new Label(attr.getName()) ;
					hbox.appendChild(lab2) ;
					if (attr.getType().equals(PropertyAttribute.TYPE_TEXT)) {
						
						Textbox txt = new Textbox() ;
						txt.setValue(auctionAttrs.get(attrNo).getValues().get(0).getValue());
						txt.setConstraint("no empty");
						hbox.appendChild(txt) ;
					}
					else
					if (attr.getType().equals(PropertyAttribute.TYPE_DATE)) {
						
						Date date;
						try {
							date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(auctionAttrs.get(attrNo).getValues().get(0).getValue());
						
						 
						Datebox dat = new Datebox() ;
						dat.setValue(date);
						hbox.appendChild(dat) ;
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					if (attr.getType().equals(PropertyAttribute.TYPE_DECIMAL)) {
						BigDecimal bd = new BigDecimal(auctionAttrs.get(attrNo).getValues().get(0).getValue()) ;
						Decimalbox dec = new Decimalbox() ;
						dec.setValue(bd);
						hbox.appendChild(dec) ;
					}
					else
					if (attr.getType().equals(PropertyAttribute.TYPE_SELECT)) {
						Listbox lbx = new Listbox() ;
						lbx.setMold("select");
						
						
						
						if(attr.getValues()!=null && attr.getValues().size()>0) {
							List<PropertyAttributeValue> auctionVals = auctionAttrs.get(attrNo).getValues() ;
							List<PropertyAttributeValue> vals = attr.getValues() ;
							int valNo=0 ;
							
							
							
							
							for (PropertyAttributeValue val:vals) {
								
								Listitem li = new Listitem() ;
								li.setValue(val);
								li.setLabel(val.getValue());
								lbx.appendChild(li) ;
								
									if (auctionVals.get(0).getValue().equals(val.getValue())) {
										lbx.setSelectedItem(li);
									}
								
								
								
								
								
							}
							
						}
						
						hbox.appendChild(lbx) ;
					}
					else
					if (attr.getType().equals(PropertyAttribute.TYPE_MULTISELECT)) {
						
						Vbox vb = new Vbox() ;
						
						if(attr.getValues()!=null && attr.getValues().size()>0) {
							
							List<PropertyAttributeValue> vals = attr.getValues() ;
							int valNo=0;
							
							for (PropertyAttributeValue val:vals) {
								List<PropertyAttributeValue> auctionVals = auctionAttrs.get(attrNo).getValues() ;
								
								Checkbox cb = new Checkbox() ;
								
								cb.setLabel(val.getValue());
								
								for (PropertyAttributeValue v:auctionVals){
									if (val.getValue().equals(v.getValue())) {
										cb.setChecked(true);
									}
								}
							
								
								
								vb.appendChild(cb) ;
								valNo++ ;
							}
							
						}
						hbox.appendChild(vb) ;
						
					}
					
					propBox.appendChild(hbox) ;
					attrNo++ ;
				}
				
				
			}
			vbox.appendChild(propBox) ;
			propNo++ ;
		}
		
	}

	
	
	
	
	
}
