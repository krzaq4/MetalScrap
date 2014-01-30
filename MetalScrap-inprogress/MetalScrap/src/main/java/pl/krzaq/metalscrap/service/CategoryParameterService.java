package pl.krzaq.metalscrap.service;

import java.util.List;

import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.CategoryParameter;
import pl.krzaq.metalscrap.model.CategoryParameterValue;

public interface CategoryParameterService {

	
	public List<CategoryParameter> findAllParams() ;
	
	public List<CategoryParameter> findAllParams(String lang) ;
	
	public List<CategoryParameter> findAllParams(Category category) ;
	
	public List<CategoryParameter> findAllParams(Category category, String lang) ;
	
	public List<CategoryParameterValue> findParameterValues() ;
	
	public List<CategoryParameterValue> findParameterValues(String lang) ;
	
	public List<CategoryParameterValue> findParameterValues(CategoryParameter categoryParam) ;
	
	public List<CategoryParameterValue> findParameterValues(CategoryParameter categoryParam, String lang) ;
	
	public void save(CategoryParameter categoryParameter) ;
	
	public void delete(CategoryParameter categoryParameter) ;
	
	public void save(CategoryParameterValue categoryParameterValue) ;
	
	public void delete(CategoryParameterValue categoryParameterValue) ;
	
}
