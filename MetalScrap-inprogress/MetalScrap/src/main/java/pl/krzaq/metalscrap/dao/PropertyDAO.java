package pl.krzaq.metalscrap.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Property;
import pl.krzaq.metalscrap.model.PropertyAttribute;
import pl.krzaq.metalscrap.model.PropertyAttributeValue;

@Transactional
public class PropertyDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<Property> findAll() {
		return sessionFactory.getCurrentSession().createCriteria(Property.class).list() ;
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
	
	public void save(Object object) {
		sessionFactory.getCurrentSession().saveOrUpdate(object);
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
