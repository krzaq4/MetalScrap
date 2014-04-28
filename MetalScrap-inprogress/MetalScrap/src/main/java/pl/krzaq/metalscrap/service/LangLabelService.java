package pl.krzaq.metalscrap.service;

import java.util.List;

import pl.krzaq.metalscrap.dao.LangLabelDAO;
import pl.krzaq.metalscrap.model.LangLabel;

public interface LangLabelService {

	
	public LangLabelDAO getLangLabelDAO() ;
	
	public List<LangLabel> findAll() ;
	
	public List<LangLabel> findAllByLang(String lang) ;
	
	public LangLabel findByKey(String key, String lang) ;
	
	public List<LangLabel> findByKey(String key) ;
	
	public List<LangLabel> findLikeKey(String key) ;
	
	public List<LangLabel> findLikeKeyUnique(String key) ;
	
	public LangLabel findById(Long id) ;
	
	public List<String> findAllKeysUnique() ;
	
	public List<String> findAllLangs() ;
		
	public void save(LangLabel label);
	
	public void update(LangLabel label);
	
	public void delete(LangLabel label) ;
	
}
