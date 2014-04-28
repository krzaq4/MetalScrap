package pl.krzaq.metalscrap.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.krzaq.metalscrap.dao.MessageDAO;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.Message;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.MessageService;

@Component(value="messageService")
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDAO messageDAO;
	
	@Override
	public Message findById(Long id) {
		return messageDAO.findById(id) ;
	}

	@Override
	public List<Message> findByAuction(Auction auction) {
		return messageDAO.findByAuction(auction) ;
	}

	@Override
	public List<Message> findByUser(User user) {
		return messageDAO.findByUser(user) ;
	}

	@Override
	public List<Message> findByParent(Message message) {
		return messageDAO.findByParent(message) ;
	}

	@Override
	public List<Message> findReadByUser(User user, Boolean read) {
		return messageDAO.findReadByUser(user, read) ;
	}

	@Override
	public List<Message> findReadByUser(Auction auction, User user, Boolean read) {
		return messageDAO.findReadByUser(auction, user, read) ;
	}

	@Override
	public List<Message> findByDateIssued(Date from, Date to, Auction auction,
			User user) {
		return messageDAO.findByDateIssued(from, to, auction, user) ;
	}

	public MessageDAO getMessageDAO() {
		return messageDAO;
	}

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}
	
	

}
