package com.dajiaoyun.community.lowcoding.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dajiaoyun.community.lowcoding.model.TableObject;


public class JSONUtil {
	public static TableObject JSON2Object(JSONObject jsonObj) {
		TableObject vo = new TableObject();
		JSONArray fields=jsonObj.getJSONArray("fields");
		for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
			String key = entry.getKey();
			if (key.equalsIgnoreCase("zibiaodan")) {
				JSONArray list = jsonObj.getJSONArray("zibiaodan");
				if (list != null && list.size() > 0) {
					List<TableObject> children = new ArrayList<TableObject>();
					for (int i = 0; i < list.size(); i++) {
						JSONObject obj = list.getJSONObject(i);
						TableObject child=JSON2Object2(obj);
						children.add(child);
					}
					vo.setChildren(children);
				}
			} else {
				if (!key.equalsIgnoreCase("fields") && !key.equalsIgnoreCase("path")) {
					if (entry.getValue() != null) {
						String value= entry.getValue().toString();
						if(value.equals("系统自动生成")) {
							value=UidGenerator.getIdByYMD();
							String prefix=getPrefix(fields,key);
							value=prefix+value;
						}
						vo.setAttribute(key,value);
					} else {
						vo.setAttribute(key, "");
					}
				}
			}

		}
		return vo;
	}
	private static String getPrefix(JSONArray fields,String key) {
		String prefix="";
		for(int i=0;i<fields.size();i++) {
			JSONObject obj=fields.getJSONObject(i);
			if(obj.getString("key").equalsIgnoreCase(key)) {
				prefix=obj.getString("fieldAutoSysValPrefix");
				if(prefix==null) {
					prefix="";
				}
				break;
			}
		}
		return prefix;
	}
	private static TableObject JSON2Object2(JSONObject jsonObj) {
		TableObject vo = new TableObject();
		for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
			String key = entry.getKey();
			if (entry.getValue() != null) {
				vo.setAttribute(key, entry.getValue().toString());
			} else {
				vo.setAttribute(key, "");
			}

		}
		return vo;
	}
}
