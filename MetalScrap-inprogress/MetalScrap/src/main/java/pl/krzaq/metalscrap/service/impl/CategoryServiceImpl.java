package pl.krzaq.metalscrap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.krzaq.metalscrap.dao.CategoryDAO;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.Property;
import pl.krzaq.metalscrap.service.CategoryService;
import pl.krzaq.metalscrap.utils.Utilities;

@Component(value="categoryService")
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDAO categoryDAO ;
	
	@Override
	public List<Category> findAll() {
		return categoryDAO.findAll() ;
	}
	
	@Override
	public List<Category> findAllByLang(String lang) {
		return categoryDAO.findAllByLang(lang) ;
	}

	@Override
	public List<Category> findSubCategories(Category parent) {
		return categoryDAO.findSubCategories(parent) ;
	}
	
	@Override
	public List<Category> findSubCategoriesByLang(Category parent, String lang) {
		return categoryDAO.findSubCategoriesByLang(parent, lang) ;
	}

	@Override
	public Category findParentCategory(Category child) {
		return categoryDAO.findParentCategory(child) ;
	}
	
	@Override
	public Category findParentCategoryByLang(Category child, String lang) {
		return categoryDAO.findParentCategoryByLang(child, lang) ;
	}

	@Override
	public Category findByName(String name, Category parent) {
		return categoryDAO.findByName(name, parent) ;
	}
	
	@Override
	public Category findByNameAndLang(String name, Category parent, String lang) {
		return categoryDAO.findByNameAndLang(name, parent, lang) ;
	}

	@Override
	public List<Category> findByName(String name) {
		return categoryDAO.findByName(name) ;
	}
	
	@Override
	public List<Category> findByNameAndLang(String name, String lang) {
		return categoryDAO.findByNameAndLang(name, lang) ;
	}

	@Override
	public List<Category> findRootCategories() {
		return categoryDAO.findRootCategories() ;
	}
	
	@Override
	public List<Category> findRootCategoriesByLang(String lang) {
		return categoryDAO.findRootCategoriesByLang(lang) ;
	}

	@Override 
	public Category getEqual(String equalIdent, String lang) {
		return categoryDAO.findEqual(equalIdent, lang) ;
	}
	
	@Override
	public List<Category> getEquals(String equalIdent) {
		return categoryDAO.findEquals(equalIdent) ;
	}
	
	@Override
	public void save(Category category) {
		
			categoryDAO.save(category) ;
	

	}

	@Override
	public void update(Category category) {
		categoryDAO.update(category);

	}

	@Override
	public void delete(Category category) {
			
		categoryDAO.delete(category);
			
	}

	public CategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@Override
	public void merge(Category category) {
		this.categoryDAO.merge(category);
		
	}

	@Override
	public void bulkUpdate(Category[] categories) {
		this.categoryDAO.bulkUpdate(categories);
		
	}

	@Override
	public void bulkSave(Category[] categories) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bulkDelete(Category[] categories) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Category findById(Long id) {
		return categoryDAO.findById(id) ;
	}

	@Override
	public Category cloneCategory(Category category, String lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getChildrenDown(Category cat) {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public Category cloneCategory(Category category, String lang) {
		
		category = this.findById(category.getId()) ;
		Category result = new Category() ;
		
		result.setDescription(category.getDescription());
		result.setEqualIdentifier(category.getEqualIdentifier());
		result.setLang(lang);
		result.setName(category.getName());
		result.setPosition(category.getPosition());
		result.setParent(category.getParent());
		
		List<Category> children = new ArrayList<Category>() ;
		
		Category cat = this.findById(category.getId()) ;
		
		while(cat.getChildren()!=null && cat.getChildren().size()>0) {
			
			for (Category child: category.getChildren()) {
				
				
				
			}
			
			
		} 
			result.setChildren(children);
			
		
		
		List<Property> props = category.getProperties() ;
		
		
		
		
	}
	*/
	
	/*private List<Category> getChildrenDown(Category cat){
		List
		while(cat.getChildren()!=null && cat.getChildren().size()>0) {
			
			for (Category child: cat.getChildren()) {
				
				
				
			}
			
			
		} 
		
	}*/
	
	

}
