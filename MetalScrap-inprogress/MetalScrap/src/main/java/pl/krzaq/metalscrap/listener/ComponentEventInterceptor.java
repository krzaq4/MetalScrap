package pl.krzaq.metalscrap.listener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;




import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.EventInterceptor;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.Binding;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Textbox;


public class ComponentEventInterceptor implements EventInterceptor {

	@Override
	public void afterProcessEvent(Event arg0) {
		
		
		if (arg0.getName().equalsIgnoreCase("onLoadOnSave") || (arg0.getTarget() instanceof CKeditor && arg0.getName().equalsIgnoreCase("onChange"))) {
			AnnotateDataBinder binder = (AnnotateDataBinder) arg0.getTarget().getPage().getAttribute("binder") ;
			
			HttpSession ses = (HttpSession) Executions.getCurrent().getSession().getNativeSession() ;
			
			String pref = arg0.getPage().getId() ;
			
			 
			
			Component cmp = arg0.getTarget() ;
			String exp="" ;
			if(binder.existBinding(cmp, "value"))
				exp =binder.getBinding(cmp, "value").getExpression() ;
			if(binder.existBinding(cmp, "checked"))
				exp =binder.getBinding(cmp, "checked").getExpression() ;
			if(binder.existBinding(cmp, "selectedItem"))
				exp =binder.getBinding(cmp, "selectedItem").getExpression() ;
			
			
			ses.setAttribute(pref+exp.substring(0, exp.indexOf(".")), arg0.getTarget().getPage().getAttribute(exp.substring(0, exp.indexOf("."))));
			
			System.out.println("onBlur  "+arg0.getPage().getTitle()+" | "+arg0.getTarget().getClass().getCanonicalName());
			
		}

	}

	@Override
	public Event beforePostEvent(Event arg0) {
		// TODO Auto-generated method stub
		return arg0 ;
	}

	@Override
	public Event beforeProcessEvent(Event arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public Event beforeSendEvent(Event arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

}
