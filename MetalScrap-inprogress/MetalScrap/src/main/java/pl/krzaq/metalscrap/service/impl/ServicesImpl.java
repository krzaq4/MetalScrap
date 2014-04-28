package pl.krzaq.metalscrap.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.krzaq.metalscrap.service.AttachementFileService;
import pl.krzaq.metalscrap.service.AuctionService;
import pl.krzaq.metalscrap.service.CategoryService;
import pl.krzaq.metalscrap.service.CommodityTypeService;
import pl.krzaq.metalscrap.service.ConfigService;
import pl.krzaq.metalscrap.service.DeliveryTypeService;
import pl.krzaq.metalscrap.service.EventService;
import pl.krzaq.metalscrap.service.LangLabelService;
import pl.krzaq.metalscrap.service.MailService;
import pl.krzaq.metalscrap.service.MessageService;
import pl.krzaq.metalscrap.service.PaymentMethodService;
import pl.krzaq.metalscrap.service.PropertyService;
import pl.krzaq.metalscrap.service.UserOfferService;

@Component(value="services")
public class ServicesImpl {

	@Autowired
	private  UserServiceImpl userService ;
	
	@Autowired
	private  AuctionService auctionService ;
	
	@Autowired
	private DeliveryTypeService deliveryTypeService ;
	
	@Autowired
	private PaymentMethodService paymentMethodService ;

	@Autowired
	private CommodityTypeService commodityTypeService ;
	
	@Autowired 
	private ConfigService configService ;
	
	@Autowired 
	private CategoryService categoryService ;
	
	@Autowired
	private AttachementFileService attachementFileService ;
	
	@Autowired
	private UserOfferService userOfferService ;
	
	@Autowired
	private LangLabelService langLabelService ;
	
	@Autowired 
	private MailService mailService ;
	
	@Autowired 
	private PropertyService propertyService ;
	
	@Autowired
	private EventService eventService ;
	
	@Autowired
	private MessageService messageService ;

	public UserServiceImpl getUserService() {
		return userService;
	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	public AuctionService getAuctionService() {
		return auctionService;
	}

	public void setAuctionService(AuctionService auctionService) {
		this.auctionService = auctionService;
	}

	public DeliveryTypeService getDeliveryTypeService() {
		return deliveryTypeService;
	}

	public void setDeliveryTypeService(DeliveryTypeService deliveryTypeService) {
		this.deliveryTypeService = deliveryTypeService;
	}

	public PaymentMethodService getPaymentMethodService() {
		return paymentMethodService;
	}

	public void setPaymentMethodService(PaymentMethodService paymentMethodService) {
		this.paymentMethodService = paymentMethodService;
	}

	public CommodityTypeService getCommodityTypeService() {
		return commodityTypeService;
	}

	public void setCommodityTypeService(CommodityTypeService commodityTypeService) {
		this.commodityTypeService = commodityTypeService;
	}

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public AttachementFileService getAttachementFileService() {
		return attachementFileService;
	}

	public void setAttachementFileService(
			AttachementFileService attachementFileService) {
		this.attachementFileService = attachementFileService;
	}

	public LangLabelService getLangLabelService() {
		return langLabelService;
	}

	public void setLangLabelService(LangLabelService langLabelService) {
		this.langLabelService = langLabelService;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public PropertyService getPropertyService() {
		return propertyService;
	}

	public void setPropertyService(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	public EventService getEventService() {
		return eventService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public UserOfferService getUserOfferService() {
		return userOfferService;
	}

	public void setUserOfferService(UserOfferService userOfferService) {
		this.userOfferService = userOfferService;
	}
	
	
}
