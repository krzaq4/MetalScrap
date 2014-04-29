package pl.krzaq.metalscrap.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.Config;

@Transactional
@Component(value="configDAO")
@Service
public class ConfigDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	public Config findByKey(String key) {
		
		return (Config) sessionFactory.getCurrentSession().getNamedQuery("Config.findByKey").setParameter("key", key).list().get(0) ;
	}
	
	public List<Config> findAll() {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Config.findAll").list() ;
	}
	
	public List<String> findKeys() {
		
		return sessionFactory.getCurrentSession().getNamedQuery("Config.findKeys").list() ;
	}
	
	public void save(Config config) {
		
		sessionFactory.getCurrentSession().save(config) ;
		
	}

	public void delete(Config config) {
		
		sessionFactory.getCurrentSession().delete(config);
		
	}
	
	public void update(Config config) {
		
		sessionFactory.getCurrentSession().update(config);
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
	
	
}
