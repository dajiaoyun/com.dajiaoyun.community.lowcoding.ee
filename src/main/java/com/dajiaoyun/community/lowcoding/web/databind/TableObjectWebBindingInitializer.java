package com.dajiaoyun.community.lowcoding.web.databind;

import java.util.Iterator;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import com.dajiaoyun.community.lowcoding.model.TableObject;



public class TableObjectWebBindingInitializer  implements WebBindingInitializer {
	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		Object target=binder.getTarget();
		if(target instanceof TableObject){
			TableObject vo=(TableObject)target;
			Iterator<String> keys=request.getParameterNames();
			while(keys.hasNext()){
				String key=keys.next();
				String value=request.getParameter(key);
				if(!key.equalsIgnoreCase("tableName")){
					vo.setAttribute(key, value);
				}else {
					vo.setTableName(value);
				}
			}
		}
	}

	@Override
	public void initBinder(WebDataBinder binder) {
		Object target=binder.getTarget();
		if(target instanceof TableObject){
			TableObject vo=(TableObject)target;
		
		}
		
	}
}