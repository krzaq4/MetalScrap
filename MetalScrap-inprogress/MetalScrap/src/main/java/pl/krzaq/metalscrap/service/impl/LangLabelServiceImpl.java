package pl.krzaq.metalscrap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.krzaq.metalscrap.dao.LangLabelDAO;
import pl.krzaq.metalscrap.model.LangLabel;
import pl.krzaq.metalscrap.service.LangLabelService;

public class LangLabelServiceImpl implements LangLabelService {

	@Autowired
	private LangLabelDAO langLabelDAO; 
	
	@Override
	public List<LangLabel> findAll() {
		return langLabelDAO.findAll() ;
	}

	@Override
	public LangLabel findByKey(String key, String lang) {
		return langLabelDAO.findByKey(key, lang) ;
	}

	@Override
	public List<LangLabel> findByKey(String key) {
		return langLabelDAO.findByKey(key) ;
	}

	@Override
	public void save(LangLabel label) {
		langLabelDAO.save(label);

	}

	@Override
	public void update(LangLabel label) {
		langLabelDAO.update(label);

	}

	@Override
	public void delete(LangLabel label) {
		langLabelDAO.delete(label);
	}

	public LangLabelDAO getLangLabelDAO() {
		return langLabelDAO;
	}

	public void setLangLabelDAO(LangLabelDAO langLabelDAO) {
		this.langLabelDAO = langLabelDAO;
	}

	@Override
	public List<LangLabel> findAllByLang(String lang) {
		return langLabelDAO.findAllByLang(lang) ;
	}

	@Override
	public List<LangLabel> findLikeKey(String key) {
		return langLabelDAO.findLikeKey(key) ;
	}

	
	
}
