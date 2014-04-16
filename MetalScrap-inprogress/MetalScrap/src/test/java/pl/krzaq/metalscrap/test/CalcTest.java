package pl.krzaq.metalscrap.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import pl.krzaq.metalscrap.model.DeliveryType;
import pl.krzaq.metalscrap.model.Property;
import pl.krzaq.metalscrap.model.PropertyAttribute;
import pl.krzaq.metalscrap.service.DeliveryTypeService;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;
import pl.krzaq.metalscrap.utils.LangUtils;


public class CalcTest {

	@Test
	public void testCashTest() {
		Double cash = 23000.00 ;
		Double was = cash ;
		Double year = 0.13 ;
		Double months = 12.00 ;
		Double month = year/months ;
		Double pay=300.00 ;
		int mnts = 36 ;
		
		while(mnts>0) {
			
			System.out.println(mnts+".  capital= "+cash);
			//System.out.println("  interest= "+cash*month);
			//cash=(cash-(pay-(cash*(month)))) ;//*(1+month);
			cash=(cash-pay) ;//*(1+month);
			/*if(mnts % 12 == 0 && mnts!=36) {
				System.out.println("Yearly interest = "+cash*year);
				cash = cash+ (cash * year) ;
			}*/
			
			mnts-- ;
		}
		
		cash=cash+(cash*(year*3)) ;
		
		System.out.println("WAS:   "+was);
		System.out.println("PAYED: "+pay*36);
		System.out.println("LEFT:  "+cash);
	}

	@Test
	public void testChechRegexTest(){
		
		String tt="45-429";
		String regex = "[0-9]{2}-{1}[0-9]{3}";
		System.out.println(tt+" matches "+regex+"  :"+tt.matches(regex));
	}
	
	
	@Test
	public void testLangCloneTest() {
		
		
		
		/*List<DeliveryType> types = new ArrayList<DeliveryType>() ;
		DeliveryType dt1 = new DeliveryType() ;
		dt1.setCode(1);
		dt1.setLang("pl");
		dt1.setName("name");
		
		DeliveryType dt2 = new DeliveryType() ;
		dt2.setCode(1);
		dt2.setLang("pl");
		dt2.setName("name");
		
		DeliveryType dt3 = new DeliveryType() ;
		dt3.setCode(1);
		dt3.setLang("pl");
		dt3.setName("name");
		
		types.add(dt1) ;
		types.add(dt2) ;
		types.add(dt3) ;
		
		System.out.println("");
		
		
		List rr = new ArrayList() ;
		LangUtils.translateInto("de", types, rr) ;
		
		for (Object o: rr) {
			System.out.println("Object:  "+o.toString());
			System.out.println("Object:  "+((DeliveryType)o).getLang());
		}
		
		
		*/
		List dd = new ArrayList() ;
		Property p1 = new Property() ;
		p1.setDescription("desc");
		p1.setExposed(true);
		p1.setLang("pl");
		p1.setName("nam1");
		
		List<PropertyAttribute> attrs1 = new ArrayList<PropertyAttribute>() ;
		PropertyAttribute pa1 = new PropertyAttribute() ;
		pa1.setLang("pl");
		pa1.setProperty(p1);
		pa1.setType(1);
		
		attrs1.add(pa1) ;
		
		PropertyAttribute pa2 = new PropertyAttribute() ;
		pa2.setLang("pl");
		pa2.setProperty(p1);
		pa2.setType(1);
		
		attrs1.add(pa2) ;
		
		PropertyAttribute pa3 = new PropertyAttribute() ;
		pa3.setLang("pl");
		pa3.setProperty(p1);
		pa3.setType(1);
		
		attrs1.add(pa3) ;
		
		p1.setAttributes(attrs1);
		
		
		Property p2 = new Property() ;
		p2.setDescription("desc");
		p2.setExposed(true);
		p2.setLang("pl");
		p2.setName("nam2");
		
		List<PropertyAttribute> attrs2 = new ArrayList<PropertyAttribute>() ;
		pa1 = new PropertyAttribute() ;
		pa1.setLang("pl");
		pa1.setProperty(p1);
		pa1.setType(1);
		
		attrs2.add(pa1) ;
		
		pa2 = new PropertyAttribute() ;
		pa2.setLang("pl");
		pa2.setProperty(p1);
		pa2.setType(1);
		
		attrs2.add(pa2) ;
		
		pa3 = new PropertyAttribute() ;
		pa3.setLang("pl");
		pa3.setProperty(p1);
		pa3.setType(1);
		
		attrs2.add(pa3) ;
		
		
		p2.setAttributes(attrs1);
		
		
		dd.add(p1) ;
		dd.add(p2) ;
		
		
		List rr = new ArrayList() ;
		LangUtils.translateInto("de", dd, rr) ;
		
		System.out.println(" --------------------------------- ");
		System.out.println("");
		
		for (Object o: rr) {
			System.out.println("Object:  "+o.toString());
			System.out.println("Lang:  "+((Property)o).getLang());
			System.out.println("Attrs:  "+((Property)o).getAttributes());
		}
		
		System.out.println(" --------------------------------- ");
		
	}
	
}
