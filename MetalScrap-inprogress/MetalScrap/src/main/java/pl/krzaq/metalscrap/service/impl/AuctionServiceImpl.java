package pl.krzaq.metalscrap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.krzaq.metalscrap.dao.AuctionDAO;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.service.AuctionService;

public class AuctionServiceImpl implements AuctionService {

	@Autowired
	private AuctionDAO auctionDAO ;
	
	@Override
	public List<Auction> findAll() {
		
		return auctionDAO.findAll() ;
	}

	public AuctionDAO getAuctionDAO() {
		return auctionDAO;
	}

	public void setAuctionDAO(AuctionDAO auctionDAO) {
		this.auctionDAO = auctionDAO;
	}

	
	
	
	
}
