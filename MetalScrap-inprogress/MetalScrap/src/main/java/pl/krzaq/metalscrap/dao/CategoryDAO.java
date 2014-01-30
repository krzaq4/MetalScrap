package pl.krzaq.metalscrap.dao;

import java.util.Collections;
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
	
	public List<Category> findAllByLang(String lang) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Category.findAllByLang").setParameter("lang", lang).list() ;
		
	}
	
	public List<Category> findSubCategories(Category parent) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Category.findSubCategories").setParameter("parent", parent).list() ;
		
	}
	
	public List<Category> findSubCategoriesByLang(Category parent, String lang) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Category.findSubCategoriesByLang").setParameter("parent", parent).setParameter("lang", lang).list() ;
		
	}

	
	public Category findParentCategory(Category child) {
		
		return (Category) sessionFactory.getCurrentSession().createCriteria(Category.class).createAlias("Category", "cat").add(Restrictions.eq("cat", child)).list().get(0) ;
	}
	
	public Category findParentCategoryByLang(Category child, String lang) {
		
		return (Category) sessionFactory.getCurrentSession().createCriteria(Category.class).createAlias("Category", "cat").add(Restrictions.eq("cat", child)).add(Restrictions.eq("cat.lang", lang)).list().get(0) ;
	}
	
	public Category findByName(String name, Category parent) {
		
		return (Category) sessionFactory.getCurrentSession().createCriteria(Category.class).add(Restrictions.eq("Category.name", name)).add(Restrictions.eq("Category.parent", parent)).list().get(0) ;
		
	}
	
	public Category findByNameAndLang(String name, Category parent, String lang) {
		
		return (Category) sessionFactory.getCurrentSession().createCriteria(Category.class).add(Restrictions.eq("Category.name", name)).add(Restrictions.eq("Category.parent", parent)).add(Restrictions.eq("Category.lang", lang)).list().get(0) ;
		
	}
	
	
	public List<Category> findByName(String name) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Category.findByName").setParameter("name",name).list() ;
		
	}
	
	public List<Category> findByNameAndLang(String name, String lang) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Category.findByNameAndLang").setParameter("name",name).setParameter("lang", lang).list() ;
		
	}
	
	public List<Category> findRootCategories() {
		
		return sessionFactory.getCurrentSession().createCriteria(Category.class).add(Restrictions.isNull("parent")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list() ;
		
	}
	
	public List<Category> findRootCategoriesByLang(String lang) {
		
		return sessionFactory.getCurrentSession().createCriteria(Category.class).add(Restrictions.isNull("parent")).add(Restrictions.eq("lang", lang)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list() ;
		
	}
	
	
	public void save(Category category) {
		
		category.setPosition(this.getNextPosition(category.getParent(), category.getLang())) ;
		sessionFactory.getCurrentSession().save(category) ;
	}
	@Transactional
	public void update(Category category) {
		
		sessionFactory.getCurrentSession().update(category);
	}
	@Transactional
	public void delete(Category category) {
		
		sessionFactory.getCurrentSession().delete(category);
		this.getInOrder(category.getParent(), category.getLang());
	}
	@Transactional
	public void merge(Category category) {
		
		category.setPosition(this.getNextPosition(category.getParent(), category.getLang())) ;
		sessionFactory.getCurrentSession().merge(category) ;
		
	}
	@Transactional
	public void bulkUpdate(Category[] categories) {
		
		
		for (int i=0;i<categories.length;i++) {
			
			sessionFactory.getCurrentSession().update(categories[i]);
			
		}
		
		
		
	}
	
	private void getInOrder(Category parent, String lang) {
		
		if (parent!=null) {
			List<Category> cats = this.findSubCategoriesByLang(parent, lang) ;
			Collections.sort(cats);
			int i=1 ;
			for(Category c:cats) {
				
				c.setPosition(i);
				this.update(c);
				i++ ;
				
			}
			
		} else {
			List<Category> cats = this.findRootCategoriesByLang(lang) ;
			Collections.sort(cats);
			int i=1 ;
			for(Category c:cats) {
				
				c.setPosition(i);
				this.update(c);
				i++ ;
				
			}
		}
		
	}
	
	private int getNextPosition(Category parent, String lang) {
		
		int position = -1 ;
		if (parent!=null) {
			List<Category> cats = this.findSubCategoriesByLang(parent, lang) ;
			
			if (cats!=null && cats.size()>0) {
				Collections.sort(cats);
				position =cats.get(cats.size()-1).getPosition()+1 ;
			} else {
				position = 1 ;
			}
			
		} else {
			
			List<Category> cats = this.findRootCategoriesByLang(lang) ;
			
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
