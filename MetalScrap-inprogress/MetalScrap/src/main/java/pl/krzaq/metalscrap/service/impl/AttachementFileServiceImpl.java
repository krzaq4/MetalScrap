package pl.krzaq.metalscrap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.krzaq.metalscrap.dao.AttachementFileDAO;
import pl.krzaq.metalscrap.model.AttachementFile;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.service.AttachementFileService;

public class AttachementFileServiceImpl implements AttachementFileService {

	@Autowired
	private AttachementFileDAO attachementFileDAO ;
	
	@Override
	public List<AttachementFile> findAll() {
		return this.attachementFileDAO.findAll() ;
	}

	@Override
	public List<AttachementFile> findByAuction(Auction auction) {
		return this.attachementFileDAO.findByAuction(auction) ;
	}

	@Override
	public void save(AttachementFile file) {
		this.attachementFileDAO.save(file);
	}

	@Override
	public void update(AttachementFile file) {
		this.attachementFileDAO.update(file);
	}

	@Override
	public void merge(AttachementFile file) {
		this.attachementFileDAO.merge(file);

	}

	@Override
	public void delete(AttachementFile file) {
		this.attachementFileDAO.delete(file);

	}

	@Override
	public String getNextName() {
		return this.attachementFileDAO.getNextName() ;
	}

	public AttachementFileDAO getAttachementFileDAO() {
		return attachementFileDAO;
	}

	public void setAttachementFileDAO(AttachementFileDAO attachementFileDAO) {
		this.attachementFileDAO = attachementFileDAO;
	}

	
	
}
