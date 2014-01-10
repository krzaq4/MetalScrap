package pl.krzaq.metalscrap.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zul.Image;

import pl.krzaq.metalscrap.model.AttachementFile;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class AuctionView extends HomePage {

	@Override
	public void doInit(Page page, Map<String, Object> arg1) throws Exception {
		
		super.doInit(page, arg1);
		
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest() ;
		
		if (request.getParameter("id")!=null) {
			
			Long id = Long.valueOf( (String) request.getParameter("id")) ;
			Auction auction = ServicesImpl.getAuctionService().findWithCollection(id) ;
			
			List<AttachementFile> attachements = ServicesImpl.getAttachementFileService().findByAuction(auction) ;
			
			List<Image> images = new ArrayList<Image>() ;
			
			for (AttachementFile attachement:attachements) {
				
				File file = new File(attachement.getPath()) ;
				AImage image = new AImage(file) ;
				Image img = new Image() ;
				img.setContent(image);
				images.add(img) ;
			}
			
			page.setAttribute("images", images) ;
			
		}
		
	}

	@Override
	public void doAfterCompose(Page page, Component[] arg1) throws Exception {
		
		super.doAfterCompose(page, arg1);
		
		
		
	}

	@Override
	public boolean doCatch(Throwable arg0) throws Exception {
		// TODO Auto-generated method stub
		return false ;
	}

	@Override
	public void doFinally() throws Exception {
		// TODO Auto-generated method stub
		super.doFinally();
	}

	
	
	
	
}
