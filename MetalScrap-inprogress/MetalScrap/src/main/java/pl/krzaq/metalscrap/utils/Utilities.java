package pl.krzaq.metalscrap.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.impl.XulElement;

import pl.krzaq.metalscrap.model.User;

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
	
}
