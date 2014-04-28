package pl.krzaq.metalscrap.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.krzaq.metalscrap.model.generalization.Translatable;

public class LangUtils {

	
	public static boolean translateInto(String lang, Translatable source) {
		
		List<Translatable> tmp = new ArrayList<Translatable>() ;
		tmp.add(source) ;
		createTranslations("de", tmp, new ArrayList()) ;
		return true ;
	}
	
public static Collection translateInto(String lang, Collection source, List ar) {
		
	for (Object object: source) {
	
	
		 createTranslations("de", source, ar) ;
	}
		return ar ;
	}
	
	
	private static Collection<Translatable> createTranslations(String lang, Collection<Translatable> sources, List res) {
		
		
		
		for (Translatable object: sources) {
		
			Method[] methods = object.getClass().getMethods() ;
			
			try {
				Object target = Class.forName(object.getClass().getCanonicalName()).newInstance() ;
				System.out.println("Target: "+target.toString()) ;
				
				for (Method method: methods) {
				System.out.println("Method: "+method.getName());
					if( method.getReturnType()==(List.class)) {
						System.out.println("return collection");
						String setter = "set".concat(method.getName().substring(3, method.getName().length())) ;
						Method setterMethod;
						try {
							Collection cl = (Collection) method.invoke(object, null) ;
							setterMethod = target.getClass().getMethod(setter, method.getReturnType());
							if(cl!=null)
								setterMethod.invoke(target, createTranslations(lang, cl , (List)cl)) ;
							((Translatable) target).setLang(lang) ;
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							
						}
						
						
					} else {
						if(method.getName().substring(0, 3).equals("get")) {
							System.out.println("Does not return collection");
							String setter = "set".concat(method.getName().substring(3, method.getName().length())) ;
							
							Object o =  method.invoke(object, null) ;
							Method setterMethod;
							try {
								setterMethod = target.getClass().getMethod(setter, method.getReturnType());
								setterMethod.invoke(target, o ) ;
								((Translatable) target).setLang(lang) ;
							} catch (NoSuchMethodException e) {
								// TODO Auto-generated catch block
								
							}
							
							
						}
						
					}
					
				}
				res.add(target) ;
				
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return res ;
	}
	
	
	/*private boolean isAvailableIn(String lang) {
		
	}*/
	
}
