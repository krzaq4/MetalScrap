package pl.krzaq.metalscrap.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="comment")
public class Comment {

	
	public static Integer TYPE_POSITIVE = 1 ;
	public static Integer TYPE_NEUTRAL = 2 ;
	public static Integer TYPE_NEGATIVE = 3 ;
	
	
	@Id
	@GeneratedValue
	private Long id ;
	
	@Column(name="comment")
	private String comment ;
	
	@OneToOne
	@JoinColumn(name="parent")
	private Comment parent ;
	
	@ManyToOne
	@JoinTable(name="auction_comments", joinColumns=@JoinColumn(name="comment_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="auction_id", referencedColumnName="id"))
	private Auction auction ;
	
	@ManyToOne
	@JoinTable(name="user_comments", joinColumns=@JoinColumn(name="comment_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="user_id", referencedColumnName="id"))
	private User user ;
	
	@Column(name="issued")
	private Date issued ;
	
	@Column(name="type")
	private Integer type ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Comment getParent() {
		return parent;
	}

	public void setParent(Comment parent) {
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
	
}
