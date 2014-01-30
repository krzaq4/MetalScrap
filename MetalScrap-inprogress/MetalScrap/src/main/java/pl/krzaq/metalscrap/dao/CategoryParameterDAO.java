package pl.krzaq.metalscrap.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.CategoryParameter;
import pl.krzaq.metalscrap.model.CategoryParameterValue;

@Transactional
public class CategoryParameterDAO {

	
	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<CategoryParameter> findAllParams() {
		return sessionFactory.getCurrentSession().getNamedQuery("CategoryParameter.findAll").list() ;
	}
	
	public List<CategoryParameter> findAllParams(String lang) {
		return sessionFactory.getCurrentSession().getNamedQuery("CategoryParameter.findAllByLang").setParameter("lang", lang).list() ;
	}
	
	public List<CategoryParameter> findAllParams(Category category) {
		return sessionFactory.getCurrentSession().getNamedQuery("CategoryParameter.findByCategory").setParameter("category", category).list() ;
	}
	
	public List<CategoryParameter> findAllParams(Category category, String lang) {
		return sessionFactory.getCurrentSession().getNamedQuery("CategoryParameter.findByCategoryAndLang").setParameter("lang", lang).setParameter("category", category).list() ;
	}
	
	public List<CategoryParameterValue> findParameterValues() {
		return sessionFactory.getCurrentSession().getNamedQuery("CategoryParameterValue.findAll").list() ;
	}
	
	public List<CategoryParameterValue> findParameterValues(String lang) {
		return sessionFactory.getCurrentSession().getNamedQuery("CategoryParameterValue.findAllByLang").setParameter("lang", lang).list() ;
	}
	
	public List<CategoryParameterValue> findParameterValues(CategoryParameter categoryParam) {
		return sessionFactory.getCurrentSession().getNamedQuery("CategoryParameterValue.findByCategoryParameter").setParameter("categoryParameter", categoryParam).list() ;		
	}
	
	public List<CategoryParameterValue> findParameterValues(CategoryParameter categoryParam, String lang) {
		return sessionFactory.getCurrentSession().getNamedQuery("CategoryParameterValue.findByCategoryParameterAndLang").setParameter("categoryParameter", categoryParam).setParameter("lang", lang).list() ;
	}

	public void save(CategoryParameter categoryParameter) {
		sessionFactory.getCurrentSession().saveOrUpdate(categoryParameter);
	}
	
	public void delete(CategoryParameter categoryParameter) {
		sessionFactory.getCurrentSession().delete(categoryParameter);
	}
	
	public void save(CategoryParameterValue categoryParameterValue) {
		sessionFactory.getCurrentSession().saveOrUpdate(categoryParameterValue);
	}
	
	public void delete(CategoryParameterValue categoryParameterValue) {
		sessionFactory.getCurrentSession().delete(categoryParameterValue);
	}
	//----------------------------------------------------------------------------------------------
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
	
	
	
	
	
}
