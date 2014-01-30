package pl.krzaq.metalscrap.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import pl.krzaq.metalscrap.model.Category;

public interface CategoryService {

	public List<Category> findAll() ;
	
	public List<Category> findAllByLang(String lang) ;
	
	public List<Category> findSubCategories(Category parent) ;
	
	public List<Category> findSubCategoriesByLang(Category parent, String lang) ;
	
	public Category findParentCategory(Category child) ;
	
	public Category findParentCategoryByLang(Category child, String lang) ;
	
	public Category findByName(String name, Category parent) ;
	
	public Category findByNameAndLang(String name, Category parent, String lang) ;

	public List<Category> findByName(String name) ;
	
	public List<Category> findByNameAndLang(String name, String lang) ;
	
	public List<Category> findRootCategories() ;
	
	public List<Category> findRootCategoriesByLang(String lang) ;
	
	public void save(Category category) ;
	
	public void update(Category category) ;
	
	public void delete(Category category) ;
	
	public void merge(Category category) ;
	
	public void bulkUpdate(Category[] categories) ;
	
	public void bulkSave(Category[] categories) ;
	
	public void bulkDelete(Category[] categories) ;
}
