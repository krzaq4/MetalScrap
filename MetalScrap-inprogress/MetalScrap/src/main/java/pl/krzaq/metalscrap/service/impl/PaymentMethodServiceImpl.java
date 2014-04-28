package pl.krzaq.metalscrap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.krzaq.metalscrap.dao.PaymentMethodDAO;
import pl.krzaq.metalscrap.model.PaymentMethod;
import pl.krzaq.metalscrap.service.PaymentMethodService;

@Component(value="paymentMethodService")
public class PaymentMethodServiceImpl implements PaymentMethodService {

	@Autowired
	private PaymentMethodDAO paymentMethodDAO ;
	
	@Override
	public List<PaymentMethod> findAll() {
		return paymentMethodDAO.findAll() ;
	}

	public PaymentMethodDAO getPaymentMethodDAO() {
		return paymentMethodDAO;
	}

	public void setPaymentMethodDAO(PaymentMethodDAO paymentMethodDAO) {
		this.paymentMethodDAO = paymentMethodDAO;
	}

	
	
}
