#Java使用MongoDB的CRUD示例#

##概述##
本文说明使用Java对MongoDB进行CURD操作的入门知识。

##环境##
OS: Windows8 64
JDK: 1.7.0_45
MongoDB: win32-i386-2.4.3
MongoDB驱动：mongo-2.10.1

##CRUD示例说明##
简单起见，本文以用户信息为例说明如何进行CRUD操作。用户信息包含account和用户名两个属性。

##程序要点说明##
本节说明Java访问MongoDB和传统SQL数据库不一样的地方

###连接数据库###
首先是获取数据库连接，通过创建MongoDB对象获得连接，因本文是连接本地的MongoDB服务器，端口也是标准端口，所以构造函数不需要传入参数。如果连接其他机器上MongoDB服务器，需要在构造函数中传入参数指定。

连接数据库代码:
```java
mongo = new Mongo();
```

###访问指定的数据库###
通过如下代码指定访问的数据库：
```java
db = mongo.getDB("temp");
```
上面的代码将当前数据库改为temp数据库。

###访问MongoDB的集合###
通过如下代码获得MongoDB的集合：
```java
users = db.getCollection("users");
```
如果此集合对象不存在，NongoDB会自动创建。

###创建文档###
使用MongoDB时，CRUD都是通过DBObject对象操作。比如要创建新的用户信息，代码如下：
```java
DBObject user = new BasicDBObject();
user.put("name", "张三");
user.put("account", "zhangsan");
users.insert(user);
```

###查询文档###
查询MongoDB也是通过DBObject对象，比如要查询name是“zhangsan”的用户信息，代码如下：
```java
List<DBObject> objs = users.find(new BasicDBObject("account", "zhangsan")).toArray();
```

###更新文档###
更新文档时有点要注意，需要指定MongoDB具体的更新命令，比如把name是“zhangsan”的用户名称改为“张三一”，代码如下：
```java
users.update(new BasicDBObject("account", account), 
    new BasicDBObject("$set",new BasicDBObject("name","张三")))
```

###删除文档###
删除文档时的条件指定也是通过DBObject对象，比如删除name是“zhangsan”的用户，代码如下：
```java
users.remove(new BasicDBObject("account", "zhangsan"))
```

##完整CRUD示例代码##
完整的CRUD示例代码如下：
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
		    //初始化数据库连接，取得users集合
			instance.init();
			//准备测试现场数据，删除用户名是“zhangsan”的数据
			instance.clear("zhangsan");
			
            //新增用户
			System.out.println("测试新增用户");
			instance.add();
			//查询新增用户
			System.out.println("测试查询用户");
			instance.find("zhangsan");
			
            //更新用户
			System.out.println("测试更新用户");
			instance.update("zhangsan", "张三1");
			//查询更新用户
			System.out.println("测试查询用户");
			instance.find("zhangsan");
			
            //删除用户
			System.out.println("测试删除用户");
			instance.delete("zhangsan");
            //查询用户
    		System.out.println("测试查询用户");
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
```

#示例工程下载#
本文的示例工程可以从github上下载，下载地址：
