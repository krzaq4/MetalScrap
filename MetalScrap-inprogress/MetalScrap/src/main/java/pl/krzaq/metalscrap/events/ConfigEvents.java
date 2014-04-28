package pl.krzaq.metalscrap.events;

import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Checkbox;

import pl.krzaq.metalscrap.model.Config;
import pl.krzaq.metalscrap.utils.Utilities;

public class ConfigEvents {

	
	
	public void onChecked(Checkbox checkbox, AnnotateDataBinder binder){
		
		//checkbox.setChecked(!checkbox.isChecked());
		this.changeConfig(checkbox.getId(), checkbox.isChecked());
		binder.loadComponent(checkbox);
		
	}
	
	
	private void changeConfig(String key, boolean checked) {
		
		Config cfg = Utilities.getServices().getConfigService().findByKey(key) ;
		String newValue = String.valueOf(checked) ;
		cfg.setValue(newValue);
		Utilities.getServices().getConfigService().update(cfg);
		
	}
	
	
}
