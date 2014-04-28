package pl.krzaq.metalscrap.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.krzaq.metalscrap.model.AttachementFile;
import pl.krzaq.metalscrap.model.Auction;

@Transactional
@Component(value="attachementFileDAO")
public class AttachementFileDAO {

	
	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public List<AttachementFile> findAll() {
		
		return this.sessionFactory.getCurrentSession().getNamedQuery("File.findAll").list(); 
	}
	
	public List<AttachementFile> findByAuction(Auction auction) {
		
		return sessionFactory.getCurrentSession().getNamedQuery("File.findByAuction").setParameter("auction", auction).list() ;
	}

	public AttachementFile findAuctionMain(Auction auction) {
		List<AttachementFile> list = sessionFactory.getCurrentSession().getNamedQuery("File.findAuctionMain").setParameter("auction", auction).list() ;
		if (list.size()>0)
			return (AttachementFile)sessionFactory.getCurrentSession().getNamedQuery("File.findAuctionMain").setParameter("auction", auction).list().get(0) ;
		else return null ;
	}
	
	public void save(AttachementFile file) {
		sessionFactory.getCurrentSession().save(file) ;
	}
	
	public void update(AttachementFile file) {
		sessionFactory.getCurrentSession().save(file) ;
	}
	
	public void merge(AttachementFile file) {
		sessionFactory.getCurrentSession().save(file) ;
	}
	
	public void delete(AttachementFile file) {
		sessionFactory.getCurrentSession().save(file) ;
	}
	
	public String getNextName() {
		
		String ret = "1" ;
		List<String> files = sessionFactory.getCurrentSession().createQuery("select f.name from AttachementFile f").list() ;
		if (files!=null && files.size()>0){
			
			int s = files.size() ;
			String last = files.get(s-1) ;
			Long next = Long.valueOf(last) ;
			next = next + 1  ;
			ret = String.valueOf(next) ;
		}
		
		return ret ;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
}
