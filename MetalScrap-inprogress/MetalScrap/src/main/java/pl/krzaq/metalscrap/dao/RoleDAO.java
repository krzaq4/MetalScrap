package pl.krzaq.metalscrap.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class RoleDAO {

	@Autowired
	private SessionFactory sessionFactory ;
	
	
	
}
