package pl.krzaq.metalscrap.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;


import pl.krzaq.metalscrap.service.impl.ServicesImpl;

@ApplicationPath("/api")
public class RestApplication extends Application {

	
	
	@Path("/auctionList")
	@Produces("application/json")
	@GET
	public List<Integer> getAuctionsIds(Integer status, Date from, Date to) {
		
		return ServicesImpl.getAuctionService().findIds(ServicesImpl.getAuctionService().findStatusByCode(status), from, to) ;
		
		
	}
	
	
	
}
