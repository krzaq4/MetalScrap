package pl.krzaq.metalscrap.events;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import pl.krzaq.metalscrap.model.AttachementFile;
import pl.krzaq.metalscrap.model.CommodityType;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;



public class FormEvents {

	@Value("${upload.path}")
	private String uploadPath ;
	
	
	public void uploadPhoto(Media media, Grid grid, AnnotateDataBinder binder) {
		
		
		
		
		String nextName = ServicesImpl.getAttachementFileService().getNextName() ;
		File file = new File(uploadPath+"/"+nextName+"."+media.getFormat()) ;
		
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(file);
			fo.write(media.getByteData());
			AttachementFile af = new AttachementFile() ;
			af.setName(nextName);
			af.setPath(uploadPath+nextName+"."+media.getFormat());
			af.setMain(false);
			ServicesImpl.getAttachementFileService().save(af);
			
			
			Image img = new Image() ;
			img.setContent(((org.zkoss.image.Image)media));
			img.setWidth("50%");
			img.setHeight("50%");
			List<Image> files = new ArrayList<Image>() ;
			HttpSession ses = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
			if( ses.getAttribute("files")!=null ){
				files = (ArrayList<Image>) ses.getAttribute("files") ;
			}
			
			files.add(img) ;
			
			
			ses.setAttribute("files", files);
			
			
			
			Row row = new Row() ;
			row.appendChild(img) ;
			grid.getRows().appendChild(row) ;
			binder.loadComponent(grid);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void showAuctionPositions(Component a, Component b) {
		
		a.setVisible(false) ;
		b.setVisible(true) ;
		
	}
	
	public void showAuctionParameters(Component a, Component b) {
		
		b.setVisible(false) ;
		a.setVisible(true) ;
	}
	
	
	public void openAddPositionWindow(Component c) {
		
		
		Window window = (Window)Executions.createComponents(
                "/secured/auctions/windows/add_position.zul", null,null);
       
		window.setPage(c.getPage());
        window.doPopup();
		
	}
	
	public void clearForms(Component c) {
		
		
		if (c.getChildren().size()>0) {
			for (Component chk:c.getChildren()) {
				
				if(chk instanceof Textbox) {
					((Textbox)chk).setValue("");
				} else {
					clearForms(chk) ;
				}
				
			}
			
			
			
		}
		
		
	}
	
}
