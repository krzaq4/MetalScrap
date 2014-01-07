package pl.krzaq.metalscrap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="file")
@NamedQueries({
	@NamedQuery(name="File.findAll", query="from AttachementFile a" ),
	@NamedQuery(name="File.findByAuction", query="from AttachementFile a where a.auction=:auction")
	
})
public class AttachementFile {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id ;
	
	@Column(name="path")
	private String path ;
	
	@Column(name="name")
	private String name ;
	
	@ManyToOne
	@JoinColumn(name="auction")
	private Auction auction ;
	
	@Column(name="main")
	private Boolean main ;

	//--------------------------------------------------------------------------
	
	
	public AttachementFile(String name, String path, Auction auction, Boolean main) {
		
		this.name = name ;
		this.path = path ;
		this.auction = auction ;
		this.main = main ;
		
	}
	
	//--------------------------------------------------------------------------
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	public Boolean getMain() {
		return main;
	}

	public void setMain(Boolean main) {
		this.main = main;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
