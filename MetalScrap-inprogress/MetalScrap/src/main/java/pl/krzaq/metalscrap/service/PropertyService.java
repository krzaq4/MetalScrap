package pl.krzaq.metalscrap.service;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import pl.krzaq.metalscrap.model.Property;
import pl.krzaq.metalscrap.model.PropertyAttribute;
import pl.krzaq.metalscrap.model.PropertyAttributeValue;

public interface PropertyService {

	
	public List<Property> findAll() ;
	
	public List<Property> findAll(String lang) ;
	
	public Property findPropertyById(Long id) ;
	
	public List<PropertyAttribute> findAllAttributes();
	
	public List<PropertyAttribute> findAllAttributes(Property property);
	
	public PropertyAttribute findAttributeById(Long id) ;
	
	public List<PropertyAttributeValue> findAllValues();
	
	public List<PropertyAttributeValue> findValues(PropertyAttribute attribute) ;
	
	public PropertyAttributeValue findValueById(Long id) ;
	
	public void save(Property property) ;
	
	public void delete(Object object) ;
	
	
}
