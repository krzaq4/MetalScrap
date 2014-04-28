package pl.krzaq.metalscrap.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="auction_charges")
public class AuctionCharge {

	public static Integer STATUS_PENDINGPAYMENT = 1 ;
	public static Integer STATUS_EXPIREDPAYMENT = 2 ;
	public static Integer STATUS_PAYED = 3 ;
	
	
	
	@Id
	@GeneratedValue
	private Long id ;
	
	@Column(name="date_issued")
	private Date issued ;
	
	@Column(name="data_required")
	private Date required ;
	
	@Column(name="date_payed")
	private Date payed ;
	
	@OneToOne
	private Auction auction ;
	
	@OneToOne
	private User user ;
	
	@OneToMany
	private List<Charge> charges ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getIssued() {
		return issued;
	}

	public void setIssued(Date issued) {
		this.issued = issued;
	}

	public Date getRequired() {
		return required;
	}

	public void setRequired(Date required) {
		this.required = required;
	}

	public Date getPayed() {
		return payed;
	}

	public void setPayed(Date payed) {
		this.payed = payed;
	}

	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	public List<Charge> getCharges() {
		return charges;
	}

	public void setCharges(List<Charge> charges) {
		this.charges = charges;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}
