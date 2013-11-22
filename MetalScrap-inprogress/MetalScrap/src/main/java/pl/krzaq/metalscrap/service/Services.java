package pl.krzaq.metalscrap.service;

import pl.krzaq.metalscrap.service.impl.AuctionServiceImpl;
import pl.krzaq.metalscrap.service.impl.DeliveryTypeServiceImpl;
import pl.krzaq.metalscrap.service.impl.PaymentMethodServiceImpl;
import pl.krzaq.metalscrap.service.impl.UserServiceImpl;

public interface Services {

	
	public AuctionServiceImpl getAuctionService() ;
	public UserServiceImpl getUserService() ;
	public DeliveryTypeServiceImpl getDeliveryTypeService() ;
	public PaymentMethodServiceImpl getPaymentMethodService() ;
	
}
