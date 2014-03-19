package com.github.hubin0011.research.mongodb.springdata;


import com.github.hubin0011.research.mongodb.springdata.domain.User;
import com.mongodb.DBCollection;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class CRUDDemo {

    public static void main(String[] args){
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("app-context.xml");

        MongoTemplate mongoTemplate = (MongoTemplate) appCtx.getBean("mongoTemplate");
        String collectionName = "users";

        User user = new User();
        user.setName("zhangsan");
        user.setEmail("zhangsan@163.com");

        //插入数据
        System.out.println("插入数据");
        mongoTemplate.save(user,collectionName);

        //查询数据
        User aUser = mongoTemplate.findOne(new Query(Criteria.where("name").is("zhangsan")), User.class, collectionName);
        System.out.println("查询结果：" + aUser);

        //更新数据
        System.out.println("更新数据");
        mongoTemplate.updateFirst(new Query(Criteria.where("name").is("zhangsan")),
                Update.update("email", "zhangsan@gmail.com"),
                collectionName);
        //查询数据
        aUser = mongoTemplate.findOne(new Query(Criteria.where("name").is("zhangsan")), User.class, collectionName);
        System.out.println("查询结果：" + aUser);


        //删除数据
        System.out.println("删除数据");
        mongoTemplate.remove(new Query(Criteria.where("name").is("zhangsan")), User.class, collectionName);
        //查询数据
        aUser = mongoTemplate.findOne(new Query(Criteria.where("name").is("zhangsan")), User.class, collectionName);
        System.out.println("查询结果：" + aUser);
    }
}
