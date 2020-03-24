package com.dajiaoyun.community.lowcoding.ee.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajiaoyun.community.lowcoding.model.FieldObject;
import com.dajiaoyun.community.lowcoding.model.TableObject;

import org.springframework.beans.BeanUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BuildSQLHelper {
	private static HashMap<String, TableObject> tableInfo;
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	public TableObject getTableObject(String tableName) throws SQLException {
		TableObject tableObject = null;
		SqlSession session = sqlSessionFactory.openSession();
		// List ll=session.selectList("query",TableObject.class);
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement stmt=null;
		try {
			if (tableInfo == null) {
				tableInfo = new HashMap<String, TableObject>();
			}
			if (tableInfo.get(tableName) == null) {
				String sql = "select * from " + tableName + " where 1=2";
				conn = session.getConfiguration().getEnvironment().getDataSource().getConnection();
				stmt = conn.prepareStatement(sql);
				DatabaseMetaData dbMetaData=conn.getMetaData();
				rs = stmt.executeQuery();
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				tableObject = new TableObject();
				tableObject.setTableName(tableName);
				ResultSet primaryKeyResultSet = dbMetaData.getPrimaryKeys(null,null,tableName);  
				while(primaryKeyResultSet.next()){  
				    String primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME"); 
				    tableObject.setPrimaryKey(primaryKeyColumnName);
				    break;
				}  
				String primarykey=tableObject.getPrimaryKey();
				List<FieldObject> fields = new ArrayList<FieldObject>();
				for (int i = 1; i <= columnCount; i++) {
					FieldObject fo = new FieldObject();
					fo.setKey(metaData.getColumnName(i).toLowerCase());
					fo.setType(String.valueOf(getColumnType(String.valueOf(metaData.getColumnType(i)))));
					fields.add(fo);
					
					if(fo.getKey().equals(primarykey)) {
						tableObject.setPrimaryType(fo.getType());
					}
				}
				tableObject.setFields(fields);
				tableObject.setDBtype(dbMetaData.getDatabaseProductName().toUpperCase());
				rs.close();
				stmt.close();

				String dbType = dbMetaData.getDatabaseProductName().toUpperCase();
				tableObject.setDBtype(dbType);
				tableInfo.put(tableName, tableObject);
				
				
			} else {
				tableObject = tableInfo.get(tableName);
			}
			
		}catch(Exception e) {
//			log.error(e.getMessage());
			e.printStackTrace();
		}finally {
			if(rs!=null&&!rs.isClosed()) {
				rs.close();
			}
			if(stmt!=null&&!stmt.isClosed()) {
				stmt.close();
			}
			session.close();
		}
		
		return tableObject;
	}
	
	private  String getColumnType(String oriColumnType) {
		String ret = "";
		if (oriColumnType.equals(String.valueOf(Types.BIGINT))) {
			ret = TableObject.LONG;
		} else if (oriColumnType.toUpperCase().equals(String.valueOf(Types.INTEGER))) {
			ret = TableObject.INT;
		} else if (oriColumnType.equals(String.valueOf(Types.TIME))
				|| oriColumnType.equals(String.valueOf(Types.TIMESTAMP))
				|| oriColumnType.equals(String.valueOf(Types.DATE))) {
			ret = TableObject.DATE;
		} else if (oriColumnType.toUpperCase().equals(String.valueOf(Types.LONGVARCHAR))
				|| oriColumnType.toUpperCase().equals(String.valueOf(Types.CHAR))
				|| oriColumnType.toUpperCase().equals(String.valueOf(Types.VARCHAR))) {
			ret = TableObject.STRING;
		} else if (oriColumnType.equals(String.valueOf(Types.NUMERIC))) {
			ret = TableObject.NUMBER;
		} else if (oriColumnType.equals(String.valueOf(Types.BLOB))) {
			ret = TableObject.BLOB;
		} else if (oriColumnType.equals(String.valueOf(Types.CLOB))) {
			ret = TableObject.CLOB;
		} else if (oriColumnType.equals(String.valueOf(Types.DECIMAL))) {
			ret = TableObject.DOUBLE;
		} else if (oriColumnType.equals(String.valueOf(Types.DOUBLE))) {
			ret = TableObject.DOUBLE;
		} else {
//			log.error("Unkonw Column Type:"+oriColumnType);
		}
		return ret;
	}
	public  TableObject removeUselessFields(TableObject to) throws SQLException{
		TableObject data=new TableObject();
		BeanUtils.copyProperties(to,data);
		String tableName=data.getTableName();
		String tname=tableName;
		if(tableName.indexOf(",")>0) {
			String[] names=tableName.split(",");
			tname=names[0];
		}
		TableObject tableConfigure=getTableObject(tname);
		if(data.getPrimaryKey()==null) {
			if(tableConfigure.getPrimaryKey()!=null) {
				data.setPrimaryKey(tableConfigure.getPrimaryKey());
			}else {
				data.setPrimaryKey(""); //TODO 有的表就是没有Primary Key
			}
			
		}
		if(data.getPrimaryType()==null) {
			data.setPrimaryType(tableConfigure.getPrimaryType());
		}
		if(data.getPrimaryValue()==null||data.getPrimaryValue().equals("")) {
			String pv=null;
			if(data.getPrimaryKey()!=null&&!data.getPrimaryKey().equals("")) {
				pv=to.getAttribute(data.getPrimaryKey());
			}
			if(pv==null||pv.equals("")) {
				pv=to.getAttribute("primaryvalue");
			}
			if(pv!=null&&!pv.equals("")) {
				data.setPrimaryValue(pv);
			}
			
		}
		for(FieldObject fieldObject : data.getFields()){
			fieldObject.setGeneratesql(true);
		}
		boolean resetPk=false;
		for(FieldObject fieldObject : data.getFields()){
			boolean found=false;
			for(FieldObject fo : tableConfigure.getFields()){
				if(fieldObject.getKey().equals(fo.getKey())){
					found=true;
					if(fieldObject.getType()==null||fieldObject.getType().equals("")){
						fieldObject.setType(fo.getType());
					}else{
						if(fieldObject.getType().equals(TableObject.STRING)&&!fo.getType().equals(TableObject.STRING)){
							fieldObject.setType(fo.getType());
						}
					}
					if(fieldObject.getType().equals(TableObject.LONG)||fieldObject.getType().equals(TableObject.FLOAT)||
							fieldObject.getType().equals(TableObject.DOUBLE)||fieldObject.getType().equals(TableObject.INT)||
							fieldObject.getType().equals(TableObject.INTEGER)||fieldObject.getType().equals(TableObject.LNUMBERIC)||
							fieldObject.getType().equals(TableObject.NUMBER)){
						if(!fieldObject.isAutogenerate()&&(fieldObject.getValue()==null||fieldObject.getValue().equals(""))){
							fieldObject.setGeneratesql(false);
						}
					}
					break;
				}
			}
			if(!found){  
				fieldObject.setGeneratesql(false);
				if(fieldObject.getKey().equals(data.getPrimaryKey())) {
					resetPk=true;
//					data.setPrimaryKey(tableConfigure.getPrimaryKey());
//					data.setPrimaryType(tableConfigure.getFieldType(tableConfigure.getPrimaryKey()));
				}
			}
		}
		if(resetPk) {
			data.setPrimaryKey(tableConfigure.getPrimaryKey());
			data.setPrimaryType(tableConfigure.getFieldType(tableConfigure.getPrimaryKey()));
			if(data.getPrimaryValue()==null||data.getPrimaryValue().equals("")) {
				if(to.getAttribute("primaryvalue")!=null&&!to.getAttribute("primaryvalue").equals("")) {
					data.setPrimaryValue(to.getAttribute("primaryvalue"));
				}
				
			}
		}
		return data;
	}
	
}
