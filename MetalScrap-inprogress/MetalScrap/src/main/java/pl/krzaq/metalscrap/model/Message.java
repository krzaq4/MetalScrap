package pl.krzaq.metalscrap.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="message")
public class Message {

	
	@Id
	@GeneratedValue
	private Long id ;
	
	@Column(name="subject")
	private String subject ;
	
	@Column(name="message")
	private String message ;
	
	@OneToOne
	@JoinColumn(name="parent")
	private Message parent ;
	
	@Column(name="is_read")
	private Boolean read ;
	
	@ManyToOne
	@JoinTable(name="auction_messages", joinColumns=@JoinColumn(name="message_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="auction_id", referencedColumnName="id"))
	private Auction auction ;
	
	@ManyToOne
	@JoinTable(name="user_messages", joinColumns=@JoinColumn(name="message_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="user_id", referencedColumnName="id"))
	private User user ;
	
	@Column(name="issued")
	private Date issued ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Message getParent() {
		return parent;
	}

	public void setParent(Message parent) {
		this.parent = parent;
	}

	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getIssued() {
		return issued;
	}

	public void setIssued(Date issued) {
		this.issued = issued;
	}

	public Boolean getRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}
	
	
	
	
}
