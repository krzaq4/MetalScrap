package pl.krzaq.metalscrap.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.krzaq.metalscrap.dao.AuctionDAO;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;
import pl.krzaq.metalscrap.service.AuctionService;

public class AuctionServiceImpl implements AuctionService {

	@Autowired
	private AuctionDAO auctionDAO ;
	
	@Override
	public List<Auction> findAll() {
		
		return auctionDAO.findAll() ;
	}

	

	@Override
	public List<Auction> findByStatus(int status) {
		return auctionDAO.findByStatus(status) ;
	}

	@Override
	public List<Auction> findByStartTime(Date start) {
		return auctionDAO.findByStartTime(start) ;
	}

	@Override
	public List<Auction> findByEndTime(Date end) {
		return auctionDAO.findByEndTime(end) ;
	}

	@Override
	public List<AuctionStatus> findAllStatuses() {
		return auctionDAO.findAllStatuses() ;
	}

	@Override
	public AuctionStatus findStatusByCode(int code) {
		return auctionDAO.findStatusByCode(code) ;
	}

	@Override
	public Auction findById(Long id) {
		return auctionDAO.findById(id) ;
	}

	@Override
	public void save(Auction a) {
		auctionDAO.save(a);
	}

	@Override
	public void update(Auction a) {
		auctionDAO.update(a);
	}

	//------------------------------------------------------------------------------------
	
	public AuctionDAO getAuctionDAO() {
		return auctionDAO;
	}

	public void setAuctionDAO(AuctionDAO auctionDAO) {
		this.auctionDAO = auctionDAO;
	}
	
}
