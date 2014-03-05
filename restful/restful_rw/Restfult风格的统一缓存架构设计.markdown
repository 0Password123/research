#Restfult风格的统一缓存架构设计#


##概述##
基于Restful的架构风格已被广泛接收，因为Restful对资源URI的命名要求、对资源操作明确的动词语义，在很大程度上影响了Web应用的整体架构设计。

本文以一个简单的CURD操作为例，讨论基于Restful的缓存设计，抛砖引玉，共通探讨。

##环境和要求##
本示例在以下环境中测试通过：
OS：WindowsXP SP3

JDK：1.6.0_26

Servlet：2.5

SpringMVC：3.2.3.RELEASE

Tomcat：6.0.33

Maven：3.0.3

crul：7.34.0-win32

##简单的CURD示例示例说明##

简单起见，以用户信息（User）的CURD操作为例，用户信息包括：用户Id、email地址两个属性，其中用户Id是主键。

用户资源的URI约定如下：


|用户资源操作|资源URI       |方法动词|参数                                     |
|----------|-------------|-------|----------------------------------------|
|创建用户    |/user        |POST   |请求体中是JSON格式的User                   |
|查询用户    |/user/{id}   |GET    |id:User的Id                           |
|更新用户    |/user/{id}   |PUT    |id:User的Id，请求体中是JSON格式的User    |
|删除用户    |/user/{id}   |DELETE |id:User的Id                           |


##创建工程##
本节说明如何创建示例工程。

###创建工程命令###
工程创建使用Maven，创建命令如下：

```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=rest-rw  -DarchetypeArtifactId=maven-archetype-webapp
```

###添加依赖###
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
```

###工程配置###

创建工程后，需要对SpringMVC做一些配置

####配置SpringMVC前端拦截器####
在web.xml中加入下面配置：

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

####配置SpringMVC的组件扫描。####
在web-servlet.xml中配置Spring的组件扫描：

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

##实现示例Restful##

之前的章节完成了工程创建和配置，本节介绍具体Restful的实现。

###创建用户实体类###
首先，先创建用户实体类。代码如下：
```java
package com.github.hubin0011.rest.rw.domain;


import com.github.hubin0011.rest.rw.ICachableDomain;

public class User implements ICachableDomain {

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

为了能够统一的取得资源id，资源需要实现ICachableDomain接口，ICachableDomain接口定义如下：

```
package com.github.hubin0011.rest.rw;


public interface ICachableDomain {

    public String getId();
}
```

###创建用户的Restful服务###
创建用户、查询用户、更新用户、删除用户的Restful服务的方法都在UserRestController类中实现。具体实现如下：

```java
package com.github.hubin0011.rest.rw.controller;

import com.github.hubin0011.rest.rw.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserRestController {

    private List<User> users = new ArrayList<User>();
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus create(@RequestBody User user){
        System.out.println("in Request Post ");
        users.add(user);
        return HttpStatus.CREATED;
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET)
    @ResponseBody
    public User get(@PathVariable("id")String id){
        System.out.println("in Request Get ");
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
        System.out.println("in Request Put ");

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
        System.out.println("in Request Delete ");
        for(User aUser : users){
            if(aUser.getId().equals(id)){
                users.remove(aUser);
                return HttpStatus.OK;
            }
        }

        return HttpStatus.BAD_REQUEST;
    }

}
```


##缓存统一处理##
因为Restful对资源操作有明确的动词语义，再配合AOP的拦截机制，架构设计上就能够根据动词的含义对读写操作进行统一的处理。

下面是一个简单的AOP拦截器的实现，要注意的是下面的拦截器只是是简单的、实验性质的，只作为抛砖引玉用。

```
package com.github.hubin0011.rest.rw.aspect;


import com.github.hubin0011.rest.rw.ICachableDomain;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class RestfulRWAspect {

    private Map<String,Object> cachedMap = new HashMap<String, Object>();

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 拦截Restful请求，根据请求的方法进行资源缓存处理。
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(public * com.github.hubin0011.rest.rw.controller.*RestController.*(..))")
    public Object intercept(ProceedingJoinPoint pjp) throws Throwable{
        //取得请求方法类别
        RequestMethod requestMethod = getRequestMethod(pjp);
        //取得请求资源的id
        String id = getParamId(pjp);

        //判断是GET请求，如果缓存中存在请求资源，则直接从缓存中返回资源。
        if(requestMethod == RequestMethod.GET && id != null){
            if(cachedMap.containsKey(id)){
                System.out.println("直接从缓存中返回资源。资源id:" + id);
                return cachedMap.get(id);
            }
        }

        //继续进行拦截链，取得执行结果
        Object obj = pjp.proceed();

        //判断是POST请求，则将资源信息加入到缓存中
        if(requestMethod == RequestMethod.POST ){
            //取得资源信息数据
            Object resource = getPostDomain(pjp);
            if(resource instanceof ICachableDomain){
                ICachableDomain domain = (ICachableDomain)resource;
                //取得资源id
                String newId = domain.getId();
                if(newId != null){
                    System.out.println("将资源添加到缓存。资源id:" + newId);
                    cachedMap.put(newId,resource);
                }
            }

        }

        //判断是PUT请求，则将资源信息更新到缓存中
        if(requestMethod == RequestMethod.PUT){
            if(id != null){
                Object param = getPutDomain(pjp);
                System.out.println("从缓存中更新相应资源。资源id:" + id);
                cachedMap.put(id,param);
            }
        }

        //判断是DELETE请求，则从缓存中删除相应资源
        if(requestMethod == RequestMethod.DELETE){
            if(id != null){
                System.out.println("从缓存中删除相应资源。资源id:" + id);
                cachedMap.remove(id);
            }
        }

        //返回执行结果
        log.debug("返回执行结果");
        return obj;
    }

    /**
     * 取得Restful请求的资源id
     *
     * @param pjp
     * @return
     */
    protected String getParamId(ProceedingJoinPoint pjp){
        String id = null;

        Object[] args = pjp.getArgs();
        if(args.length >= 1){
            id = args[0].toString();
        }

        return id;
    }

    /**
     * 取得Post请求时资源的信息数据
     * @param pjp
     * @return
     */
    protected Object getPostDomain(ProceedingJoinPoint pjp){
        Object obj = null;

        Object[] args = pjp.getArgs();
        if(args.length == 1){
            obj = args[0];
        }

        return obj;
    }

    /**
     * 取得Put请求时资源的信息数据
     * @param pjp
     * @return
     */
    protected Object getPutDomain(ProceedingJoinPoint pjp){
        Object obj = null;

        Object[] args = pjp.getArgs();
        if(args.length == 2){
            obj = args[1];
        }

        return obj;
    }

    /**
     * 取得Restful请求的方法
     * @param pjp
     * @return
     */
    protected RequestMethod getRequestMethod(ProceedingJoinPoint pjp){
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method targetMethod = methodSignature.getMethod();
        RequestMapping methodRequestMapping = AnnotationUtils.findAnnotation(targetMethod, RequestMapping.class);
        RequestMethod[] methods = methodRequestMapping.method();

        if(methods.length <= 0){
            return null;
        }
        return methods[0];
    }
}
```

##测试##
首先部署应用到Tomcat中，并启动Tomcat。之后用curl工具测试缓存拦截器。

###创建用户###
在curl的bin目录下，执行测试命令和返回结果如下：

```
curl.exe -X POST -H "Content-Type: application/json" -d "{\"id\":\"hubin0011\",\"email\":\"demo\"}" http://localhost:8080/rest-rw/user

"CREATED"
```

**服务端日志：**

```
in Request Post 
将资源添加到缓存。资源id:hubin0011
```

###查询用户###
在curl的bin目录下，执行测试命令和返回结果如下：

```
curl.exe http://localhost:8080/rest-springmvc/user/hubin0011

{"id":"hubin0011","email":"demo"}
```

**服务端日志：**

```
直接从缓存中返回资源。资源id:hubin0011
```

可以看到结果是从缓存中返回。

###更新用户###
测试更新用户，命令和如下：

```
curl.exe -X PUT -H "Content-Type: application/json" -d "{\"id\":\"hubin0011\",\"email\":\"demo1\"}" http://localhost:8080/rest-springmvc/user/hubin0011

"OK"
```

**服务端日志：**

```
in Request Put 
从缓存中更新相应资源。资源id:hubin0011
```

查询用户检验更新结果：
```
curl.exe http://localhost:8080/rest-springmvc/user/hubin0011

{"id":"hubin0011","email":"demo1"}
```

**服务端日志：**

```
直接从缓存中返回资源。资源id:hubin0011
```

可以看到结果是从缓存中返回，并且缓存数据已经更新。


###删除用户###
测试删除用户，命令和如下：

```
curl.exe -X DELETE http://localhost:8080/rest-springmvc/user/hubin0011

"OK"
```

**服务端日志：**

```
in Request Delete 
从缓存中删除相应资源。资源id:hubin0011
```

查询用户检验删除结果：
```
curl.exe http://localhost:8080/rest-springmvc/user/hubin0011
```

**服务端日志：**

```
in Request Get 
```


#示例代码下载
本文中的示例代码可以从下面地址下载：
