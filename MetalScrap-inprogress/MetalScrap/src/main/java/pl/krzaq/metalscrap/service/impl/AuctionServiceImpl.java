package pl.krzaq.metalscrap.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.StringUtils;

import pl.krzaq.metalscrap.dao.AuctionDAO;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;
import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.DeliveryType;
import pl.krzaq.metalscrap.model.PaymentMethod;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.AuctionService;
import pl.krzaq.metalscrap.utils.Utilities;

@Component(value="auctionService")
public class AuctionServiceImpl implements AuctionService {

	@Autowired
	private AuctionDAO auctionDAO ;
	
	@Override
	public List<Auction> findAll() {
		
		return auctionDAO.findAll() ;
	}

	@Override
	public List<Long> findIds(AuctionStatus status, Date from, Date to) {
		return auctionDAO.findIds(status, from, to) ;
	}

	@Override
	public List<Auction> findByStatus(AuctionStatus status) {
		return auctionDAO.findByStatus(status) ;
	}

	@Override
	public List<Auction> findByStartTime(Date start) {
		return auctionDAO.findByStartTime(start) ;
	}

	@Override
	public List<Auction> findByEndTime(Date end) {
		return auctionDAO.findByEndTime(end) ;
	}

	@Override
	public List<AuctionStatus> findAllStatuses() {
		return auctionDAO.findAllStatuses() ;
	}

	@Override
	public AuctionStatus findStatusByCode(int code) {
		return auctionDAO.findStatusByCode(code) ;
	}

	@Override
	public Auction findById(Long id) {
		return auctionDAO.findById(id) ;
	}
	
	@Override
	public Auction findWithCollection(Long id) {
		Auction result = auctionDAO.findById(id) ;
		result.getCommodities().addAll(auctionDAO.findAuctionCommodities(result)) ;
		result.setFiles(Utilities.getServices().getAttachementFileService().findByAuction(result)) ;
		return result ;
	}
	
	@Override
	public Auction findByName(String name) {
		return auctionDAO.findByName(name) ;
	}



	@Override
	public Auction findByNumber(String number) {
		return auctionDAO.findByNumber(number) ;
	}

	@Override
	public List<Auction> filter(String phrase, AuctionStatus status,
			PaymentMethod method, DeliveryType type, Date startDate,
			Date endDate) {
		List<Auction> result = this.auctionDAO.findAll() ;
		Iterator<Auction> it = result.iterator() ;
		while(it.hasNext()) {
			Auction next = it.next() ;
			boolean remove = false ;
			if (status!=null) {
				if (!next.getStatus().getCode().equals(status.getCode()) && status.getCode()>=0) {
					remove = true ;
				}
			}
		
			if (method!=null){
				if (!next.getPaymentMethod().getCode().equals(method.getCode()) && method.getCode()>=0) {
					remove = true ;
				}
			}
			
			if (type!=null) {
				if(!next.getDeliveryType().getCode().equals(type.getCode()) && type.getCode()>=0) {
					remove = true ;
				}
				
			}
			
			if (startDate!=null) {
				if (next.getStartDate().compareTo(startDate)<0) {
					remove = true ;
				}
			}
			
			if (endDate!=null) {
				if (next.getEndDate().compareTo(endDate)>0){
					remove = true ;
				}
			}
		
			if (!StringUtils.isNullOrEmpty(phrase)) {
				
				if (!next.getName().toLowerCase().contains(phrase.toLowerCase()) && !next.getNumber().toLowerCase().contains(phrase.toLowerCase())) {
					remove = true ;
				}
				
			}
		
			if (remove){
				it.remove();
			}
			 
		}
		
		return result ;
	}

	@Override
	public void save(Auction a) {
		auctionDAO.save(a);
	}

	@Override
	public void update(Auction a) {
		auctionDAO.update(a);
	}
	
	@Override
	public void delete(Auction a) {
		auctionDAO.delete(a) ;
		
	}


	//------------------------------------------------------------------------------------
	
	public AuctionDAO getAuctionDAO() {
		return auctionDAO;
	}

	public void setAuctionDAO(AuctionDAO auctionDAO) {
		this.auctionDAO = auctionDAO;
	}



	@Override
	public List<Auction> findByCategory(Category category) {
		return auctionDAO.findByCategory(category) ;
	}



	@Override
	public List<Auction> findByCategoryDown(Category category, AuctionStatus status) {
		
		
		List<Auction> result = new ArrayList<Auction>() ;
		List<Category> subCategories = new ArrayList<Category>() ;
		subCategories.add(category) ;
		
		return findAuctions(subCategories, result, status) ;
		
		}
		
		
		private List<Auction> findAuctions(List<Category> cats, List<Auction> result, AuctionStatus status) {
			
			
			while (cats.size()>0) {
				
				Iterator<Category> it = cats.iterator() ;
				while (it.hasNext()) {
					Category cat = it.next() ;
					List<Auction> auctions = new ArrayList<Auction>() ;
					if (status!=null)
							auctions = auctionDAO.findByCategoryAndStatus(cat, status) ;
					else  
							auctions = auctionDAO.findByCategory(cat) ;
					
					for (Auction a:auctions) {
						if(!result.contains(a)) {
							result.add(a) ;
						}
					}
					
					it.remove(); 
					
					if (Utilities.getServices().getCategoryService().findSubCategories(cat).size()>0) {
						findAuctions(Utilities.getServices().getCategoryService().findSubCategories(cat), result, status) ;
					}
					
				}
			
		}
			
			return result ;
		
	}



		@Override
		public List<Auction> findByObserver(User user) {
			return auctionDAO.findByObserver(user) ;
		}



		@Override
		public List<Auction> findByCategoryDown(Category category) {
			List<Auction> result = new ArrayList<Auction>() ;
			List<Category> subCategories = new ArrayList<Category>() ;
			subCategories.add(category) ;
			
			return findAuctions(subCategories, result, null) ;
		}

		@Override
		public List<Auction> findLostByUser(User user) {
			// TODO Auto-generated method stub
			return auctionDAO.findLostByUser(user) ;
		}

		@Override
		public List<Auction> findWonByUser(User user) {
			// TODO Auto-generated method stub
			return auctionDAO.findWonByUser(user) ;
		}

		@Override
		public List<Auction> findOwnedByUser(User user) {
			// TODO Auto-generated method stub
			return auctionDAO.findOwnedByUser(user) ;
		}

		@Override
		public List<Auction> findActiveByUser(User user) {
			// TODO Auto-generated method stub
			return auctionDAO.findActiveByUser(user) ;
		}



	


	



	


	
	
}
