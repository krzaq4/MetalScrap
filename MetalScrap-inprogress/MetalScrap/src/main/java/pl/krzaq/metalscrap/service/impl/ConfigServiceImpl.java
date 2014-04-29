package pl.krzaq.metalscrap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import pl.krzaq.metalscrap.dao.ConfigDAO;
import pl.krzaq.metalscrap.model.Config;
import pl.krzaq.metalscrap.service.ConfigService;

@Component(value="configService")
@Service
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private ConfigDAO configDAO ;
	
	@Override
	public Config findByKey(String key) {
		return configDAO.findByKey(key) ;
	}

	@Override
	public List<Config> findAll() {
		return configDAO.findAll() ;
	}
	
	@Override
	public List<String> findKeys() {
		return configDAO.findKeys() ;
	}

	public ConfigDAO getConfigDAO() {
		return configDAO;
	}

	public void setConfigDAO(ConfigDAO configDAO) {
		this.configDAO = configDAO;
	}

	@Override
	public void save(Config config) {
		this.configDAO.save(config);
		
	}

	@Override
	public void delete(Config config) {
		
		this.configDAO.delete(config);
		
	}

	@Override
	public void update(Config config) {
		
		this.configDAO.update(config);
		
	}

	
	
	

}
