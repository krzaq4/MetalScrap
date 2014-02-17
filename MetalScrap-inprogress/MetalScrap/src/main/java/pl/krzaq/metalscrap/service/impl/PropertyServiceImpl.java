package pl.krzaq.metalscrap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.krzaq.metalscrap.dao.PropertyDAO;
import pl.krzaq.metalscrap.model.Property;
import pl.krzaq.metalscrap.model.PropertyAttribute;
import pl.krzaq.metalscrap.model.PropertyAttributeValue;
import pl.krzaq.metalscrap.service.PropertyService;

public class PropertyServiceImpl implements PropertyService {

	@Autowired
	private PropertyDAO propertyDAO ;
	
	
	@Override
	public List<Property> findAll() {
		return propertyDAO.findAll() ;
	}

	@Override
	public List<Property> findAll(String lang) {
		return propertyDAO.findAll(lang) ;
	}

	@Override
	public Property findPropertyById(Long id) {
		return propertyDAO.findPropertyById(id) ;
	}

	@Override
	public List<PropertyAttribute> findAllAttributes() {
		return propertyDAO.findAllAttributes() ;
	}

	@Override
	public List<PropertyAttribute> findAllAttributes(Property property) {
		return propertyDAO.findAllAttributes(property) ;
	}

	@Override
	public PropertyAttribute findAttributeById(Long id) {
		return propertyDAO.findAttributeById(id) ;
	}

	@Override
	public List<PropertyAttributeValue> findAllValues() {
		return propertyDAO.findAllValues() ;
	}

	@Override
	public List<PropertyAttributeValue> findValues(PropertyAttribute attribute) {
		return propertyDAO.findValues(attribute) ;
	}

	@Override
	public PropertyAttributeValue findValueById(Long id) {
		return propertyDAO.findValueById(id) ;
	}

	@Override
	public void save(Object object) {
		propertyDAO.save(object);

	}

	@Override
	public void delete(Object object) {
		propertyDAO.delete(object);

	}

	public PropertyDAO getPropertyDAO() {
		return propertyDAO;
	}

	public void setPropertyDAO(PropertyDAO propertyDAO) {
		this.propertyDAO = propertyDAO;
	}
	
	
	

}
