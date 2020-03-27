package com.dajiaoyun.community.lowcoding.ee.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dajiaoyun.community.lowcoding.ee.dao.BaseDAO;
import com.dajiaoyun.community.lowcoding.model.FieldObject;
import com.dajiaoyun.community.lowcoding.model.Page;
import com.dajiaoyun.community.lowcoding.model.TableObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
public class CommonService<T> {
	@Autowired
	private BaseDAO baseDAO;
	@Autowired
	private BuildSQLHelper buildSQLHelper;

	public CommonService() {

	}

	protected BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void execute(String sql) throws RuntimeException {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("sql", sql);
		getBaseDAO().execute(maps);
	}
	
	public List<TableObject> getData(String tableName, String primeryKey, String primeryValue, String primeryType,
			String tenantno) {
		List<TableObject> data = this.queryByTablename(tableName, primeryKey, primeryValue, primeryType, tenantno,null);
		if (data != null && data.size() > 0) {
			for (TableObject vo : data) {
				vo.setTableName(tableName);
			}
		}
		return data;
	}

	
	public Page getDataAsc(String tableName, String pageSize, String currentPage, String tenantno,String key,String value,String operation,String sortColumn) {
		Page data = this.queryByTableNameAsc(tableName, pageSize, currentPage, tenantno,sortColumn,key,value,operation);
		if (data.getRecords() != null && data.getRecords().size() > 0) {
			for (TableObject vo : data.getRecords()) {
				vo.setTableName(tableName);
			}
		}
		return data;
	}
	
	public TableObject find(TableObject vo) {
		List<FieldObject> keys=vo.getQueryFields();
		String sql="select * from "+vo.getTableName()+" where 1=1 ";
		for(FieldObject fo2:keys){
			FieldObject fo=vo.getFieldObject(fo2.getKey().trim());
			if(fo!=null) {
				if(fo.getType()!=null){
					if(fo.getType().equalsIgnoreCase(TableObject.STRING)||fo.getType().equalsIgnoreCase(TableObject.DATE)){
						sql=sql+" and "+fo.getKey()+"='"+fo.getValue()+"' ";
					}else if(fo.getType().equalsIgnoreCase(TableObject.INT)||fo.getType().equalsIgnoreCase(TableObject.INTEGER)
							||fo.getType().equalsIgnoreCase(TableObject.NUMBER)||fo.getType().equalsIgnoreCase(TableObject.LONG)
							||fo.getType().equalsIgnoreCase(TableObject.LNUMBERIC)){
						sql=sql+" and "+fo.getKey()+"="+fo.getValue();
					}else{
						sql=sql+" and "+fo.getKey()+"='"+fo.getValue()+"' ";
					}
				}else{
					sql=sql+" and "+fo.getKey()+"='"+fo.getValue()+"' ";
				}
			}
			
		}
		TableObject data=this.find(sql);
		return data;
	}
	
	public void insertOrUpdate(TableObject vo) throws Exception {
		List<FieldObject> keys=vo.getQueryFields();
		String sql="select * from "+vo.getTableName()+" where 1=1 ";
		for(FieldObject fo2:keys){
			FieldObject fo=vo.getFieldObject(fo2.getKey());
			if(fo!=null) {
				if(fo.getType()!=null){
					if(fo.getType().equalsIgnoreCase(TableObject.STRING)||fo.getType().equalsIgnoreCase(TableObject.DATE)){
						sql=sql+" and "+fo.getKey()+"='"+fo.getValue()+"' ";
					}else if(fo.getType().equalsIgnoreCase(TableObject.INT)||fo.getType().equalsIgnoreCase(TableObject.INTEGER)
							||fo.getType().equalsIgnoreCase(TableObject.NUMBER)||fo.getType().equalsIgnoreCase(TableObject.LONG)
							||fo.getType().equalsIgnoreCase(TableObject.LNUMBERIC)){
						sql=sql+" and "+fo.getKey()+"="+fo.getValue();
					}else{
						sql=sql+" and "+fo.getKey()+"='"+fo.getValue()+"' ";
					}
				}else{
					sql=sql+" and "+fo.getKey()+"='"+fo.getValue()+"' ";
				}
			}
			
		}
		TableObject data=this.find(sql);
		if(data==null){
			this.insert(vo);
		}else{
			vo.setPrimaryValue(data.getAttribute(vo.getPrimaryKey()));
			this.update(vo);
		}
	}
	
	public TableObject insert(TableObject vo) throws Exception {

		TableObject data = buildSQLHelper.removeUselessFields(vo);
		Map<String, Object> maps = new HashMap<String, Object>();
		String sql = data.buildInsertSQLs();
		maps.put("sql", sql);
		getBaseDAO().insert(maps);
		String id = (String) maps.get("indentid_id");
		vo.setAttribute("id", id);

		return vo;
	}

	public TableObject update(TableObject vo) throws Exception {
		TableObject data = buildSQLHelper.removeUselessFields(vo);
		Map<String, Object> maps = new HashMap<String, Object>();
		String sql = data.buildUpdateSQL(data);
		if(sql!=null&&!sql.equals("")) {
			maps.put("sql", sql);
			getBaseDAO().update(maps);
		}else {
//			log.error("update sql is null");
		}
		return vo;
	}

	/**
	 * 
	 * @param tablename
	 * @param primaryKey
	 *        
	 * @param primaryValue
	 * @param tenantno
	 * @throws RuntimeException
	 */
	public void remove(String tablename, String primaryKey, String primaryValue, String tenantno)
			throws RuntimeException {
		String sql = "delete from " + tablename + " where " + primaryKey + "='" + primaryValue + "' and tenantno='"
				+ tenantno + "'";
		remove(sql);
	}

	/**
	 * 
	 * @param tablename
	 * @param primaryKey
	 *    
	 * @param primaryValue
	 * @param tenantno
	 * @throws RuntimeException
	 */
	public void remove(String tablename, String primaryKey, int primaryValue, String tenantno) throws RuntimeException {
		String sql = "delete from " + tablename + " where " + primaryKey + "=" + primaryValue + " and tenantno='"
				+ tenantno + "'";
		remove(sql);
	}

	public void remove(TableObject vo) throws Exception {
		TableObject data = buildSQLHelper.removeUselessFields(vo);
		String tablename = data.getTableName();
		String primaryKey = data.getPrimaryKey();
		String primaryValue = data.getPrimaryValue();
		
		String tenantno = vo.getAttribute("tenantno");
		String sql = "delete from " + tablename + " where " + primaryKey + "='" + primaryValue + "'";
		if (tenantno != null && !tenantno.equals("")) {
			sql = sql + " and tenantno='" + tenantno + "'";
		}
		remove(sql);
	}

	public void remove(String sql) throws RuntimeException {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("sql", sql);
		getBaseDAO().remove(maps);
	}

	public TableObject findbyid(String tableName, String primaryKeyName, String primaryKeyValue, String tenantno) {
		String sql = "select * from " + tableName + " where " + primaryKeyName + "='" + primaryKeyValue
				+ "' and tenantno='" + tenantno + "' ";
		return find(sql);
	}

	public TableObject findbyid(String tableName, String primaryKeyName, String primaryKeyValue) {
		String sql = "select * from " + tableName + " where " + primaryKeyName + "='" + primaryKeyValue + "' ";
		return find(sql);
	}

	public TableObject find(String sql) {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("sql", sql);
//		log.info("sql=" + sql);
		Map<String, String> results = getBaseDAO().find(maps);
		TableObject vo = null;
		if (results != null) {
			vo = map(results);
		}

		return vo;
	}
	public JSONObject findJSONObject(String sql) {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("sql", sql);
//		log.info("sql=" + sql);
		Map<String, String> results = getBaseDAO().find(maps);
		JSONObject vo = null;
		if (results != null) {
			vo = mapjson(results);
		}

		return vo;
	}
	
	public List<TableObject> querybyid(String tableName, String primaryKeyName, String primaryKeyValue,
			String tenantno) {
		String sql = "select * from " + tableName + " where " + primaryKeyName + "='" + primaryKeyValue
				+ "' and tenantno='" + tenantno + "' ";
		return query(sql);
	}

	public List<TableObject> querybyid(String tableName, String primaryKeyName, String primaryKeyValue) {
		String sql = "select * from " + tableName + " where " + primaryKeyName + "='" + primaryKeyValue + "'";
		return query(sql);
	}

	public Page queryByTableName(String tableName, String pageSize, String currentPage, String tenantno,
			String sortColumn) {
		if (sortColumn == null || sortColumn.equals("")) {
			sortColumn = " lstupdatedate ";
		}
		String sql = "select * from " + tableName + " where tenantno='" + tenantno + "' order by " + sortColumn
				+ " desc ";
		return query(sql, pageSize, currentPage);
	}

	public Page queryByTableName(String tableName, String pageSize, String currentPage, String tenantno,
			String sortColumn, String key, String value, String operation) {
		String sql = "select * from " + tableName + " where tenantno='" + tenantno + "' ";
		if (key != null && !key.equals("") && value != null && !value.equals("") && operation != null
				&& !operation.equals("")) {
			if (operation.equalsIgnoreCase("like")) {
				sql = sql + " and " + key + " like '%" + value + "%'";
			} else {
				sql = sql + " and (" + key + operation + "'" + value + "' )";
			}
		}
		if (sortColumn != null && !sortColumn.trim().equals("")) {
			sql = sql + " order by " + sortColumn + " desc ";
		}

		return query(sql, pageSize, currentPage);
	}

	public Page queryByTableNameAsc(String tableName, String pageSize, String currentPage, String tenantno,
			String sortColumn, String key, String value, String operation) {
		String sql = "select * from " + tableName + " where tenantno='" + tenantno + "' ";
		if (key != null && !key.equals("") && value != null && !value.equals("") && operation != null
				&& !operation.equals("")) {
			if (operation.equalsIgnoreCase("like")) {
				sql = sql + " and " + key + " like '%" + value + "%'";
			} else {
				sql = sql + " and (" + key + operation + "'" + value + "' )";
			}
		}
		if (sortColumn != null && !sortColumn.trim().equals("")) {
			sql = sql + " order by " + sortColumn;
		}

		return query(sql, pageSize, currentPage);
	}

	public Page query(String originalSql, String pageSize, String currentPage) {
		String dialect = "mysql";
		List<TableObject> results = new ArrayList<TableObject>();
		Page page = new Page();
		String countSql = getCountSql(originalSql, dialect);
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("sql", countSql);
		TableObject countVo = this.find(countSql);
		String totalCount = "0";
		if (countVo != null) {
			totalCount = countVo.getAttribute("num");
		}
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		try {
			if (totalCount == null || totalCount.equals("")) {
//				System.out.println();
			}
			page.setTotalCount(totalCount);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String page_sql = getPageSql(originalSql, dialect, page.startRecord, Integer.parseInt(page.getPageSize()));

		// maps.put("page", page);
		maps.put("sql", page_sql);
		List<Map<String, String>> ret = getBaseDAO().query(maps);
		if (ret != null && ret.size() > 0) {
			for (int i = 0; i < ret.size(); i++) {
				Map<String, String> v = ret.get(i);
				TableObject vo = map(v);
				results.add(vo);
			}
		}
		page.setRecords(results);
		return page;
	}

	public TableObject findByTablename(String tableName, String tenantno) {
		String sql = "select * from " + tableName + " where tenantno='" + tenantno + "'";
		return find(sql);
	}

	public TableObject findByTablename(String tableName) {
		String sql = "select * from " + tableName;
		return find(sql);
	}

	public List<TableObject> queryByTablenameBySort(String tableName, String tenantno, String sortColumn) {
		if (sortColumn == null || sortColumn.equals("")) {
			// sortColumn=" lstupdatedate ";
		}
		String sql = "select * from " + tableName + " where tenantno='" + tenantno + "' order by " + sortColumn
				+ " desc ";
		return query(sql);
	}

	public List<TableObject> queryByTablenameByGroup(String tableName, String tenantno, String groupby) {
		String sql = "select * from " + tableName + " where tenantno='" + tenantno + "' group by " + groupby;
		return query(sql);
	}

	public List<TableObject> queryByTablename(String tableName, String primeryKey, String primeryValue,
			String primeryType, String tenantno, String sortColumn) {
		if (sortColumn == null || sortColumn.equals("")) {
			// sortColumn=" lstupdatedate ";
		}
		String sql = "select * from " + tableName + " where tenantno='" + tenantno + "' " ;
		if (primeryType.equalsIgnoreCase(TableObject.STRING)) {
			sql = sql + " and " + primeryKey + "='" + primeryValue + "'";
		} else {
			sql = sql + " and " + primeryKey + "=" + primeryValue;
		}
		if(sortColumn!=null&&!sortColumn.equals("")) {
			sql=sql+" order by "+sortColumn;
		}
		return query(sql);
	}

	public List<TableObject> queryByTableName(String tableName, String tenantno) {
		return query(tableName, tenantno);
	}
	public List<TableObject> queryByTableName(String tableName) {
		return query(tableName, null);
	}


	public List<TableObject> queryByTablename(String tableName, String sortColumn, String tenantno) {
		if (sortColumn == null || sortColumn.equals("")) {
			// sortColumn=" lstupdatedate ";
		}
		String sql = "select * from " + tableName;
		if (sortColumn != null && !sortColumn.equals("")) {
			sql = "select * from " + tableName;
		}
		if (tenantno != null && !tenantno.equals("")) {
			sql = sql + " and tenantno='" + tenantno + "'";
		}
		sql = sql + " order by " + sortColumn + " desc ";
		return query(sql);
	}

	public List<TableObject> query(String tableName, String tenantno) {
		String sql = "select * from " + tableName + " where tenantno='" + tenantno + "'";
		if(tenantno==null) {
			sql = "select * from " + tableName ;
		}
		return query(sql);
	}
	
//	public List<TableObject> fastquery(String sql) {
//		return query(sql);
//	}

//	public List<TableObject> fastquery(String sql) {
//		List<TableObject> vos = new ArrayList<TableObject>();
//
//		SqlSession session = sqlSessionFactory.openSession();
//		// List ll=session.selectList("query",TableObject.class);
//		Connection conn = null;
//		try {
//			conn = session.getConfiguration().getEnvironment().getDataSource().getConnection();
//			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
//					ResultSet.CONCUR_READ_ONLY);
//			stmt.setFetchSize(Integer.MIN_VALUE);
//			ResultSet rs = stmt.executeQuery();
//			ResultSetMetaData rsmd = rs.getMetaData();
//			int columnCount = rsmd.getColumnCount();
//
//			try {
//				while (rs.next()) {
//					TableObject vo = new TableObject();
//					for (int i = 1; i <= columnCount; i++) {
//						String key = rsmd.getColumnName(i);
//						String value = rs.getString(i);
//						vo.setAttribute(key, value);
//					}
//					vos.add(vo);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				rs.close();
//				stmt.close();
//				session.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return vos;
//	}

	public List<TableObject> query(String sql) {
		List<TableObject> results = new ArrayList<TableObject>();
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("sql", sql);
		List<Map<String, String>> ret = getBaseDAO().query(maps);
		if (ret != null && ret.size() > 0) {
			for (int i = 0; i < ret.size(); i++) {
				Map<String, String> v = ret.get(i);
				TableObject vo = map(v);
				results.add(vo);
			}
		}
		return results;
	}

	public JSONArray queryJSONArray(String tableName, String tenantno) {
		String sql = "select * from " + tableName + " where tenantno='" + tenantno + "'";
		return queryJSONArray(sql);
	}

	/**
	 * 
	 * 
	 * @param sql
	 * @return
	 */
//	public JSONArray queryFastJSONArray(String sql) {
//		SqlSession session = sqlSessionFactory.openSession();
//		// List ll=session.selectList("query",TableObject.class);
//		JSONArray vos = new JSONArray();
//		Connection conn = null;
//		try {
//			conn = session.getConfiguration().getEnvironment().getDataSource().getConnection();
//			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
//					ResultSet.CONCUR_READ_ONLY);
//			stmt.setFetchSize(Integer.MIN_VALUE);
//			ResultSet rs = stmt.executeQuery();
//			ResultSetMetaData rsmd = rs.getMetaData();
//			int columnCount = rsmd.getColumnCount();
//
//			try {
//				while (rs.next()) {
//					JSONObject vo = new JSONObject();
//					for (int i = 1; i <= columnCount; i++) {
//						String key = rsmd.getColumnName(i);
//						String value = rs.getString(i);
//						vo.put(key, value);
//					}
//					vos.add(vo);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				rs.close();
//				stmt.close();
//				session.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return vos;
//
//	}

	public JSONArray queryJSONArray(String sql) {
		JSONArray results = new JSONArray();
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("sql", sql);
		List<Map<String, String>> ret = getBaseDAO().query(maps);
		if (ret != null && ret.size() > 0) {
			for (int i = 0; i < ret.size(); i++) {
				Map<String, String> v = ret.get(i);
				JSONObject vo = mapjson(v);
				results.add(vo);
			}
		}
		return results;
	}

	public List<JSONObject> queryJSON(String sql) {
		List<JSONObject> results = new ArrayList<JSONObject>();
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("sql", sql);
		List<Map<String, String>> ret = getBaseDAO().query(maps);
		if (ret != null && ret.size() > 0) {
			for (int i = 0; i < ret.size(); i++) {
				Map<String, String> v = ret.get(i);
				JSONObject vo = mapjson(v);
				results.add(vo);
			}
		}
		return results;
	}

	public List<T> queryT(String sql) {
		List<T> results = new ArrayList<T>();
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("sql", sql);
		List<Map<String, String>> ret = getBaseDAO().query(maps);
		if (ret != null && ret.size() > 0) {
			for (int i = 0; i < ret.size(); i++) {
				Map<String, String> v = ret.get(i);
				JSONObject vo = mapjson(v);
				String json = vo.toJSONString();
				Class<T> tt = getClasses();
				try {
					Object t = JSON.parseObject(json, tt);
					results.add((T) t);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return results;
	}

	protected TableObject map(Map<String, String> v) {
		TableObject vo = new TableObject();
		for (Map.Entry<String, String> entry : v.entrySet()) {
			Object val = entry.getValue();
			if (val != null) {
				String type = val.getClass().getName();
				if (type.equalsIgnoreCase("java.lang.String")) {
					type = TableObject.STRING;
				} else if (type.equalsIgnoreCase("java.lang.Integer")) {
					type = TableObject.INTEGER;
				} else {
					type = TableObject.STRING;
				}
				vo.setAttribute(entry.getKey().toLowerCase(), String.valueOf(val), type);
			} else {
				vo.setAttribute(entry.getKey().toLowerCase(), null);
			}
		}
		return vo;
	}

	protected JSONObject mapjson(Map<String, String> v) {
		JSONObject vo = new JSONObject();
		for (Map.Entry<String, String> entry : v.entrySet()) {
			Object val = entry.getValue();
			if (val != null) {
				vo.put(entry.getKey().toLowerCase(), String.valueOf(val));
			} else {
				vo.put(entry.getKey().toLowerCase(), null);
			}
		}
		return vo;
	}

	private Class<T> getClasses() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		Class<T> entityClass = (Class) params[0];
		return entityClass;
	}

	private String getCountSql(String sql, String dialect) {
		String ret = "";
		if (dialect.equalsIgnoreCase("mysql")) {
			ret = "SELECT COUNT(*) num FROM (" + sql + ") aliasForPage";
		}
		return ret;
	}

	private String getPageSql(String sql, String dialect, int startRecord, int pageSize) {
		String ret = "";
		if (dialect.equalsIgnoreCase("mysql")) {
			ret = " select * from ( " + sql + " ) a limit " + (startRecord - 1) + ", " + pageSize;
		}
		return ret;
	}
}
