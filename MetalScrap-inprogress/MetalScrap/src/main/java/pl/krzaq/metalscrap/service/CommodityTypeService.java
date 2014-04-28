package pl.krzaq.metalscrap.service;

import java.util.List;

import pl.krzaq.metalscrap.dao.CommodityTypeDAO;
import pl.krzaq.metalscrap.model.CommodityType;

public interface CommodityTypeService {

	public CommodityTypeDAO getCommodityTypeDAO() ;
	public List<CommodityType> findAll() ;
	
	
}
