package pl.krzaq.metalscrap.listener;

import org.zkoss.zk.au.AuRequest;
import org.zkoss.zk.au.AuService;

public class MAuService implements AuService {

	@Override
	public boolean service(AuRequest req, boolean arg1) {
		System.out.println("AU - "+req.getCommand());
		return true;
	}

}
