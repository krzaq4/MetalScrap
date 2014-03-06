package pl.krzaq.metalscrap.service;

import java.util.List;
import java.util.Map;

import pl.krzaq.metalscrap.model.Company;
import pl.krzaq.metalscrap.model.User;
import freemarker.template.Template;

public interface MailService {

	public void sendUserMail(String template, Map<String, Object> model, String title, User user) ;
	public void sendUserMails(Template template, List<User> users) ;
	public void sendCompanyMail(Template template, Company company) ;
	public void sendCompanyMail(Template template, List<Company> companies) ;
	public void sendMail(String template, Map<String, Object> model, String title, String mailTo);
	
	
}
