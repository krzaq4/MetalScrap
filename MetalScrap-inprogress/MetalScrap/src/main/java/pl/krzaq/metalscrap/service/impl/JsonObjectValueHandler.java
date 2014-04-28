package pl.krzaq.metalscrap.service.impl;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class JsonObjectValueHandler implements HandlerMethodReturnValueHandler {

	@Override
	public void handleReturnValue(Object returnValue,
			MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		// TODO Auto-generated method stub
		return false;
	}

}
