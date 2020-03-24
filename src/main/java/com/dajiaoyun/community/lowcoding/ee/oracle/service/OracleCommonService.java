package com.dajiaoyun.community.lowcoding.ee.oracle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajiaoyun.community.lowcoding.ee.dao.BaseDAO;
import com.dajiaoyun.community.lowcoding.ee.oracle.dao.OracleBaseDAO;
import com.dajiaoyun.community.lowcoding.ee.service.CommonService;

/**
 * @author xing
 * 2018/11/09 适合在一个配置文件里配置多数据源，例如一个mysql，一个Oracle
 */
@Service
public class OracleCommonService<T> extends CommonService<T>{
	@Autowired
	private  OracleBaseDAO baseDAO;
	
	
	public OracleCommonService(){
		
	}

	@Override
	protected BaseDAO getBaseDAO() {
		return baseDAO;
	}
	
}
