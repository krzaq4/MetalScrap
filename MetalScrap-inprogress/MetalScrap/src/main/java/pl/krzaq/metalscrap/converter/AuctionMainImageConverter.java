package pl.krzaq.metalscrap.converter;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import pl.krzaq.metalscrap.model.AttachementFile;
import pl.krzaq.metalscrap.model.Auction;
import pl.krzaq.metalscrap.utils.Utilities;

public class AuctionMainImageConverter implements TypeConverter {

	@Value("${image.noimagepath}")
	private String noImagePath ;
	
	
	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
		Auction a = (Auction) arg0 ;
		AttachementFile af = Utilities.getServices().getAttachementFileService().findAuctionMain(a) ;
		AImage aimg = null ;
		try{
		
		if (af!=null) {
			File file = new File(af.getPath()) ;
			aimg = new AImage(file) ;
		} else {
			File file = new File("c:\\temp\\noimage.png") ;
			aimg = new AImage(file) ;
			
		}
		
		} catch(IOException ex) {
			
		}
		return aimg ;
	}

}
