package pl.krzaq.metalscrap.service.impl;

import java.util.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.krzaq.metalscrap.dao.UserOfferDAO;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.UserOffer;
import pl.krzaq.metalscrap.service.UserOfferService;

@Component(value="userOfferService")
public class UserOfferServiceImpl implements UserOfferService {

	@Autowired
	private UserOfferDAO userOfferDAO ;
	
	
	public List<UserOffer> findAll() {
		return userOfferDAO.findAll() ;
	}

	
	public List<UserOffer> findByAuction(Auction auction) {
		return userOfferDAO.findByAuction(auction) ;
	}

	
	public UserOffer findById(Long id) {
		return userOfferDAO.findById(id) ;
	}

	
	public List<UserOffer> findByDate(Date start, Date end) {
		return userOfferDAO.findByDate(start, end) ;
	}

	
	public void save(UserOffer userOffer) {
		userOfferDAO.save(userOffer);

	}

	
	public void update(UserOffer userOffer) {
		userOfferDAO.update(userOffer);
	}

	
	public void delete(UserOffer userOffer) {
		userOfferDAO.delete(userOffer);
	}

	public UserOfferDAO getUserOfferDAO() {
		return userOfferDAO;
	}

	public void setUserOfferDAO(UserOfferDAO userOfferDAO) {
		this.userOfferDAO = userOfferDAO;
	}
	
	

}
