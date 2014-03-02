Maven构建Web工程和Tomcat7插件使用
#概述#
本文描述如何用Maven管理和构建JavaWeb工程。

#软件环境#
Java:1.6.0_26
Maven:3.1.1
OS:WindowXP SP3
Tomcat：7.0.47

#创建工程#
##创建脚本##
Maven创建JavaWeb工程脚本：
```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=research_maven_web -DarchetypeArtifactId=maven-archetype-webapp
```
要说明的参数
* groupId: 公司名称，一般是域名反写，也是工程的默认包名。
* artifactId: 构建的名称。
* archetypeArtifactId: 创建工程的类型，“maven-archetype-webapp”表示是Web工程。

##Web工程标准结构##
创建工程后会生成文件结构，其中：
* src\main\resources： 工程资源文件目录
* src\main\webapp： 工程web应用目录

一般的web工程还需要创建如下目录：
* src\main\java： 工程的Java源代码文件
* src\test\java： 测试用的Java源文件文件
* src\test\resources： 测试用的资源文件
通过如下命令创建这两个目录：
```
mkdir src\main\java
mkdir src\test\java
mkdir src\test\resources
```

#编译工程#
在Maven工程的根目录，执行如下编译脚本可以编译整个工程：
```
mvn compile
```

#执行单体测试#
TDD模式开发或者做CI时，需要经常执行单体测试，一下命令可以执行工程的全部单体测试类
```
mvn test
```

#打包#
执行如下脚本可以将整个工程打包成：
```
mvn package
```
package命令会先执行compile、test命令。

#部署Web工程到Tomcat#
##配置Tomcat插件##
部署到Tomcat需要相应的插件，Tomcat6和7需要不同的插件，将下面配置添加到pom.xml文件中：
```xml
	<pluginManagement>
      <plugins>
        <plugin>
          <groupId>org.apache.tomcat.maven</groupId>
          <artifactId>tomcat6-maven-plugin</artifactId>
          <version>2.2</version>
          <configuration>  
              <url>http://localhost:8080/manager/html</url>   
              <server>tomcat</server>
            </configuration>
        </plugin>
        <plugin>
          <groupId>org.apache.tomcat.maven</groupId>
          <artifactId>tomcat7-maven-plugin</artifactId>
          <version>2.2</version>
          <configuration>  
              <url>http://localhost:8080/manager/text</url>   
              <server>tomcat</server>
            </configuration>
        </plugin>
      </plugins>
    </pluginManagement>
```

**注意：Tomcat7的url后面必须是text，而不能是html。否则发布Web工程时会提示权限不足。**

##配置Maven连接Tomcat的用户信息##
在%MAVEN_HOME%\conf\settings.xml文件的servers节点中，增加以下配置：
```xml
<server>  
       <id>tomcat</id>  
       <username>admin</username>  
       <password>admin</password>  
</server>  
```

##配置Tomcat认证信息##
在%TOMCAT_HOME%\conf\tomcat_user.xml文件中增加以下角色和用户，用于tomcat_maven_plugin自动部署工程：
```xml
<role rolename="manager-gui"/>    
<role rolename="manager-script"/>    
<user username="admin" password="admin" roles="manager-gui, manager-script"/>  
```

##启动Tomcat##
启动Tomcat-7.0.47

##部署Web工程##
###以Web包形式部署###
执行下面命令：
```
mvn package tomcat6/7:redeploy
```
###以文件夹形式部署###
执行下面命令：
```
mvn war:exploded tomcat6/7:redeploy
```

##卸载Web工程##
执行下面命令：
```
mvn tomcat7:undeploy
```

#示例工程下载#
本文所用的示例工程和配置文件可以从下面地址下载：
[示例工程](git "")