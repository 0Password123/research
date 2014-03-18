#Javaʹ��MongoDB��CRUDʾ��#

##����##
����˵��ʹ��Java��MongoDB����CURD����������֪ʶ��

##����##
OS: Windows8 64
JDK: 1.7.0_45
MongoDB: win32-i386-2.4.3
MongoDB������mongo-2.10.1

##CRUDʾ��˵��##
��������������û���ϢΪ��˵����ν���CRUD�������û���Ϣ����account���û����������ԡ�

##����Ҫ��˵��##
����˵��Java����MongoDB�ʹ�ͳSQL���ݿⲻһ���ĵط�

###�������ݿ�###
�����ǻ�ȡ���ݿ����ӣ�ͨ������MongoDB���������ӣ����������ӱ��ص�MongoDB���������˿�Ҳ�Ǳ�׼�˿ڣ����Թ��캯������Ҫ��������������������������MongoDB����������Ҫ�ڹ��캯���д������ָ����

�������ݿ����:
```java
mongo = new Mongo();
```

###����ָ�������ݿ�###
ͨ�����´���ָ�����ʵ����ݿ⣺
```java
db = mongo.getDB("temp");
```
����Ĵ��뽫��ǰ���ݿ��Ϊtemp���ݿ⡣

###����MongoDB�ļ���###
ͨ�����´�����MongoDB�ļ��ϣ�
```java
users = db.getCollection("users");
```
����˼��϶��󲻴��ڣ�NongoDB���Զ�������

###�����ĵ�###
ʹ��MongoDBʱ��CRUD����ͨ��DBObject�������������Ҫ�����µ��û���Ϣ���������£�
```java
DBObject user = new BasicDBObject();
user.put("name", "����");
user.put("account", "zhangsan");
users.insert(user);
```

###��ѯ�ĵ�###
��ѯMongoDBҲ��ͨ��DBObject���󣬱���Ҫ��ѯname�ǡ�zhangsan�����û���Ϣ���������£�
```java
List<DBObject> objs = users.find(new BasicDBObject("account", "zhangsan")).toArray();
```

###�����ĵ�###
�����ĵ�ʱ�е�Ҫע�⣬��Ҫָ��MongoDB����ĸ�����������name�ǡ�zhangsan�����û����Ƹ�Ϊ������һ�����������£�
```java
users.update(new BasicDBObject("account", account), 
    new BasicDBObject("$set",new BasicDBObject("name","����")))
```

###ɾ���ĵ�###
ɾ���ĵ�ʱ������ָ��Ҳ��ͨ��DBObject���󣬱���ɾ��name�ǡ�zhangsan�����û����������£�
```java
users.remove(new BasicDBObject("account", "zhangsan"))
```

##����CRUDʾ������##
������CRUDʾ���������£�
```java

package com.github.hubin0011.research.mongodb.curd;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

/**
 * MongoDB����ɾ�Ĳ�
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
		    //��ʼ�����ݿ����ӣ�ȡ��users����
			instance.init();
			//׼�������ֳ����ݣ�ɾ���û����ǡ�zhangsan��������
			instance.clear("zhangsan");
			
            //�����û�
			System.out.println("���������û�");
			instance.add();
			//��ѯ�����û�
			System.out.println("���Բ�ѯ�û�");
			instance.find("zhangsan");
			
            //�����û�
			System.out.println("���Ը����û�");
			instance.update("zhangsan", "����1");
			//��ѯ�����û�
			System.out.println("���Բ�ѯ�û�");
			instance.find("zhangsan");
			
            //ɾ���û�
			System.out.println("����ɾ���û�");
			instance.delete("zhangsan");
            //��ѯ�û�
    		System.out.println("���Բ�ѯ�û�");
			instance.find("zhangsan");

			
		} catch (Exception e) {
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
		user.put("name", "����");
		user.put("account", "zhangsan");
		WriteResult result = users.insert(user);
		System.out.println(result.getN() + "," + result.getLastError());
		System.out.println("�����ĵ���" + user.toString());
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
```

#ʾ����������#
���ĵ�ʾ�����̿��Դ�github�����أ����ص�ַ��
