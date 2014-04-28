package pl.krzaq.metalscrap.service;

import java.util.List;

import pl.krzaq.metalscrap.dao.DeliveryTypeDAO;
import pl.krzaq.metalscrap.model.DeliveryType;

public interface DeliveryTypeService {

	public DeliveryTypeDAO getDeliveryTypeDAO();
	
	public List<DeliveryType> findAll() ;
}
