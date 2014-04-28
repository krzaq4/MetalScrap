package pl.krzaq.metalscrap.service;

import java.util.List;

import pl.krzaq.metalscrap.dao.PaymentMethodDAO;
import pl.krzaq.metalscrap.model.PaymentMethod;

public interface PaymentMethodService {

	public PaymentMethodDAO getPaymentMethodDAO() ;
	
	public List<PaymentMethod> findAll() ;
}
