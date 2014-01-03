package pl.krzaq.metalscrap.events;

import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Checkbox;

import pl.krzaq.metalscrap.model.Config;
import pl.krzaq.metalscrap.service.impl.ServicesImpl;

public class ConfigEvents {

	
	
	public void onChecked(Checkbox checkbox, AnnotateDataBinder binder){
		
		//checkbox.setChecked(!checkbox.isChecked());
		this.changeConfig(checkbox.getId(), checkbox.isChecked());
		binder.loadComponent(checkbox);
		
	}
	
	
	private void changeConfig(String key, boolean checked) {
		
		Config cfg = ServicesImpl.getConfigService().findByKey(key) ;
		String newValue = String.valueOf(checked) ;
		cfg.setValue(newValue);
		ServicesImpl.getConfigService().update(cfg);
		
	}
	
	
}
