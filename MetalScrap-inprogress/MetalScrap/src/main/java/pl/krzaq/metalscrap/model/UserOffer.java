package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="user_offer")
@NamedQueries({
	@NamedQuery(name="UserOffer.findAll", query="from UserOffer" ),
	@NamedQuery(name="UserOffer.findByAuction", query="from UserOffer uo where uo.auction=:auction"),
	@NamedQuery(name="UserOffer.findById", query="from UserOffer uo where uo.id=:id")
	
	
})
public class UserOffer implements Serializable, Comparable<UserOffer>{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@ManyToOne
	private User user ;
	
	@ManyToOne
	private Auction auction ;
	
	@Column(name="price")
	private Double price ;
	
	@Column(name="date_issued")
	private Date dateIssued ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDateIssued() {
		return dateIssued;
	}

	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}

	@Override
	public int compareTo(UserOffer o) {
		return this.dateIssued.compareTo(o.getDateIssued()) ;
	}
	
}
