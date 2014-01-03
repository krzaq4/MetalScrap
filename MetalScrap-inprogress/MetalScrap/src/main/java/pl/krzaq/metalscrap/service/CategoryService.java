package pl.krzaq.metalscrap.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import pl.krzaq.metalscrap.model.Category;

public interface CategoryService {

public List<Category> findAll() ;
	
	public List<Category> findSubCategories(Category parent) ;
	
	public Category findParentCategory(Category child) ;
	
	public Category findByName(String name, Category parent) ;

	public List<Category> findByName(String name) ;
	
	public List<Category> findRootCategories() ;
	
	public void save(Category category) ;
	
	public void update(Category category) ;
	
	public void delete(Category category) ;
	
	public void merge(Category category) ;
	
	public void bulkUpdate(Category[] categories) ;
	
	public void bulkSave(Category[] categories) ;
	
	public void bulkDelete(Category[] categories) ;
}
