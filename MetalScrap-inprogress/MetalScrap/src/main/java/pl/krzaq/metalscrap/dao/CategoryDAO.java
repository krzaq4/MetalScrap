package pl.krzaq.metalscrap.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Category;

@Transactional
public class CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory ;

	
	public List<Category> findAll() {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Category.findAll").list() ;
		
	}
	
	public List<Category> findSubCategories(Category parent) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Category.findSubCategories").setParameter("parent", parent).list() ;
		
	}
	
	public Category findParentCategory(Category child) {
		
		return (Category) sessionFactory.getCurrentSession().createCriteria(Category.class).createAlias("Category", "cat").add(Restrictions.eq("cat", child)).list().get(0) ;
	}
	
	public Category findByName(String name, Category parent) {
		
		return (Category) sessionFactory.getCurrentSession().createCriteria(Category.class).add(Restrictions.eq("Category.name", name)).add(Restrictions.eq("Category.parent", parent)).list().get(0) ;
		
	}

	public List<Category> findByName(String name) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Category.findByName").setParameter("name",name).list() ;
		
	}
	
	public List<Category> findRootCategories() {
		
		return sessionFactory.getCurrentSession().createCriteria(Category.class).add(Restrictions.isNull("parent")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list() ;
		
	}
	
	//-----------------------------------------------------------------------------------------------
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
	
	
}
