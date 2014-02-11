package pl.krzaq.metalscrap.service.impl;


import org.springframework.beans.factory.annotation.Autowired;


public class ServicesImpl {

	@Autowired
	private static UserServiceImpl userService ;
	@Autowired
	private static AuctionServiceImpl auctionService ;
	
	@Autowired
	private static DeliveryTypeServiceImpl deliveryTypeService ;
	
	@Autowired
	private static PaymentMethodServiceImpl paymentMethodService ;

	@Autowired
	private static CommodityTypeServiceImpl commodityTypeService ;
	
	@Autowired 
	private static ConfigServiceImpl configService ;
	
	@Autowired 
	private static CategoryServiceImpl categoryService ;
	
	@Autowired
	private static AttachementFileServiceImpl attachementFileService ;
	
	@Autowired
	private static UserOfferServiceImpl userOfferService ;
	
	@Autowired
	private static LangLabelServiceImpl langLabelService ;
	
	@Autowired static MailServiceImpl mailService ;
	
	public static MailServiceImpl getMailService(){
		return mailService ;
	}

	public static LangLabelServiceImpl getLangLabelService() {
		return langLabelService ;
	}
	
	public static AuctionServiceImpl getAuctionService() {
		return auctionService ;
	}
	
	public static UserServiceImpl getUserService() {
		
		return userService ;
	}

	public void setMailService(MailServiceImpl mailService){
		this.mailService = mailService ;
	}
	
	public void setLangLabelService(LangLabelServiceImpl langLabelService) {
		this.langLabelService = langLabelService ;
	}
	
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	public void setAuctionService(AuctionServiceImpl auctionService) {
		this.auctionService = auctionService;
	}

	public static DeliveryTypeServiceImpl getDeliveryTypeService() {
		return deliveryTypeService;
	}

	public void setDeliveryTypeService(
			DeliveryTypeServiceImpl deliveryTypeService) {
		this.deliveryTypeService = deliveryTypeService;
	}

	public static PaymentMethodServiceImpl getPaymentMethodService() {
		return paymentMethodService;
	}

	public void setPaymentMethodService(
			PaymentMethodServiceImpl paymentMethodService) {
		this.paymentMethodService = paymentMethodService;
	}
	
	public  void setConfigService(ConfigServiceImpl configService) {
		this.configService = configService;
	}

	public void setCommodityTypeService(
			CommodityTypeServiceImpl commodityTypeService) {
		this.commodityTypeService = commodityTypeService;
	}

	public static CommodityTypeServiceImpl getCommodityTypeService() {
		return commodityTypeService;
	}
	
	
	public void setCategoryService(CategoryServiceImpl categoryService) {
		this.categoryService = categoryService;
	}

	public static AttachementFileServiceImpl getAttachementFileService() {
		return attachementFileService ;
	}
	
	public void setAttachementFileService(AttachementFileServiceImpl attachementFileService) {
		this.attachementFileService = attachementFileService ;
	}
	
	public static ConfigServiceImpl getConfigService() {
		
		return configService ;
		
	}
	
	public static CategoryServiceImpl getCategoryService() {
		
		return categoryService ;
		
	}
	
	public static UserOfferServiceImpl getUserOfferService() {
		return userOfferService ;
	}
	
	public void setUserOfferService(UserOfferServiceImpl userOfferService) {
		this.userOfferService = userOfferService ;
	}

	
	
	
	
	
	
	
}
