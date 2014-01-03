package pl.krzaq.metalscrap.service;

import java.util.List;

import pl.krzaq.metalscrap.model.Config;

public interface ConfigService {

	
	public Config findByKey(String key) ;
	
	public List<Config> findAll() ;
	
	public List<String> findKeys() ;
	
	public void save(Config config) ;
	
	public void delete(Config config) ;
	
	public void update(Config config) ;
	
}
