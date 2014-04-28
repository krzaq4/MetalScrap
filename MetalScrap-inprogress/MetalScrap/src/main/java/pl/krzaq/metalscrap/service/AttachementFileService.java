package pl.krzaq.metalscrap.service;

import java.util.List;

import pl.krzaq.metalscrap.dao.AttachementFileDAO;
import pl.krzaq.metalscrap.model.AttachementFile;
import pl.krzaq.metalscrap.model.Auction;

public interface AttachementFileService {

	public AttachementFileDAO getAttachementFileDAO();
	public List<AttachementFile> findAll() ;
	public List<AttachementFile> findByAuction(Auction auction);
	public AttachementFile findAuctionMain(Auction auction) ;
	public void save(AttachementFile file) ;
	public void update(AttachementFile file) ;
	public void merge(AttachementFile file) ;
	public void delete(AttachementFile file);
	public String getNextName() ;
	
	
	
}
