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

	
	public AuctionService getAuctionService() ;
	public UserService getUserService() ;
	public DeliveryTypeService getDeliveryTypeService() ;
	public PaymentMethodService getPaymentMethodService() ;
	public ConfigService getConfigService();
	public CategoryService getCategoryService() ;
	public AttachementFileService getAttachementFileService() ;
	public LangLabelService getLangLabelService() ;
	public MailService getMailService();
	public PropertyService getPropertyService() ;
	public EventService getEventService() ;
	public UserOfferService getUserOfferService() ;
	public CommodityTypeService getCommodityTypeService() ;
}
