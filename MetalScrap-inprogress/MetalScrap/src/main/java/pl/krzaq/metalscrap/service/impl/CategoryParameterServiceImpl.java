package pl.krzaq.metalscrap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.krzaq.metalscrap.dao.CategoryParameterDAO;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.CategoryParameter;
import pl.krzaq.metalscrap.model.CategoryParameterValue;
import pl.krzaq.metalscrap.service.CategoryParameterService;

public class CategoryParameterServiceImpl implements CategoryParameterService {

	@Autowired
	private CategoryParameterDAO categoryParameterDAO ;
	
	
	@Override
	public List<CategoryParameter> findAllParams() {
		return categoryParameterDAO.findAllParams() ;
	}

	@Override
	public List<CategoryParameter> findAllParams(String lang) {
		return categoryParameterDAO.findAllParams(lang) ;
	}

	@Override
	public List<CategoryParameter> findAllParams(Category category) {
		return categoryParameterDAO.findAllParams(category) ;
	}

	@Override
	public List<CategoryParameter> findAllParams(Category category, String lang) {
		return categoryParameterDAO.findAllParams(category, lang) ;
	}

	@Override
	public List<CategoryParameterValue> findParameterValues() {
		return categoryParameterDAO.findParameterValues() ;
	}

	@Override
	public List<CategoryParameterValue> findParameterValues(String lang) {
		return categoryParameterDAO.findParameterValues(lang) ;
	}

	@Override
	public List<CategoryParameterValue> findParameterValues(
			CategoryParameter categoryParam) {
		return categoryParameterDAO.findParameterValues(categoryParam) ;
	}

	@Override
	public List<CategoryParameterValue> findParameterValues(
			CategoryParameter categoryParam, String lang) {
		return categoryParameterDAO.findParameterValues(categoryParam, lang) ;
	}

	@Override
	public void save(CategoryParameter categoryParameter) {
		categoryParameterDAO.save(categoryParameter);

	}

	@Override
	public void delete(CategoryParameter categoryParameter) {
		categoryParameterDAO.delete(categoryParameter);

	}

	@Override
	public void save(CategoryParameterValue categoryParameterValue) {
		categoryParameterDAO.save(categoryParameterValue);

	}

	@Override
	public void delete(CategoryParameterValue categoryParameterValue) {
		categoryParameterDAO.delete(categoryParameterValue);

	}

	public CategoryParameterDAO getCategoryParameterDAO() {
		return categoryParameterDAO;
	}

	public void setCategoryParameterDAO(CategoryParameterDAO categoryParameterDAO) {
		this.categoryParameterDAO = categoryParameterDAO;
	}
	
	

}
