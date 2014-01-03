package pl.krzaq.metalscrap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.krzaq.metalscrap.dao.CategoryDAO;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDAO categoryDAO ;
	
	@Override
	public List<Category> findAll() {
		return categoryDAO.findAll() ;
	}

	@Override
	public List<Category> findSubCategories(Category parent) {
		return categoryDAO.findSubCategories(parent) ;
	}

	@Override
	public Category findParentCategory(Category child) {
		return categoryDAO.findParentCategory(child) ;
	}

	@Override
	public Category findByName(String name, Category parent) {
		return categoryDAO.findByName(name, parent) ;
	}

	@Override
	public List<Category> findByName(String name) {
		return categoryDAO.findByName(name) ;
	}

	@Override
	public List<Category> findRootCategories() {
		return categoryDAO.findRootCategories() ;
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
	
	

}
