package pl.krzaq.metalscrap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.krzaq.metalscrap.dao.CommodityTypeDAO;
import pl.krzaq.metalscrap.model.CommodityType;
import pl.krzaq.metalscrap.service.CommodityTypeService;

public class CommodityTypeServiceImpl implements CommodityTypeService {

	
	@Autowired
	private CommodityTypeDAO commodityTypeDAO ;
	
	@Override
	public List<CommodityType> findAll() {
		return commodityTypeDAO.findAll() ;
	}

	public CommodityTypeDAO getCommodityTypeDAO() {
		return commodityTypeDAO;
	}

	public void setCommodityTypeDAO(CommodityTypeDAO commodityTypeDAO) {
		this.commodityTypeDAO = commodityTypeDAO;
	}

	
	
	
}
