package pl.krzaq.metalscrap.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.metamodel.binding.CascadeType;

@Entity
@Table(name="event_log")
public class Event {

	public static Integer TYPE_LOGIN = 1 ;
	public static Integer TYPE_LOGOUT = 2 ;
	
	
	@Id
	@GeneratedValue
	private Long id ;
	
	@Column(name="name")
	private String name ;
	
	@Column(name="type")
	private Integer type ;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user ;
	
	@Column(name="date")
	private Date date ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
