package pl.krzaq.metalscrap.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.LangLabel;
@Transactional
public class LangLabelDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<LangLabel> findAll() {
		return sessionFactory.getCurrentSession().getNamedQuery("LangLabel.findAll").list() ;
	}
	
	public List<LangLabel> findAllByLang(String lang) {
		return sessionFactory.getCurrentSession().getNamedQuery("LangLabel.findAllByLang").setParameter("lang", lang).list() ;
	}
	
	public LangLabel findByKey(String key, String lang) {
		LangLabel result  = null ;
		List<LangLabel> res = sessionFactory.getCurrentSession().getNamedQuery("LangLabel.findByKeyAndLang").setParameter("lkey", key).setParameter("lang", lang).list() ;
		if (res!=null && res.size()>0) {
			result = res.get(0) ;
		}
		return result ;
	}
	
	public List<LangLabel> findLikeKey(String key) {
		return sessionFactory.getCurrentSession().getNamedQuery("LangLabel.findLikeKey").setParameter("lkey", key).list() ;
	}
	
	public List<LangLabel> findByKey(String key) {
		return sessionFactory.getCurrentSession().getNamedQuery("LangLabel.findByKey").setParameter("lkey", key).list() ;
	}
	
	public List<String> findAllLangs() {
		return sessionFactory.getCurrentSession().getNamedQuery("LangLabel.findAllLangs").list() ;
	}
	
	public List<LangLabel> findLikeKeysUnique(String key) {
		return sessionFactory.getCurrentSession().getNamedQuery("LangLabel.findLikeKeyUnique").setParameter("lkey", key).list() ;
	}
	
	public List<String> findAllKeysUnique() {
		
		return sessionFactory.getCurrentSession().getNamedQuery("LangLabel.findAllKeysUnique").list() ;
	}
	
	public LangLabel findById(Long id) {
		
		List<LangLabel> labels = sessionFactory.getCurrentSession().getNamedQuery("LangLabel.findById").setParameter("id", id).list() ;
		if (labels.size()>0) return labels.get(0) ;
		else return null ;
	}
	
	public void save(LangLabel label) {
		sessionFactory.getCurrentSession().save(label) ;
	}
	
	public void update(LangLabel label) {
		sessionFactory.getCurrentSession().update(label) ;
	}
	
	public void delete(LangLabel label) {
		sessionFactory.getCurrentSession().delete(label) ;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
}
