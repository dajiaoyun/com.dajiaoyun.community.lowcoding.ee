package com.dajiaoyun.community.lowcoding.excel.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.dajiaoyun.community.lowcoding.ee.util.UtilHelper;
import com.dajiaoyun.community.lowcoding.model.TableObject;


public class ExcelListener extends AnalysisEventListener<Object> {
	private List<TableObject> data = new ArrayList<TableObject>();

	@Override
	public void invoke(Object object, AnalysisContext context) {
		if(object instanceof List) {
			List list=(List)object;
			TableObject vo=new TableObject();
			int i=0;
			for(Object obj:list) {
				if(obj!=null) {
					String s=String.valueOf(obj);
					if(UtilHelper.isNumeric3(s)) {
						int start=s.indexOf(".");
						if(start>0) {
							//整数自动转换成double啦
							String v=s.substring(start+1);
							if(Integer.parseInt(v)==0) {//其实是整数
								s=s.substring(0,start);
							}
						}
					}else if(s.indexOf("00:00:00")>0) { //年月日自动转换成 年月日时分秒了
						int start=s.indexOf("00:00:00");
						s=s.substring(0,start);
						s=s.trim();
					}
					vo.setAttribute("key"+i, s);
				}else {
					vo.setAttribute("key"+i, "");
				}
				i++;
			}
			data.add(vo);
		}
	}
	
	public List<TableObject> getData() {
		return data;
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		
	}

}
