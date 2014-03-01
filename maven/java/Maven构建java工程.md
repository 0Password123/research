Maven构建Java工程
#概述#
本文描述如何用Maven管理和构建Java工程。

#软件环境#
Java:1.6.0_26
Maven:3.0.4
OS:WindowXP SP3

#创建工程#
##创建脚本##
Maven创建Java工程脚本：
```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=research_maven_java -DarchetypeArtifactId=maven-archetype-quickstart
```
要说明的参数
* groupId: 公司名称，一般是域名反写，也是工程的默认包名。
* artifactId: 构建的名称。
* archetypeArtifactId: 创建工程的类型，“maven-archetype-quickstart”代表一般的Java工程。

##修改工程编译版本##
Maven创建的Java工程默认的编译版本是1.5，可以在pom.xml中指定编译版本：
```xml
<project>  
  [...]  
  <build>  
    [...]  
    <plugins>  
      <plugin>  
        <groupId>org.apache.maven.plugins</groupId>  
        <artifactId>maven-compiler-plugin</artifactId>  
        <version>3.1</version>  
        <configuration>  
          <source>1.7</source>  
          <target>1.7</target>  
        </configuration>  
      </plugin>  
    </plugins>  
    [...]  
  </build>  
  [...]  
</project>
```
修改后的pom.xml文件如下：
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.github.hubin0011</groupId>
  <artifactId>research_maven_java</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>research_maven_java</name>
  <url>http://maven.apache.org</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	<maven.compiler.source>1.6</maven.compiler.source>  
    <maven.compiler.target>1.6</maven.compiler.target>  
    <maven.compiler.compilerVersion>1.6</maven.compiler.compilerVersion> 
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>

    <build>   
    <plugins>  
      <plugin>  
        <groupId>org.apache.maven.plugins</groupId>  
        <artifactId>maven-compiler-plugin</artifactId>  
        <version>3.1</version>  
        <configuration>  
          <source>1.7</source>  
          <target>1.7</target>  
        </configuration>  
      </plugin>  
    </plugins>
  </build>
</project>
```

##Java工程标准结构##
创建工程后会生成文件结构，其中：
* src\main\java： 工程Java源代码目录
* src\test\java： 工程Java测试源代码目录
还有两个目录也是标准目录，只是没有被自动创建，分别是：
* src\main\resources： 工程的资源文件
* src\test\resources： 测试用的资源文件
通过如下命令创建这两个目录：
```
mkdir src\main\resources
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


#发布构件到本地仓库#
要将构件发布到本地的Maven仓库可以使用下面命令：
```
mvn install
```

#公司级Maven仓库#
要构建公司级的Maven仓库，可以使用Nexus私服。具体可以参考Nexus的相关资料。


#下载依赖构件的源代码和JavaDoc#
执行下面命令可以下载依赖构件的源代码和JavaDoc
```
mvn dependency:resolve -Dclassifier=sources  
mvn dependency:resolve -Dclassifier=javadoc 
```
