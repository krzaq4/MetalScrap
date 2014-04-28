package pl.krzaq.metalscrap.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Property;
import pl.krzaq.metalscrap.model.PropertyAttribute;
import pl.krzaq.metalscrap.model.PropertyAttributeValue;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

@Transactional
@Component(value="propertyDAO")
public class PropertyDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<Property> findAll() {
		return sessionFactory.getCurrentSession().createCriteria(Property.class).list() ;
	}
	
	public Property findEqual(String equalIdentifier, String lang) {
		Criteria c1 = sessionFactory.getCurrentSession().createCriteria(Property.class, "prop").add(Restrictions.eq("prop.equalIdentifier", equalIdentifier)).add(Restrictions.eq("prop.lang", lang)).createCriteria("prop.attributes", "attrs", JoinType.LEFT_OUTER_JOIN);
		return (Property)c1.uniqueResult() ;
	}
	
	public List<Property> findAll(String lang) {
		return sessionFactory.getCurrentSession().createCriteria(Property.class).add(Restrictions.eq("lang", lang)).list() ;
	}
	
	public Property findPropertyById(Long id) {
		return (Property) sessionFactory.getCurrentSession().createCriteria(Property.class).add(Restrictions.idEq(id)).uniqueResult() ;
	}
	
	public List<PropertyAttribute> findAllAttributes(){
		return sessionFactory.getCurrentSession().createCriteria(PropertyAttribute.class).list() ;
	}
	
	public List<PropertyAttribute> findAllAttributes(Property property){
		return sessionFactory.getCurrentSession().createCriteria(PropertyAttribute.class).add(Restrictions.eq("property", property)).list() ;
	}
	
	public PropertyAttribute findAttributeById(Long id) {
		return (PropertyAttribute) sessionFactory.getCurrentSession().createCriteria(PropertyAttribute.class).add(Restrictions.idEq(id)).uniqueResult() ;
	}
	
	public List<PropertyAttributeValue> findAllValues(){
		return sessionFactory.getCurrentSession().createCriteria(PropertyAttributeValue.class).list() ;
	}
	
	public List<PropertyAttributeValue> findValues(PropertyAttribute attribute) {
		return sessionFactory.getCurrentSession().createCriteria(PropertyAttributeValue.class).add(Restrictions.eq("attribute", attribute)).list() ;
	}
	
	public PropertyAttributeValue findValueById(Long id) {
		return (PropertyAttributeValue) sessionFactory.getCurrentSession().createCriteria(PropertyAttributeValue.class).add(Restrictions.idEq(id)).uniqueResult() ;
	}
	
	public void save(Property property) {
		if (property.getId()!=null) {
			update(property) ;
		}
		else {
			
				sessionFactory.getCurrentSession().save(property);
			
			
		}
		
	}
	
	public void update(Property property) {
		sessionFactory.getCurrentSession().update(property);
	}
	
	public void delete(Object object) {
		sessionFactory.getCurrentSession().delete(object);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
}
