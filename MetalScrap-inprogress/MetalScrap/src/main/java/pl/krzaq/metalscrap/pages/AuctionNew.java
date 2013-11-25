package pl.krzaq.metalscrap.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zkplus.databind.AnnotateDataBinder;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.Commodity;
import pl.krzaq.metalscrap.model.CommodityType;
import pl.krzaq.metalscrap.service.Services;
import pl.krzaq.metalscrap.service.impl.AuctionServiceImpl;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class AuctionNew extends HomePage{

	private AnnotateDataBinder binder ;
	
	
	@Override
	public void doAfterCompose(Page arg0, Component[] arg1) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(arg0, arg1);
		
		
	}


	@Override
	public boolean doCatch(Throwable arg0) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void doFinally() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
	
		
		super.doInit(page, arg1) ;
		
		Auction auction = new Auction() ;
		List<Commodity> commodities = new ArrayList<Commodity>(); 
		auction.setCommodities(commodities);
		page.setAttribute("auction", auction) ;
		page.setAttribute("commodity", new Commodity() ) ;
		page.setAttribute("paymentMethods", ServicesImpl.getPaymentMethodService().findAll()) ;
		page.setAttribute("deliveryTypes", ServicesImpl.getDeliveryTypeService().findAll()) ;
		
		
		System.out.println("Page init") ;
		
		this.getPageData(page);
		this.binder = new AnnotateDataBinder(page) ;
		binder.loadAll();
	}






	

	
	
	
	
	
}
