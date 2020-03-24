package com.dajiaoyun.community.lowcoding.ee.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dajiaoyun.community.lowcoding.ee.service.CommonService;
import com.dajiaoyun.community.lowcoding.ee.util.date.FlexDate;
import com.dajiaoyun.community.lowcoding.model.Page;
import com.dajiaoyun.community.lowcoding.model.TableObject;
import com.dajiaoyun.community.lowcoding.model.jwt.AuthTokenDetailsDTO;
import com.dajiaoyun.community.lowcoding.model.jwt.JsonWebTokenUtility;

public class BaseController {
	
	@Autowired
	private CommonService<TableObject> commonService;
	
	public String queryData(HttpServletRequest request, HttpServletResponse response, TableObject model) {
		JSONObject root = new JSONObject();
		root.put("code", "403");
		root.put("message", "failed");
		root.put("data", null);
		String tableName = request.getParameter("tablename");
		String view= parseStr(request.getParameter("view"));
		String token = request.getHeader("Authorization");
		JsonWebTokenUtility jsonWebTokenUtility = new JsonWebTokenUtility();
		AuthTokenDetailsDTO user = jsonWebTokenUtility.parseAndValidate(token);
		String usrno = user.userId;
		String tenantno = user.tenantno;
		long num = Long.parseLong(tenantno);
		tableName = tableName + "_" + num;
		
		String childrenTableName = null;
		if (tableName.indexOf(",") > 0) {
			String[] tables = tableName.split(",");
			tableName = tables[0];
			if (tables.length > 1) {
				childrenTableName = tables[1];
				childrenTableName=childrenTableName+ "_" + num;
			}
		}
		
		if(view==null||view.equals("")) {
			view=tableName;
		}else {
			view = view + "_" + num;
		}
		String pageSize =parseStr( request.getParameter("pagesize"));
		String currentPage = parseStr(request.getParameter("currentpage"));
		if (pageSize == null) {
			pageSize = "10";
		}
		if (currentPage == null) {
			currentPage = "1";
		}
		String queryKey = parseStr(request.getParameter("querykey"));
		String queryValue = parseStr(request.getParameter("queryvalue"));
		String operator = parseStr(request.getParameter("operator"));
		String sortColumn = null;
		
		Page page =commonService.getDataAsc(view, pageSize, currentPage, tenantno, queryKey, queryValue, operator,
				sortColumn);
		String primeryKey = null;
		String primeryType = TableObject.STRING;
		List<TableObject> vos = page.getRecords();
		if (vos != null && vos.size() > 0) {
			JSONArray array = new JSONArray();
			int j = 0;
			for (TableObject vo : vos) {
				if (childrenTableName != null && childrenTableName.length() > 0) {
					// primeryKey
					// TableObject
					// parentMetaData=tableService.getMetaData(tableName);
					// TableObject
					// metaData=tableService.getMetaData(childrenTableName);
					String primeryValue = vo.getAttribute(primeryKey);
					// String tableName,String primeryKey,String
					// primeryValue,String primeryType,String tenantno
					List<TableObject> children = commonService.getData(childrenTableName, primeryKey, primeryValue,
							primeryType, tenantno);
					if (children != null && children.size() > 0) {
						int i = 0;
						for (TableObject sub : children) {
							sub.setAttribute("key", String.valueOf(i));
							i++;
						}
						vo.setAttribute("key", String.valueOf(j));
						j++;
						vo.setChildren(children);
					}
				}
//				String action="render: (text, record) => (<span><a href='javascript:;'>修改</a><Divider type='vertical' /><a href='javascript:;'>删除</a></span>";
//				vo.setAttribute("action", action);
				JSONObject obj = vo.toJSONObject();
				array.add(obj);
			}
			root.put("code", "200");
			root.put("message", "succesful");
			root.put("data", array);
			root.put("currentpage", page.getCurrentPage());
			root.put("pagesize", page.getPageSize());
			root.put("totalcount", page.getTotalCount());
			root.put("totalpage", page.getTotalPage());
		} else {
			root.put("code", "200");
		}

		return root.toJSONString();
	}
	
	private String parseStr(String str) {
		String ret=str;
		if(str!=null&&str.equals("undefined")) {
			ret=null;
		}
		
		return ret;
	}
}
