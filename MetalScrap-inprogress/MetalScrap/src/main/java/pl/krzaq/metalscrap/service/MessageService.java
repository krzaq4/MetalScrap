package pl.krzaq.metalscrap.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import pl.krzaq.metalscrap.dao.MessageDAO;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.Message;
import pl.krzaq.metalscrap.model.User;

public interface MessageService {

	public MessageDAO getMessageDAO() ;
	
	public Message findById(Long id) ;
	
	public List<Message> findByAuction(Auction auction) ;
	
	public List<Message> findByUser(User user) ;
	
	public List<Message> findByParent(Message message) ;
	
	public List<Message> findReadByUser(User user, Boolean read) ;
	
	public List<Message> findReadByUser(Auction auction, User user, Boolean read) ;
	
	public List<Message> findByDateIssued(Date from, Date to, Auction auction, User user) ;
	
}
