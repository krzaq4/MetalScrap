package pl.krzaq.metalscrap.pages;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;
import pl.krzaq.metalscrap.model.DeliveryType;
import pl.krzaq.metalscrap.model.PaymentMethod;
import pl.krzaq.metalscrap.service.impl.AuctionServiceImpl;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class AuctionList extends HomePage{

	
	
	
	
	@Override
	public void doAfterCompose(Page page, Component[] arg1) throws Exception {

		Combobox statusCombo = (Combobox) page.getFellow("statusSearch") ;
		Combobox paymentCombo = (Combobox) page.getFellow("paymentSearch") ;
		Combobox deliveryCombo = (Combobox) page.getFellow("deliverySearch") ;
		
		/*statusCombo.setSelectedItem(statusCombo.getItems().get(0)) ;
		paymentCombo.setSelectedItem(paymentCombo.getItems().get(0));
		deliveryCombo.setSelectedItem(deliveryCombo.getItems().get(0));*/
		
		
		/*
		Comboitem statusItem = statusCombo.appendItem("Wszystkie") ;
		statusCombo.setSelectedItem(statusItem);
		
		Comboitem paymentItem = paymentCombo.appendItem("Wszystkie") ;
		paymentCombo.setSelectedItem(paymentItem);
		
		Comboitem deliveryItem = deliveryCombo.appendItem("Wszystkie") ;
		deliveryCombo.setSelectedItem(deliveryItem);*/
		
		
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
	
		AuctionServiceImpl auctionService = ServicesImpl.getAuctionService() ;
		super.doInit(page, arg1) ;
		
		List<Auction> auctions = auctionService.findAll() ;
		List<AuctionStatus> statuses = auctionService.findAllStatuses() ;
		List<PaymentMethod> payments = ServicesImpl.getPaymentMethodService().findAll() ;
		List<DeliveryType> deliveries = ServicesImpl.getDeliveryTypeService().findAll() ;
		
		AuctionStatus as = new AuctionStatus() ;
		as.setCode(-1);
		as.setName("Wszystkie");
		
		PaymentMethod pm = new PaymentMethod() ;
		pm.setCode(-1);
		pm.setName("Wszystkie");
		
		DeliveryType dt = new DeliveryType() ;
		dt.setCode(-1);
		dt.setName("Wszystkie");
		
		statuses.add(0, as);
		payments.add(0, pm) ;
		deliveries.add(0, dt) ;
		
		
		page.setAttribute("selectedStatus", as) ;
		page.setAttribute("selectedPayment", pm) ;
		page.setAttribute("selectedDelivery", dt) ;
		
		page.setAttribute("auction", null) ;
		page.setAttribute("auctions", auctions) ;
		page.setAttribute("statuses", statuses) ;
		page.setAttribute("payments", payments) ;
		page.setAttribute("deliveries", deliveries) ;
		
		

	}






	

	
	
	
	
	
}
