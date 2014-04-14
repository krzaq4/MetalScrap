package pl.krzaq.metalscrap.test;

import static org.junit.Assert.*;

import org.junit.Test;

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
	
}
