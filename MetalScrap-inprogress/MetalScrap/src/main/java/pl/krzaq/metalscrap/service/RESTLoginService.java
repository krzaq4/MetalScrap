package pl.krzaq.metalscrap.service;

import org.springframework.web.servlet.view.xml.MarshallingView;

import pl.krzaq.metalscrap.utils.ResponseMessage;

public interface RESTLoginService {

	
	public ResponseMessage login(String login, String password) ;
	
}
