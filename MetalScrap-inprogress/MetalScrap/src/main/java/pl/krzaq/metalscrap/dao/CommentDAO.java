package pl.krzaq.metalscrap.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.Comment;
import pl.krzaq.metalscrap.model.User;

@Component(value="commentDAO")
public class CommentDAO {

	@Autowired
	private SessionFactory sessionFactory  ;
	
	
	public Comment findById(Long id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Comment.class, "comment") ;
		criteria.add(Restrictions.idEq(id)) ;
		return (Comment) criteria.uniqueResult() ;
	}
	
	public List<Comment> findByAuction(Auction auction) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Comment.class, "comment") ;
		criteria.add(Restrictions.eq("auction", auction)) ;
		
		return criteria.list() ;
	}
	
	public List<Comment> findByUser(User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Comment.class, "comment") ;
		criteria.add(Restrictions.eq("user", user)) ;
		
		return criteria.list() ;
	}
	
	public List<Comment> findByParent(Comment comment) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Comment.class, "comment") ;
		criteria.add(Restrictions.eq("parent", comment)) ;
		
		return criteria.list() ;
	}
	
	
	public List<Comment> findByDateIssued(Date from, Date to, Auction auction, User user) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Comment.class, "comment") ;
		if(from!=null && to!=null) {
			criteria.add(Restrictions.between("issued", from, to)) ;
		} 
		else 
		if (from!=null){
			criteria.add(Restrictions.ge("issued", from)) ;
		} 
		else
		if(to!=null) {
			criteria.add(Restrictions.le("issued", to)) ;
		}
		
		if(auction!=null) {
			criteria.add(Restrictions.eq("auction", auction)) ;
		}
		
		if(user!=null) {
			criteria.add(Restrictions.eq("user", user)) ;
		}
		
		
		return criteria.list() ;
		
	}
	
	public List<Comment> findByType(Date from, Date to, User user, Auction auction, Integer type) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Comment.class, "comment") ;
		if(from!=null && to!=null) {
			criteria.add(Restrictions.between("issued", from, to)) ;
		} 
		else 
		if (from!=null){
			criteria.add(Restrictions.ge("issued", from)) ;
		} 
		else
		if(to!=null) {
			criteria.add(Restrictions.le("issued", to)) ;
		}
		
		if(auction!=null) {
			criteria.add(Restrictions.eq("auction", auction)) ;
		}
		
		if(user!=null) {
			criteria.add(Restrictions.eq("user", user)) ;
		}
		
		if(type!=null) {
			criteria.add(Restrictions.eq("type", type)) ;
		}
		
		
		return criteria.list() ;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	
}
