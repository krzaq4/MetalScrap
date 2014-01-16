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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
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
	
	
	public void uploadPhoto(Media media, final Listbox grid, final AnnotateDataBinder binder) {
		
		final Page page = grid.getPage() ;
		String format = media.getFormat() ;
		if (format !=null && !format.equalsIgnoreCase("jpg") && !format.equalsIgnoreCase("png") && !format.equalsIgnoreCase("gif") && !format.equalsIgnoreCase("jpeg")){
			
			Messagebox.show("Nieobs³ugiwany format pliku. \n Obs³ugiwane formaty to .jpeg, .jpg, .gif, .png") ;
			
		} else {
		
		final Div overflow = (Div)page.getFellow("galleryOverflow") ;
		
		String nextName = ServicesImpl.getAttachementFileService().getNextName() ;
		File file = new File(uploadPath+"/"+nextName+"."+media.getFormat()) ;
		
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(file);
			fo.write(media.getByteData());
			final AttachementFile af = new AttachementFile() ;
			af.setName(nextName);
			af.setPath(uploadPath+nextName+"."+media.getFormat());
			af.setMain(false);
			ServicesImpl.getAttachementFileService().save(af);
			
			
			Image img = new Image() ;
			
			img.setContent(((org.zkoss.image.Image)media));
			
			
			final Image orig = (Image) img.clone() ;
			
			img.addEventListener("onClick", new EventListener<Event>(){
				
				@Override
				public void onEvent(Event event) throws Exception {
					
					if(event.getName().equalsIgnoreCase("onClick")) {
						
						Image main = (Image) event.getTarget() ;
						overflow.removeChild(overflow.getFellow("overflowImage"));
						
						orig.setId("overflowImage") ;
						overflow.appendChild(orig) ;
						overflow.setVisible(true) ;
					}
					
				}
				
			}) ;
			
			
			img.setId(nextName);
			img.setWidth("50%");
			img.setHeight("50%");
			List<AttachementFile> files = new ArrayList<AttachementFile>() ;
			final HttpSession ses = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
			if( ses.getAttribute("files")!=null ){
				files = (ArrayList<AttachementFile>) ses.getAttribute("files") ;
			}
			
			files.add(af) ;
			
			
			ses.setAttribute("files", files);
			
			
			final Listitem li = new Listitem() ;
			Listcell lc = new Listcell() ;
			Listcell lc2 = new Listcell() ;
			
			Button delImage = new Button("Usuñ") ;
			delImage.addEventListener("onClick", new EventListener<Event>(){

				@Override
				public void onEvent(Event event) throws Exception {
					grid.getItems().remove(li) ;
					List<AttachementFile> afiles = (ArrayList<AttachementFile>) ses.getAttribute("files") ;
					if (afiles!=null && afiles.size()>0) {
						afiles.remove(af) ;
						ses.setAttribute("files", afiles);
						binder.loadComponent(grid);
					}
					
				}
				
			}) ;
			
			lc2.appendChild(delImage) ;
			lc.appendChild(img) ; 
			li.appendChild(lc) ;
			li.appendChild(lc2) ;
			//Row row = new Row() ;
			//row.appendChild(img) ;
			grid.getItems().add(li) ; //.getRows().appendChild(row) ;
			binder.loadComponent(grid);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
