package com.github.hubin0011.research.mongodb.crud;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

/**
 * MongoDB的增删改查
 * 
 *
 */
public class MongoDbCRUD {

	private Mongo mongo;
	private DB db;
	private DBCollection users;
	
	public static void main(String[] args) {
		MongoDbCRUD instance = new MongoDbCRUD();
		try {
			instance.init();
			
			instance.clear("zhangsan");
			
			System.out.println("测试新增用户");
			instance.add();
			System.out.println("测试查询用户");
			instance.find("zhangsan");
			
			System.out.println("测试更新用户");
			instance.update("zhangsan", "张三1");
			System.out.println("测试查询用户");
			instance.find("zhangsan");
			
			System.out.println("测试删除用户");
			instance.delete("zhangsan");
			System.out.println("测试查询用户");
			instance.find("zhangsan");

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void init() throws Exception{
		mongo = new Mongo();
		db = mongo.getDB("temp");
		users = db.getCollection("users");
	}

	
	private void clear(String name){
		users.remove(new BasicDBObject("account", name));
	}
	
	private void add(){
		DBObject user = new BasicDBObject();
		user.put("name", "张三");
		user.put("account", "zhangsan");
		WriteResult result = users.insert(user);
		System.out.println(result.getN() + "," + result.getLastError());
		System.out.println("插入文档：" + user.toString());
	}
	
	private void find(String account){
		List<DBObject> objs = users.find(new BasicDBObject("account", "zhangsan")).toArray();
		for(DBObject obj : objs){
			System.out.println(obj.toString());
		}
	}
	
	private void delete(String account){
		System.out.println(users.remove(new BasicDBObject("account", account)));
	}
	
	private void update(String account, String name){
		System.out.println(
				users.update(new BasicDBObject("account", account), 
						new BasicDBObject("$set",new BasicDBObject("name",name))));
		
	}
}
