package pl.krzaq.metalscrap.dao;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Category;


public class CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory ;

	@Transactional
	public List<Category> findAll() {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Category.findAll").list() ;
		
	}
	@Transactional
	public List<Category> findSubCategories(Category parent) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Category.findSubCategories").setParameter("parent", parent).list() ;
		
	}
	@Transactional
	public Category findParentCategory(Category child) {
		
		return (Category) sessionFactory.getCurrentSession().createCriteria(Category.class).createAlias("Category", "cat").add(Restrictions.eq("cat", child)).list().get(0) ;
	}
	@Transactional
	public Category findByName(String name, Category parent) {
		
		return (Category) sessionFactory.getCurrentSession().createCriteria(Category.class).add(Restrictions.eq("Category.name", name)).add(Restrictions.eq("Category.parent", parent)).list().get(0) ;
		
	}
	@Transactional
	public List<Category> findByName(String name) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Category.findByName").setParameter("name",name).list() ;
		
	}
	@Transactional
	public List<Category> findRootCategories() {
		
		return sessionFactory.getCurrentSession().createCriteria(Category.class).add(Restrictions.isNull("parent")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list() ;
		
	}
	@Transactional
	public void save(Category category) {
		
		category.setPosition(this.getNextPosition(category.getParent())) ;
		sessionFactory.getCurrentSession().save(category) ;
	}
	@Transactional
	public void update(Category category) {
		
		sessionFactory.getCurrentSession().update(category);
	}
	@Transactional
	public void delete(Category category) {
		
		sessionFactory.getCurrentSession().delete(category);
		this.getInOrder(category.getParent());
	}
	@Transactional
	public void merge(Category category) {
		
		category.setPosition(this.getNextPosition(category.getParent())) ;
		sessionFactory.getCurrentSession().merge(category) ;
		
	}
	@Transactional
	public void bulkUpdate(Category[] categories) {
		
		
		for (int i=0;i<categories.length;i++) {
			
			sessionFactory.getCurrentSession().update(categories[i]);
			
		}
		
		
		
	}
	
	private void getInOrder(Category parent) {
		
		if (parent!=null) {
			List<Category> cats = this.findSubCategories(parent) ;
			Collections.sort(cats);
			int i=1 ;
			for(Category c:cats) {
				
				c.setPosition(i);
				this.update(c);
				i++ ;
				
			}
			
		} else {
			List<Category> cats = this.findRootCategories() ;
			Collections.sort(cats);
			int i=1 ;
			for(Category c:cats) {
				
				c.setPosition(i);
				this.update(c);
				i++ ;
				
			}
		}
		
	}
	
	private int getNextPosition(Category parent) {
		
		int position = -1 ;
		if (parent!=null) {
			List<Category> cats = this.findSubCategories(parent) ;
			
			if (cats!=null && cats.size()>0) {
				Collections.sort(cats);
				position =cats.get(cats.size()-1).getPosition()+1 ;
			} else {
				position = 1 ;
			}
			
		} else {
			
			List<Category> cats = this.findRootCategories() ;
			
			if(cats != null && cats.size()>0) {
				Collections.sort(cats);
				position = cats.get(cats.size()-1).getPosition()+1;
			} else {
				position = 1 ;
			}
		}
		
		return position ;
	}
	
	//-----------------------------------------------------------------------------------------------
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
	
	
}
