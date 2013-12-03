package pl.krzaq.metalscrap.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="auction_status")
@NamedQueries({
	@NamedQuery(name="AuctionStatus.findAll", query="from AuctionStatus a" )
	
})
public class AuctionStatus implements Serializable{

	
	public static final int STATUS_NEW =1 ;
	public static final int STATUS_STARTED =2 ;
	public static final int STATUS_FINISHED =3 ;
	
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="name")
	private String name ;
	
	@Column(name="code")
	private Integer code ;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="status")
	private Set<Auction> auctions ;
}
