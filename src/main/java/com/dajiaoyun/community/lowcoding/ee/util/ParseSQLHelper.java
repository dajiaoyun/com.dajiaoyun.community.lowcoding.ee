package com.dajiaoyun.community.lowcoding.ee.util;

import com.dajiaoyun.community.lowcoding.model.TableObject;

public class ParseSQLHelper {

	public static void main(String[] args) {
		String insertSql="insert into t_user(   usrno  ,   tenantno  ) values  ( '00003',  \"0000000001\"   )   ";
		String updateSQL="update t_user set usrno='0003' where usrno='0003' and tenantno = '0000000001'   ";
		String deleteSQL=" delete from o2o_engineer where shifu_no='0002' and tenantno='paihuoyi' ' ";
		parseSQL(insertSql);
		parseSQL(updateSQL);
		parseSQL(deleteSQL);
	}
	
	public static TableObject parseSQL(String sql){
		sql=sql.toLowerCase();
		sql=sql.trim();
		if(sql.indexOf("insert")==0){
			return parseInsertSQL(sql);
		}else if(sql.indexOf("update")==0){
			return parseUpdateSQL(sql);
		}else if(sql.indexOf("delete")==0){
			return parseDeleteSQL(sql);
		}
		return null;
	}
	private static TableObject parseDeleteSQL(String sql){
		TableObject vo=new TableObject();
		vo.setAttribute("action", "delete");
		int start=sql.indexOf("from")+4;
		int end=sql.indexOf("where");
		String tableName=sql.substring(start, end);
		tableName=tableName.trim();
		vo.setAttribute("table_name", tableName);
		
		start=sql.indexOf("tenantno");
		if(start>0){
			start=sql.indexOf("=",start);
			start=start+1;
			end=sql.indexOf("and",start);
			if(end==-1){
				end=sql.length();
			}
			String value=sql.substring(start, end);
			value=value.trim();
			value=value.replaceAll("'", "");
			value=value.replaceAll("\"", "");
			vo.setAttribute("tenantnno", value);
		}
		
		return vo;
	}
	private static TableObject parseInsertSQL(String sql){
		TableObject vo=new TableObject();
		int start=sql.indexOf("into")+4;
		int end=sql.indexOf("(");
		String tableName=sql.substring(start, end);
		tableName=tableName.trim();
		vo.setAttribute("action", "insert");
		vo.setAttribute("table_name", tableName);
		start=end+1;
		String sql2=sql.substring(start);
		end=sql2.indexOf(")");
		String fieldnames=sql2.substring(0,end);
		String[] fields=fieldnames.split(",");
		start=sql.indexOf("values")+6;
		sql2=sql.substring(start);
		start=sql2.indexOf("(")+1;
		end=sql2.lastIndexOf(")");
		sql2=sql2.substring(start,end);
		sql2=sql2.trim();
		String[] values=sql2.split(",");
		for(int i=0;i<fields.length;i++){
			String key=fields[i];
			key=key.trim();
			String value=values[i];
			value=value.trim();
			value=value.replaceAll("'", "");
			value=value.replaceAll("\"", "");
			vo.setAttribute(key,value);
		}
		return vo;
	}
	
	private static TableObject parseUpdateSQL(String sql){
		TableObject vo=new TableObject();
		int start=sql.indexOf("update")+6;
		int end=sql.indexOf("set");
		String tableName=sql.substring(start, end);
		tableName=tableName.trim();
		vo.setAttribute("action", "update");
		vo.setAttribute("table_name", tableName);
		start=end+3;
		end=sql.indexOf("where");
		if(end==-1){
			end=sql.length();
		}
		String fieldAndValues=sql.substring(start,end);
		String[] keyvalues=fieldAndValues.split(",");
		for(int i=0;i<keyvalues.length;i++){
			 String[] keyvalue=keyvalues[i].split("=");
			 String key=keyvalue[0];
			 String value=keyvalue[1];
			 key=key.trim();
			 value=value.trim();
			 value=value.replaceAll("'", "");
			 value=value.replaceAll("\"", "");
			 vo.setAttribute(key,value);
		}
		String tenantno=vo.getAttribute("tenantno");
		if(tenantno==null||tenantno.equals("")){
			start=sql.indexOf("tenantno");
			if(start>0){
				start=sql.indexOf("=",start);
				start=start+1;
				end=sql.indexOf("and",start);
				if(end==-1){
					end=sql.length();
				}
				String value=sql.substring(start, end);
				value=value.trim();
				value=value.replaceAll("'", "");
				value=value.replaceAll("\"", "");
				vo.setAttribute("tenantnno", value);
			}
		}
		return vo;
	}

}
