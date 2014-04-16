package pl.krzaq.metalscrap.utils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;

import pl.krzaq.metalscrap.model.Category;
import pl.krzaq.metalscrap.model.Property;
import pl.krzaq.metalscrap.model.PropertyAttribute;
import pl.krzaq.metalscrap.model.PropertyAttributeValue;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class Utilities {

	public static String HASH_METHOD_MD5 = "md5" ;
	public static String HASH_METHOD_SHA512 = "sha" ;
	static Logger log = LoggerFactory.getLogger(Utilities.class) ;
	
	
	public static String hash(String hashMethod, String input) throws NoSuchAlgorithmException {
		String result = null ;
		if (hashMethod.equals(HASH_METHOD_MD5)) {
			result = md5(input) ;
		}
		
		if (hashMethod.equals(HASH_METHOD_SHA512)) {
			result = sha(input) ;
		}
		
		return result ;
	}
	
	
	public static String md5(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("MD5");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
	
	public static String sha(String input) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA-512");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
		
		
	}
	
	
	public static String generateToken(User user) throws NoSuchAlgorithmException {
		
		Date today = new Date() ;
		Calendar calendar = Calendar.getInstance() ;
		calendar.setTime(today) ;
		
		int day = calendar.get(Calendar.DAY_OF_MONTH) ;
		int month = calendar.get(Calendar.MONTH) ;
		int year = calendar.get(Calendar.YEAR) ;
		
		String pass = user.getPassword();
		
		int val = (day*10)+(month*100)+(year*10) ;
		
		String tok = String.valueOf(val).concat(pass) ;
		String token = md5(tok) ;
		return token ;
	}
	
	public static void bind(IdSpace cmp, AnnotateDataBinder binder) {
		Clients.showBusy(null);
		log.info("Bindings for : ["+cmp.getClass().getCanonicalName()+"]");
		if (cmp instanceof Component){			
			bindComponents(((Component) cmp).getChildren(), binder) ;
		}
		Clients.clearBusy();
	}
	
	private static void bindComponents(List<Component> list, AnnotateDataBinder binder) {
		
		for(Component c:list) {
			
			
				binder.loadComponent(c);
				//log.info("Found binding : ["+c.getClass().getCanonicalName()+"] ["+c.getId()+"]") ;
			
			
			if (c.getChildren()!=null && c.getChildren().size()>0) {
				bindComponents(c.getChildren(), binder) ;
			}
			
			
		}
		
	}
	
	public static boolean validate(Component c, boolean children) {
		boolean valid = true ;
		
		try {
			
			if (c instanceof Textbox) {
				((InputElement)c).getText() ;
			}
			else
			if (c instanceof Checkbox) {
				((Checkbox)c).isChecked() ;
			}
			else
			if (c instanceof Listbox) {
				if(((Listbox)c).getMold().equals("select")) {
					valid = valid && ( ((Listbox)c).getSelectedItem()!=null || ((Listbox)c).getSelectedIndex()!=-1 );
					if(!valid) {
						throw new WrongValueException(c, Labels.getLabel("error.itemnotselected")) ;
					}
				}	
			}
			else
			if (c instanceof Combobox) {
				valid = valid && ( ((Combobox)c).getSelectedItem()!=null || ((Combobox)c).getSelectedIndex()!=-1) ;
				if(!valid) {
					throw new WrongValueException(c, Labels.getLabel("error.itemnotselected")) ;
				}
			}
			
			if (children) {
				valid = valid && validate(c.getChildren()) ;
			}
			
			
		} catch(WrongValueException wex) {
			valid = false ;
			throw wex ;
		}
		
		return valid ;
		
		
	}
	
	private static boolean validate(List<Component> list) {
		boolean valid = true ;
		
		for (Component c:list) {
			try {
			
				if (c instanceof Textbox) {
					((InputElement)c).getText() ;
				}
				else
					if (c instanceof Checkbox) {
						((Checkbox)c).isChecked() ;
					}
				else
					if (c instanceof Listbox) {
						if(((Listbox)c).getMold().equals("select")) {
							valid = valid && ( ((Listbox)c).getSelectedItem()!=null || ((Listbox)c).getSelectedIndex()!=-1 );
							if(!valid) {
								throw new WrongValueException(c, Labels.getLabel("error.itemnotselected")) ;
							}
						}	
					}
				else
					if (c instanceof Combobox) {
						valid = valid && ( ((Combobox)c).getSelectedItem()!=null || ((Combobox)c).getSelectedIndex()!=-1) ;
						if(!valid) {
							throw new WrongValueException(c, Labels.getLabel("error.itemnotselected")) ;
						}
					}
			
			if(c.getChildren()!=null && c.getChildren().size()>0) {
				valid = valid && validate(c.getChildren()) ;
			}
			
		} catch(WrongValueException wex) {
			valid = false ;
			throw wex ;
			
		}

		}
		
		return valid ;
		
	}
	
	
	public static List<Property> populateProperties(Component c, Category cat) {
		
		List<Property> result = new ArrayList<Property>() ;
		List<Component> list = c.getChildren() ;
		
		Locale locale = (Locale) Executions.getCurrent().getSession().getAttribute(Attributes.PREFERRED_LOCALE) ;
		
		for (String lang:ServicesImpl.getLangLabelService().findAllLangs()) {
			
		for (Component cc:list) {
			
			Property newProp = new Property() ;
			String equalIdent = "" ;
			Property equalProp = null ; 
			List<PropertyAttribute> attrs = new ArrayList<PropertyAttribute>() ;
			
			if(cc instanceof Vbox){    // Property
				
				List<Component> innerList = cc.getChildren() ;
				
				for (Component at: innerList) {
					
					PropertyAttribute newAttr = new PropertyAttribute() ;
					newAttr.setProperty(newProp);
					
					if (at instanceof Label) {
						equalIdent = ( (Label) cc).getId() ;
						equalProp = ServicesImpl.getPropertyService().findEqual(equalIdent, lang) ;
						newProp.setName( equalProp.getName() );
						//Property inLangProp = ServicesImpl.getPropertyService().findEqual(equalIdentifier, lang)
					}
					else
					if(at instanceof Hbox) {   // PropertyAttribute
						
						List<Component> attrList = at.getChildren() ;
						int nextAttr = 0 ;
						for (Component pc:attrList) {
							PropertyAttribute equalAttr = equalProp.getAttributes().get(nextAttr) ;
							if(pc instanceof Label) {
								
								newAttr.setName(equalAttr.getName()) ;
							}
							else
							if(pc instanceof Textbox) {
								
								newAttr.setType(PropertyAttribute.TYPE_TEXT);
								PropertyAttributeValue val = new PropertyAttributeValue() ;
								val.setAttribute(newAttr);
								val.setValue(((Textbox)pc).getValue());
								List<PropertyAttributeValue> vals = new ArrayList<PropertyAttributeValue>() ;
								vals.add(val) ;
								newAttr.setValues(vals);
								attrs.add(newAttr) ;
								
							}
							else
							if(pc instanceof Datebox) {
								
								newAttr.setType(PropertyAttribute.TYPE_DATE);
								
								PropertyAttributeValue val = new PropertyAttributeValue() ;
								val.setAttribute(newAttr);
								DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss") ;
								
								val.setValue(df.format(((Datebox)pc).getValue()));
								List<PropertyAttributeValue> vals = new ArrayList<PropertyAttributeValue>() ;
								vals.add(val) ;
								newAttr.setValues(vals);
								attrs.add(newAttr) ;
								
							}
							else
							if(pc instanceof Decimalbox) {
								newAttr.setType(PropertyAttribute.TYPE_DECIMAL);
								PropertyAttributeValue val = new PropertyAttributeValue() ;
								val.setAttribute(newAttr);
								BigDecimal v = ((Decimalbox)pc).getValue() ;
								v.setScale(2) ;
								
								val.setValue(v.toString());
								List<PropertyAttributeValue> vals = new ArrayList<PropertyAttributeValue>() ;
								vals.add(val) ;
								newAttr.setValues(vals);
								attrs.add(newAttr) ;
							}
							else
							if(pc instanceof Listbox) {
								
								newAttr.setType(PropertyAttribute.TYPE_SELECT);
								PropertyAttributeValue val = new PropertyAttributeValue() ;
								val.setAttribute(newAttr);
								val.setValue(((PropertyAttributeValue)((Listbox)pc).getSelectedItem().getValue()).getValue());
								List<PropertyAttributeValue> vals = new ArrayList<PropertyAttributeValue>() ;
								vals.add(val) ;
								newAttr.setValues(vals);
								attrs.add(newAttr) ;
								
							}
							else
							if(pc instanceof Vbox) {
								
								newAttr.setType(PropertyAttribute.TYPE_MULTISELECT);
								List<PropertyAttributeValue> vals = new ArrayList<PropertyAttributeValue>() ;
								
								List<Component> checks = pc.getChildren() ;
								
								for (Component check:checks) {
									if(check instanceof Checkbox) {
										
										if( ((Checkbox)check).isChecked()) {
											PropertyAttributeValue val = new PropertyAttributeValue() ;
											val.setAttribute(newAttr);
											val.setValue( ((Checkbox)check).getLabel()) ;
											vals.add(val) ;
										}
										
										
									}
									
								}
								
								
								newAttr.setValues(vals);
								
								attrs.add(newAttr) ;
							}
							
							
						}
						
						
						
						
						
					}
					
					
					
				}
				
				int component = 0 ;
				
				
					
				
				
			}
			
			newProp.setAttributes(attrs);
			
			result.add(newProp) ;
			
		}
	}
		return result ;
		
	}
	
	
	public static String getUserDataFolder(User user) {
		String dataDir = System.getProperty("jboss.server.data.dir") ;
		String usersDir = dataDir.concat("\\platform\\users\\").concat(user.getLogin()) ;
		return usersDir ;
	}
	
	public static String getUserDataFolder(String userName) {
		String dataDir = System.getProperty("jboss.server.data.dir") ;
		String usersDir = dataDir.concat("\\platform\\users\\").concat(userName) ;
		return usersDir ;
	}
	
	
public static Image scaleImage(Image image, int maxWidth, int maxHeight) {
		
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
	
}
