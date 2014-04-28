package pl.krzaq.metalscrap.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import pl.krzaq.metalscrap.dao.UserOfferDAO;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.UserOffer;

public interface UserOfferService {

	public UserOfferDAO getUserOfferDAO() ;
	
	public List<UserOffer> findAll() ;
	public List<UserOffer> findByAuction(Auction auction) ;
	
	public UserOffer findById(Long id) ;
	
	public List<UserOffer> findByDate(Date start, Date end);
	
	public void save(UserOffer userOffer) ;
	public void update(UserOffer userOffer);
	
	public void delete(UserOffer userOffer);
	
}
