package pl.krzaq.metalscrap.components;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;

public class GalleryDiv extends Div {

	private List<Image> model ;
	
	private List<Image> thumbs ;
	private List<Image> mains ;
	private List<Image> overflowThumbs ;
	private List<Image> overflows = new ArrayList<Image>();
	
	private Button previous ;
	private Button next ;
	
	private final Div mainView = new Div();
	private final Div thumbView = new Div();
	private final Div overflow = new Div() ;
	private final Div overflowThumbView = new Div() ;
	
	private int screenWidth = 0;
	private int screenHeight = 0;
	
	public void onCreate() {
		
		this.getPage().getFirstRoot().addEventListener("onClientInfo", new EventListener<ClientInfoEvent>(){

			

			@Override
			public void onEvent(ClientInfoEvent event) throws Exception {
				screenWidth = event.getDesktopWidth();
				screenHeight = event.getDesktopHeight();
				overflows = scaleOverflows(model,screenWidth-(screenWidth/6),screenHeight-(screenHeight/6)) ;
			}
			
			
		}) ;
		
		this.thumbs = scaleThumbs(model, 90) ;
		this.mains = scaleMains(model, 590, 450) ;
		this.overflowThumbs = scaleOverflowThumbs(model,0,90) ;
		
		
		prepareView() ;
		
		
		
		
		
	}
	
	private void prepareView() {
		next = new Button(">") ;
		next.setWidth("30px");
		next.setHeight("450px");
		next.setSclass("galleryNext");
		
		next.addEventListener("onMouseOver", new EventListener<Event>(){

			@Override
			public void onEvent(Event event) throws Exception {
				
				if (event.getName().equalsIgnoreCase("onMouseOver")) {
					
					((Button)event.getTarget()).setStyle("opacity:0.8") ;
					
				} 
			}
			
			
		}) ;
		
		next.addEventListener("onMouseOut", new EventListener<Event>(){

			@Override
			public void onEvent(Event event) throws Exception {
				
				if (event.getName().equalsIgnoreCase("onMouseOut")) {
					
					((Button)event.getTarget()).setStyle("opacity:0.3") ;
					
				}
				
			}
			
			
			
		}) ;
		
		previous = new Button("<") ;
		previous.setWidth("30px");
		previous.setHeight("450px");
		previous.setSclass("galleryPrevious");
		
		previous.addEventListener("onMouseOver", new EventListener<Event>(){

			@Override
			public void onEvent(Event event) throws Exception {
				
				if (event.getName().equalsIgnoreCase("onMouseOver")) {
					
					((Button)event.getTarget()).setStyle("opacity:0.8") ;
					
				}
					
			}
			
			
		}) ;
		
		previous.addEventListener("onMouseOut", new EventListener<Event>(){

			@Override
			public void onEvent(Event event) throws Exception {
				
				if (event.getName().equalsIgnoreCase("onMouseOut")) {
					
					((Button)event.getTarget()).setStyle("opacity:0.3") ;
					
				}
				
			}
			
			
			
		}) ;
		
		
		this.setSclass("galleryView");
		
		
		mainView.setWidth(this.getWidth());
		mainView.setHeight("450px");
		mainView.setSclass("mainView");
		
		overflow.setSclass("galleryOverflow");
		overflow.setVisible(false) ;
		
		overflow.setPage(this.getPage());
		
		Button closeOverflow = new Button("X") ;
		closeOverflow.setSclass("overflowClose");
		closeOverflow.addEventListener("onClick", new EventListener<Event>(){

			@Override
			public void onEvent(Event event) throws Exception {
				
				overflow.setVisible(false) ;
				
			}
			
			
			
		}) ;
		
		overflow.appendChild(closeOverflow) ;
		Image overflowImage = new Image();
		overflowImage.setId("overflowImage");
		overflow.appendChild(overflowImage) ;
		
		overflowThumbView.setSclass("overflowThumbs");
		overflowThumbView.setHeight("100px");
		overflowThumbView.setWidth(String.valueOf(this.screenWidth-10)+"px") ;
		
		thumbView.setWidth(this.getWidth());
		thumbView.setHeight("100px");
		thumbView.setSclass("thumbView");
		
		if (model.size()>0) {
			Image im = model.get(0) ;
			im = scaleImage(im, 590, 450) ;
			mainView.appendChild(im) ;
		
		for(Image thumb:thumbs) {
			thumb.setSclass("thumbImg");
			thumbView.appendChild(thumb) ;
			
		}
		
		for(Image thumb:overflowThumbs) {
			thumb.setSclass("thumbImg");
			overflowThumbView.appendChild(thumb) ;
		}
		
		}
		
		
		overflow.appendChild(overflowThumbView) ;
		this.appendChild(mainView) ;
		this.appendChild(thumbView) ;
		this.appendChild(next) ;
		this.appendChild(previous) ;
		
	}
	
	private List<Image> scaleThumbs(List<Image> images, int scaleHeight){
		
		List<Image> result = new ArrayList<Image>() ;
		
		for (Image img:images){
			
			Image thumb = scaleImage(img,0,scaleHeight) ;
				
			thumb.addEventListener("onClick", new ThumbClickListener()) ;
			
			result.add(thumb) ;
		}
		
		return result ;
		
	}
	
	
	
private List<Image> scaleMains(List<Image> images, int scaleWidth, int scaleHeight){
		
		List<Image> result = new ArrayList<Image>() ;
		
		for (Image img:images){
			
			Image thumb = scaleImage(img,scaleWidth,scaleHeight) ;
				
			thumb.addEventListener("onClick", new MainClickListener()) ;
			
			
			result.add(thumb) ;
		}
		
		return result ;
		
	}

private List<Image> scaleOverflowThumbs(List<Image> images, int scaleWidth, int scaleHeight){
	
	List<Image> result = new ArrayList<Image>() ;
	
	for (Image img:images){
		
		Image thumb = scaleImage(img,scaleWidth,scaleHeight) ;
			
		thumb.addEventListener("onClick", new OverflowThumbClickListener()) ;
		
		
		result.add(thumb) ;
	}
	
	return result ;
	
}
	

private List<Image> scaleOverflows(List<Image> images, int scaleWidth, int scaleHeight){
	
	List<Image> result = new ArrayList<Image>() ;
	
	for (Image img:images){
		
		Image thumb = scaleImage(img,scaleWidth,scaleHeight) ;
			
	//	thumb.addEventListener("onClick", new OverflowThumbClickListener()) ;
		
		
		result.add(thumb) ;
	}
	
	return result ;
	
}
	
	
	private Image scaleImage(Image image, int maxWidth, int maxHeight) {
		
		float maxRatio = (float) maxWidth / (float) maxHeight ;
		float widthRatio = (float) maxWidth / (float) image.getContent().getWidth() ;
		float heightRatio = (float) maxHeight / (float) image.getContent().getHeight() ;
		
		float scale = 100 ;
		
		if (widthRatio<heightRatio || widthRatio==0) {
			
			scale = heightRatio ;
			
		} else
		if (widthRatio>heightRatio || heightRatio==0){
			scale = widthRatio ;
		}
		
		Image img = (Image)image.clone() ;
		int w = img.getContent().getWidth() ;
		int h = img.getContent().getHeight() ;
		int newWidth = Math.round((float)w*(float)scale) ;
		int newHeight = Math.round((float)h*(float)scale) ;
		
		
		
		img.setWidth(String.valueOf(Math.round((float)w*(float)scale))+"px");
		img.setHeight(String.valueOf(Math.round((float)h*(float)scale))+"px");
		
		if (newWidth>maxWidth && maxWidth>0) {
			img = scaleImage(img, maxWidth,0) ;
		}
		
		if(newHeight>maxHeight && maxHeight>0) {
			img = scaleImage(img, 0, maxHeight) ;
		}
		
		return img ;
		
	}
	
	
	public List<Image> getModel() {
		return model;
	}

	public void setModel(List<Image> model) {
		this.model = model;
	}






	private class ThumbClickListener implements EventListener<Event> {

		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equalsIgnoreCase("onClick")) {
				
				Image thumb = (Image) event.getTarget() ;
				mainView.getChildren().clear(); 
				mainView.appendChild(mains.get(thumbs.indexOf(thumb))) ;
				
			}
			
			
		}
		
		
	}
	
	private class MainClickListener implements EventListener<Event>{

		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equalsIgnoreCase("onClick")) {
				
				Image main = (Image) event.getTarget() ;
				overflow.removeChild(overflow.getFellow("overflowImage"));
				Image overflowImage = overflows.get(mains.indexOf(main));
				overflowImage.setId("overflowImage") ;
				overflow.appendChild(overflowImage) ;
				overflow.setVisible(true) ;
			}
			
		}
		
		
		
	}
	
	private class OverflowThumbClickListener implements EventListener<Event>{

		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equalsIgnoreCase("onClick")) {
				
				Image main = (Image) event.getTarget() ;
				overflow.removeChild(overflow.getFellow("overflowImage"));
				
				Image overflowImage = overflows.get(overflowThumbs.indexOf(main));
				overflowImage.setId("overflowImage") ;
				overflow.appendChild(overflowImage) ;
				//overflow.setVisible(true) ;
			}
			
		}
		
		
		
	}
	
	
}
