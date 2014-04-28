package pl.krzaq.metalscrap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.krzaq.metalscrap.dao.DeliveryTypeDAO;
import pl.krzaq.metalscrap.model.DeliveryType;
import pl.krzaq.metalscrap.service.DeliveryTypeService;

@Component(value="deliveryTypeService")
public class DeliveryTypeServiceImpl implements DeliveryTypeService {

	@Autowired
	private DeliveryTypeDAO deliveryTypeDAO ;
	
	@Override
	public List<DeliveryType> findAll() {
		return deliveryTypeDAO.findAll() ;
	}

	public DeliveryTypeDAO getDeliveryTypeDAO() {
		return deliveryTypeDAO;
	}

	public void setDeliveryTypeDAO(DeliveryTypeDAO deliveryTypeDAO) {
		this.deliveryTypeDAO = deliveryTypeDAO;
	}
	
	
	

}
