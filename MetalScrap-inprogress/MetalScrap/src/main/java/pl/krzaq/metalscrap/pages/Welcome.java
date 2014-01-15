
package pl.krzaq.metalscrap.pages;

import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;

import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.model.AuctionStatus;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class Welcome extends HomePage {

	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
		// TODO Auto-generated method stub
		super.doInit(page, arg1);
		
		List<Auction> auctions = ServicesImpl.getAuctionService().findByStatus(ServicesImpl.getAuctionService().findStatusByCode(AuctionStatus.STATUS_STARTED)) ;
		page.setAttribute("categoryAuctions", auctions) ;
		page.setAttribute("selectedCategory", null) ;
		
	}

	@Override
	public void doAfterCompose(Page page, Component[] arg1) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(page, arg1);
	}

	@Override
	public boolean doCatch(Throwable arg0) throws Exception {
		// TODO Auto-generated method stub
		return super.doCatch(arg0);
	}

	@Override
	public void doFinally() throws Exception {
		// TODO Auto-generated method stub
		super.doFinally();
	}

	
	
	
	
	
	
	
	
}