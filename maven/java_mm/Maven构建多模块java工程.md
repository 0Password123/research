Maven构建多模块Java工程
#概述#
项目开发时，通常会将项目分为多个模块进行开发，本文讨论如何用Maven构建多模块的Java工程。

#软件环境#
Java:1.6.0_26
Maven:3.1.1
OS:WindowXP SP3

#项目模块说明#
示例工程分为多个模块，分别是：
* research_maven_javamm：父工程，聚合各模块。
* App：主模块，依赖ModuleA、ModuleB和ModuleCom
* ModuleA：模块A，依赖ModuleCom
* ModuleB：模块B，依赖ModuleCom
* ModuleCom：共通模块。

多模块项目整体构建使用Maven的父子继承能力。

#创建工程#
首先创建工程和各模块，然后配置各模块之间的依赖关系。

项目的对第三方构件的依赖关系配置在父工程的pom.xml中，这样各模块可以省去第三方构件的依赖配置。

##创建父工程##
首先创建父工程应用
###创建脚本###
```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=research_maven_javamm -DarchetypeArtifactId=maven-archetype-quickstart
```
###修改父工程为Maven的父工程###
创建成功后，需要将父工程类型改为Maven的父工程，修改pom.xml文件的packaging属性为pom，如下：
```xml
<packaging>pom</packaging>
```

##创建各模块工程##
父工程创建成功后，创建各模块工程。

**注意：创建各模块工程时，要先cd到父工程的根目录:**
```
cd research_maven_javamm
```

###创建App主模块###
```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=app -DarchetypeArtifactId=maven-archetype-quickstart
```

###创建ModuleA工程###
```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=module-a -DarchetypeArtifactId=maven-archetype-quickstart
```

###创建ModuleB工程###
```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=module-b -DarchetypeArtifactId=maven-archetype-quickstart
```

###创建ModuleC工程###
```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=module-com -DarchetypeArtifactId=maven-archetype-quickstart
```

创建各模块后，可以看到父工程research_maven_javamm的pom.xml文件中添加了子工程的信息：
```xml
  <modules>
    <module>app</module>
    <module>module-a</module>
    <module>module-b</module>
    <module>module-com</module>
  </modules>
```
##配置各模块依赖关系##
创建各模块工程后，需要对各模块工程之间的依赖关系进行配置。如之前说明的模块依赖关系：
* ModuleA依赖ModuleCom
* ModuleB依赖ModuleCom
* 主模块app依赖ModuleA、ModuleB和ModuleCom

###配置ModuleA依赖ModuleCom###
修改ModuleA的pom.xml文件，添加ModuleCom的依赖：
```xml
    <dependency>
		<groupId>com.github.hubin0011</groupId>
		<artifactId>module-com</artifactId>
		<version>1.0-SNAPSHOT</version>
    </dependency>
```
###配置ModuleB依赖ModuleCom###
修改ModuleB的pom.xml文件，添加ModuleCom的依赖：
```xml
    <dependency>
		<groupId>com.github.hubin0011</groupId>
		<artifactId>module-com</artifactId>
		<version>1.0-SNAPSHOT</version>
    </dependency>
```
###配置主模块app依赖ModuleA、ModuleB和ModuleCom###
修改app的pom.xml文件，添加ModuleA、ModuleB和ModuleCom的依赖：
```xml
    <dependency>
		<groupId>com.github.hubin0011</groupId>
		<artifactId>module-a</artifactId>
		<version>1.0-SNAPSHOT</version>
    </dependency>
    <dependency>
		<groupId>com.github.hubin0011</groupId>
		<artifactId>module-b</artifactId>
		<version>1.0-SNAPSHOT</version>
    </dependency>
    <dependency>
		<groupId>com.github.hubin0011</groupId>
		<artifactId>module-com</artifactId>
		<version>1.0-SNAPSHOT</version>
    </dependency>
```

#编译工程#
##编译模块工程##
在模块工程的目录，执行如下脚本可以编译该模块工程：
```
mvn clean compile
```
##编译父工程##
在父工程的目录，执行如下脚本则编译整个工程：
```
mvn clean compile
```

#执行单体测试#
##对模块工程进行单体测试##
在模块工程的目录，执行如下脚本可以运行该模块工程的单体测试：
```
mvn clean test
```
##对整个工程进行单体测试##
在父工程的目录，执行如下脚本则运行整个工程的单体测试：
```
mvn clean test
```

#打包#
##打包模块工程##
在模块工程的目录，执行如下脚本可以打包该模块：
```
mvn clean package
```
##打包整个工程##
在父工程的目录，执行如下脚本则打包所有模块，在各模块的target目录会生成相应的jar构件：
```
mvn clean package
```

#发布构件到本地仓库#
##发布模块工程到本地仓库##
在模块工程的目录，执行如下脚本可以发布该模块工程到本地仓库：
```
mvn clean install
```
##发布整个工程##
在父工程的目录，执行如下脚本则发布整个工程到本地仓库：
```
mvn clean install
```

#最佳实践#
* 父工程不应该有代码，所有的代码都应该查分到子模块中开发
* 工程共通依赖的第三方构件应该在父工程的pom.xml中维护，各模块依赖的特殊构件可以在各模块的pom.xml中维护
