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

import javax.sql.DataSource;

import com.dajiaoyun.community.lowcoding.model.FieldObject;
import com.dajiaoyun.community.lowcoding.model.TableObject;


public class TableInfoService {
	private static HashMap<String, TableObject> tableInfo;

	public static void removeUselessFields(TableObject vo, TableObject tableConfigure) {
		for (FieldObject fieldObject : vo.getFields()) {
			fieldObject.setGeneratesql(true);
		}
		for (FieldObject fieldObject : vo.getFields()) {
			boolean found = false;
			for (FieldObject fo : tableConfigure.getFields()) {
				if (fieldObject.getKey().equals(fo.getKey())) {
					found = true;
					if (fieldObject.getType() == null || fieldObject.getType().equals("")) {
						fieldObject.setType(fo.getType());
					} else {
						if (fieldObject.getType().equals(TableObject.STRING)
								&& !fo.getType().equals(TableObject.STRING)) {
							fieldObject.setType(fo.getType());
						}
					}
					if (fieldObject.getType().equals(TableObject.LONG)
							|| fieldObject.getType().equals(TableObject.FLOAT)
							|| fieldObject.getType().equals(TableObject.DOUBLE)
							|| fieldObject.getType().equals(TableObject.INT)
							|| fieldObject.getType().equals(TableObject.INTEGER)
							|| fieldObject.getType().equals(TableObject.LNUMBERIC)
							|| fieldObject.getType().equals(TableObject.NUMBER)) {
						if (fieldObject.getValue() == null || fieldObject.getValue().equals("")) {
							fieldObject.setValue("0");
						}
					}
					break;
				}
			}
			if (!found) {
				// vo.removeAttribute(fieldObject.getKey());
				fieldObject.setGeneratesql(false);
			}
		}
	}

	public static TableObject getTableObject(DataSource dataSource, String tableName) {
		Connection conn=null;
		TableObject vo=null;
		try{
			conn=dataSource.getConnection();
			vo=getTableObjecta(conn, tableName);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vo;
	}

	private static TableObject getTableObjecta(Connection conn, String tableName) {
		TableObject ret = null;
		try {
			if (tableInfo == null) {
				tableInfo = new HashMap<String, TableObject>();
			}
			if (tableInfo.get(tableName) == null) {
				TableObject tableObject = new TableObject();
				tableObject.setTableName(tableName);

				String sql = "select * from " + tableName + " where 1=2";
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData metaData = rs.getMetaData();
				DatabaseMetaData dbMeta = conn.getMetaData(); 
				ResultSet pkRSet=dbMeta.getPrimaryKeys(null, null, tableName);
				while(pkRSet.next()){
					System.err.println("****** Comment ******"); 
					System.err.println("TABLE_CAT : "+pkRSet.getObject(1)); 
					System.err.println("TABLE_SCHEM: "+pkRSet.getObject(2)); 
					System.err.println("TABLE_NAME : "+pkRSet.getObject(3)); 
					System.err.println("COLUMN_NAME: "+pkRSet.getObject(4)); 
					System.err.println("KEY_SEQ : "+pkRSet.getObject(5)); 
					System.err.println("PK_NAME : "+pkRSet.getObject(6)); 
				}
				pkRSet.close();
				int columnCount = metaData.getColumnCount();
				List<FieldObject> fields = new ArrayList<FieldObject>();
				for (int i = 1; i <= columnCount; i++) {
					FieldObject fo = new FieldObject();
					fo.setKey(metaData.getColumnName(i).toLowerCase());
					fo.setType(String.valueOf(getColumnType(String.valueOf(metaData.getColumnType(i)))));
					fields.add(fo);
				}
				tableObject.setFields(fields);
				DatabaseMetaData dbMetaData = conn.getMetaData();
				tableObject.setDBtype(dbMetaData.getDatabaseProductName().toUpperCase());
				
				
				rs.close();
				stmt.close();

				String dbType = dbMetaData.getDatabaseProductName().toUpperCase();
				tableObject.setDBtype(dbType);
				ret = tableObject;
				tableInfo.put(tableName, tableObject);
			} else {
				ret = tableInfo.get(tableName);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;

	}

	// public static TableObject getTableObject(String tableName) {
	// UTransaction transaction = TransactionFactory.getInstance();
	// TableObject ret=null;
	// try {
	// Connection conn=transaction.getConnection();
	// ret=getTableObject(conn,tableName);
	// conn.close();
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// return ret;
	// }

	private static String getColumnType(String oriColumnType) {
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
			System.out.println("Unkonw Column Type");
		}
		return ret;
	}
}