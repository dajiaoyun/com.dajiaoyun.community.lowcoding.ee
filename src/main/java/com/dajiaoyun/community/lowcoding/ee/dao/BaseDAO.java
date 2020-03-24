package com.dajiaoyun.community.lowcoding.ee.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface BaseDAO {
	public List<Map<String,String>> query(Map<String,Object> maps);
	
	public Map<String,String> find(Map<String,Object> maps);
	
	public void insert(Map<String,Object> maps);
	
	public void batchInsert(Map<String,Object> maps);
	
	public void update(Map<String,Object> maps);
	
	public void remove(Map<String,Object> maps);
	
	public void execute(Map<String,Object> maps);
}
