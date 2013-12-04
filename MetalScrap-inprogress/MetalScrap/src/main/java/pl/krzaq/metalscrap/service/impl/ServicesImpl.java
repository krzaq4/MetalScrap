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
	
	public static AuctionServiceImpl getAuctionService() {
		return auctionService ;
	}
	
	public static UserServiceImpl getUserService() {
		
		return userService ;
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

	public static CommodityTypeServiceImpl getCommodityTypeService() {
		return commodityTypeService;
	}

	public void setCommodityTypeService(
			CommodityTypeServiceImpl commodityTypeService) {
		this.commodityTypeService = commodityTypeService;
	}
	
	
	
	
	
	
}
