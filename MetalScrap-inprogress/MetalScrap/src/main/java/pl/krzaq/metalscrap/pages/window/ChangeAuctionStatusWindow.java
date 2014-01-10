package pl.krzaq.metalscrap.pages.window;

import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import pl.krzaq.metalscrap.model.AuctionStatus;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class ChangeAuctionStatusWindow implements Initiator {

	@Override
	public void doInit(Page arg0, Map<String, Object> arg1) throws Exception {

		List<AuctionStatus> statuses = ServicesImpl.getAuctionService().findAllStatuses() ;
		arg0.setAttribute("statuses", statuses) ;
		
	}

}
