package pl.krzaq.metalscrap.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.krzaq.metalscrap.dao.UserOfferDAO;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.UserOffer;
import pl.krzaq.metalscrap.service.UserOfferService;

public class UserOfferServiceImpl implements UserOfferService {

	@Autowired
	private UserOfferDAO userOfferDAO ;
	
	@Override
	public List<UserOffer> findAll() {
		return userOfferDAO.findAll() ;
	}

	@Override
	public List<UserOffer> findByAuction(Auction auction) {
		return userOfferDAO.findByAuction(auction) ;
	}

	@Override
	public UserOffer findById(Long id) {
		return userOfferDAO.findById(id) ;
	}

	@Override
	public List<UserOffer> findByDate(Date start, Date end) {
		return userOfferDAO.findByDate(start, end) ;
	}

	@Override
	public void save(UserOffer userOffer) {
		userOfferDAO.save(userOffer);

	}

	@Override
	public void update(UserOffer userOffer) {
		userOfferDAO.update(userOffer);
	}

	@Override
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
