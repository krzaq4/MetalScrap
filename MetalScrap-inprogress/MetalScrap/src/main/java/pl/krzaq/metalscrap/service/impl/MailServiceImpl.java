package pl.krzaq.metalscrap.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.zkoss.util.resource.Labels;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import pl.krzaq.metalscrap.model.Company;
import pl.krzaq.metalscrap.model.User;
import pl.krzaq.metalscrap.service.MailService;

public class MailServiceImpl implements MailService {

	@Value("${mail.smtp.server}")
	private String mailHost ;
	
	@Value("${mail.smtp.account}")
	private String mailAccount ;
	
	@Value("${mail.smtp.password}")
	private String mailPassword ;
	
	@Value("${resources.path}")
	private String templatePath ;
	
	@Autowired
	private JavaMailSenderImpl mailSender ;
	
	@Override
	public void sendUserMail(String template, Map<String, Object> model, String title, User user) {
		Template tmpl = this.getTemplate(template) ;
		try {
			String message = FreeMarkerTemplateUtils.processTemplateIntoString(tmpl, model) ;
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
			helper.setText("", message);
		
			SimpleMailMessage msg = new SimpleMailMessage() ;
			helper.setFrom(mailAccount);
			helper.setTo(user.getEmail()) ;
			helper.setSubject(title);
			//.setText(message);
			
			mailSender.send(helper.getMimeMessage());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void sendMail(String template, Map<String, Object> model, String title, String mailTo) {
		Template tmpl = this.getTemplate(template) ;
		try {
			String message = FreeMarkerTemplateUtils.processTemplateIntoString(tmpl, model) ;
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
			helper.setText("", message);
		
			SimpleMailMessage msg = new SimpleMailMessage() ;
			helper.setFrom(mailAccount);
			
			helper.setSubject(title);
			helper.setTo(mailTo) ;
			
			mailSender.send(helper.getMimeMessage());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void sendUserMails(Template template, List<User> users) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendCompanyMail(Template template, Company company) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendCompanyMail(Template template, List<Company> companies) {
		// TODO Auto-generated method stub

	}
	
	
	private Template getTemplate(String name) {
		Configuration cfg = new Configuration() ;
		try {
			
			cfg.setClassForTemplateLoading(this.getClass(), "/");
			return cfg.getTemplate(templatePath+name) ;
			
			
		} catch (IOException e) {
			return null ;
		}
		
		
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public String getMailAccount() {
		return mailAccount;
	}

	public void setMailAccount(String mailAccount) {
		this.mailAccount = mailAccount;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	
	
	
	
}
