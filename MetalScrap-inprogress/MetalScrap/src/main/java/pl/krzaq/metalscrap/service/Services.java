package pl.krzaq.metalscrap.service;

import pl.krzaq.metalscrap.service.impl.AttachementFileServiceImpl;
import pl.krzaq.metalscrap.service.impl.AuctionServiceImpl;
import pl.krzaq.metalscrap.service.impl.CategoryServiceImpl;
import pl.krzaq.metalscrap.service.impl.ConfigServiceImpl;
import pl.krzaq.metalscrap.service.impl.DeliveryTypeServiceImpl;
import pl.krzaq.metalscrap.service.impl.EventServiceImpl;
import pl.krzaq.metalscrap.service.impl.LangLabelServiceImpl;
import pl.krzaq.metalscrap.service.impl.MailServiceImpl;
import pl.krzaq.metalscrap.service.impl.PaymentMethodServiceImpl;
import pl.krzaq.metalscrap.service.impl.PropertyServiceImpl;
import pl.krzaq.metalscrap.service.impl.UserServiceImpl;

public interface Services {

	
	public AuctionServiceImpl getAuctionService() ;
	public UserServiceImpl getUserService() ;
	public DeliveryTypeServiceImpl getDeliveryTypeService() ;
	public PaymentMethodServiceImpl getPaymentMethodService() ;
	public ConfigServiceImpl getConfigService();
	public CategoryServiceImpl getCategoryService() ;
	public AttachementFileServiceImpl getAttachementFileService() ;
	public LangLabelServiceImpl getLangLabelService() ;
	public MailServiceImpl getMailService();
	public PropertyServiceImpl getPropertyService() ;
	public EventServiceImpl getEventService() ;
}
