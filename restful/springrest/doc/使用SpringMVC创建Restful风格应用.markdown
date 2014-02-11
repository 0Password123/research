#使用SpringMVC创建Restfult风格应用


##目的
本文介绍如何使用SpringMVC搭建Restful风格的应用。

为更容易理解，以维护用户信息为例，说明如何实现restful的增删改查和列表功能。

简单起见，用户信息（User）包括：用户Id、email地址两个属性，其中用户Id是主键。

##环境和要求
本示例在以下环境中测试通过：
OS：WindowsXP SP3

JDK：1.6.0_26

Servlet：2.5

SpringMVC：3.2.3.RELEASE

Tomcat：6.0.33

MySql：5.6

Maven：3.0.3

crul：7.34.0-win32

##用户资源的URI定义

|用户资源操作|资源URI       |方法动词|参数                                     |
|----------|-------------|-------|----------------------------------------|
|创建用户    |/user        |POST   |请求体中是JSON格式的User                   |
|查询用户    |/user/{id}   |GET    |id:User的Id                           |
|更新用户    |/user/{id}   |PUT    |id:User的Id，请求体中是JSON格式的User    |
|删除用户    |/user/{id}   |DELETE |id:User的Id                           |
|用户列表    |/user/list   |GET    |无                                       |


##创建工程
本节说明如何创建示例工程。

###创建工程命令
工程创建使用Maven，创建命令如下：

```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=rest-springmvc  -DarchetypeArtifactId=maven-archetype-webapp
```

###添加依赖
Maven工程创建成功后，在pom.xml中添加如下依赖：
```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>servlet-api</artifactId>
    <version>2.5</version>
    <scope>provided</scope>
</dependency>

<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
    <scope>provided</scope>
</dependency>

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>3.2.3.RELEASE</version>
</dependency>

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.2.1</version>
</dependency>

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.25</version>
</dependency> 

```

###工程配置
####配置SpringMVC前端拦截器。在web.xml中加入下面配置：

```xml
<servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>
        org.springframework.web.servlet.DispatcherServlet
    </servlet-class>
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:web-servlet.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>dispatcher</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

####配置SpringMVC的组件扫描。在web-servlet.xml中配置Spring的组件扫描：


```xml
<context:annotation-config />

<mvc:annotation-driven
	content-negotiation-manager="contentNegotiationManager">
</mvc:annotation-driven>

<context:component-scan base-package="com.github.hubin0011" >

</context:component-scan>

<bean id="contentNegotiationManager" class="org.springframework.web.accept.ContentNegotiationManagerFactoryBean">
	<property name="favorPathExtension" value="false" />
</bean>

<bean
	class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	<property name="prefix" value="/WEB-INF/jsp/" />
	<property name="suffix" value=".jsp" />
</bean>

```

##实现Restful

之前的章节完成了工程创建和配置，本节介绍具体Restful的实现。

###创建用户实体类
首先，先创建用户实体类。代码如下：
```java
package com.github.hubin0011.rest.spring.domain;

public class User {

    private String id;

    private String email;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}

```

###创建用户的Restful服务
创建用户、查询用户、更新用户、删除用户和用户列表的Restful服务的方法都在UserRestController类中实现。具体实现如下：

```java
package com.github.hubin0011.rest.spring.controller;

import com.github.hubin0011.rest.spring.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserRestController {

    private List<User> users = new ArrayList<User>();

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus create(@RequestBody User user){
        users.add(user);
        return HttpStatus.CREATED;
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET)
    @ResponseBody
    public User get(@PathVariable("id")String id){
        if(id == null) {
            return null;
        }

        for(User user : users){
            if(user.getId().equals(id)){
                return user;
            }
        }

        return null;
    }

    @RequestMapping(value="{id}",method = RequestMethod.PUT)
    @ResponseBody
    public HttpStatus update(@PathVariable("id")String id, @RequestBody User user){
        for(User aUser : users){
            if(aUser.getId().equals(id)){
                aUser.setEmail(user.getEmail());
                return HttpStatus.OK;
            }
        }

        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value="{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public HttpStatus delete(@PathVariable("id")String id){
        for(User aUser : users){
            if(aUser.getId().equals(id)){
                users.remove(aUser);
                return HttpStatus.OK;
            }
        }

        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value="list",method = RequestMethod.GET)
    @ResponseBody
    public List<User> list(){
        return users;
    }
}


```

###测试Restful服务
首先部署应用到Tomcat中，并启动Tomcat。之后用curl工具测试restful服务。

#### 创建用户
在curl的bin目录下，执行测试命令和返回结果如下：

```
curl.exe -X POST -H "Content-Type: application/json" -d "{\"id\":\"hubin0011\",\"email\":\"demo\"}" http://localhost:8080/rest-springmvc/user

"CREATED"
```

####查询用户
在curl的bin目录下，执行测试命令和返回结果如下：

```
curl.exe http://localhost:8080/rest-springmvc/user/hubin0011

{"id":"hubin0011","email":"demo"}
```

####更新用户
在curl的bin目录下，执行测试命令和返回结果如下：

```
curl.exe -X PUT -H "Content-Type: application/json" -d "{\"id\":\"hubin0011\",\"email\":\"demo1\"}" http://localhost:8080/rest-springmvc/user/hubin0011

"OK"


curl.exe http://localhost:8080/rest-springmvc/user/hubin0011
{"id":"hubin0011","email":"demo1"}
```

####删除用户
在curl的bin目录下，执行测试命令和返回结果如下：

```
curl.exe -X DELETE http://localhost:8080/rest-springmvc/user/hubin0011

"OK"


curl.exe http://localhost:8080/rest-springmvc/user/hubin0011

```

####用户列表
在curl的bin目录下，执行测试命令和返回结果如下：

```
curl.exe -X POST -H "Content-Type: application/json" -d "{\"id\":\"hubin0012\",\"email\":\"demo2\"}" http://localhost:8080/rest-springmvc/user

"CREATED"

curl.exe -X POST -H "Content-Type: application/json" -d "{\"id\":\"hubin0013\",\"email\":\"demo3\"}" http://localhost:8080/rest-springmvc/user

"CREATED"


curl.exe http://localhost:8080/rest-springmvc/user/list

[{"id":"hubin0012","email":"demo2"},{"id":"hubin0013","email":"demo3"}]
```


