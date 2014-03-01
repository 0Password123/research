Maven����Java����
#����#
�������������Maven����͹���Java���̡�

#�������#
Java:1.6.0_26
Maven:3.0.4
OS:WindowXP SP3

#��������#
##�����ű�##
Maven����Java���̽ű���
```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=research_maven_java -DarchetypeArtifactId=maven-archetype-quickstart
```
Ҫ˵���Ĳ���
* groupId: ��˾���ƣ�һ����������д��Ҳ�ǹ��̵�Ĭ�ϰ�����
* artifactId: ���������ơ�
* archetypeArtifactId: �������̵����ͣ���maven-archetype-quickstart������һ���Java���̡�

##�޸Ĺ��̱���汾##
Maven������Java����Ĭ�ϵı���汾��1.5��������pom.xml��ָ������汾��
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
�޸ĺ��pom.xml�ļ����£�
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

##Java���̱�׼�ṹ##
�������̺�������ļ��ṹ�����У�
* src\main\java�� ����JavaԴ����Ŀ¼
* src\test\java�� ����Java����Դ����Ŀ¼
��������Ŀ¼Ҳ�Ǳ�׼Ŀ¼��ֻ��û�б��Զ��������ֱ��ǣ�
* src\main\resources�� ���̵���Դ�ļ�
* src\test\resources�� �����õ���Դ�ļ�
ͨ�����������������Ŀ¼��
```
mkdir src\main\resources
mkdir src\test\resources
```

#���빤��#
��Maven���̵ĸ�Ŀ¼��ִ�����±���ű����Ա����������̣�
```
mvn compile
```

#ִ�е������#
TDDģʽ����������CIʱ����Ҫ����ִ�е�����ԣ�һ���������ִ�й��̵�ȫ�����������
```
mvn test
```

#���#
ִ�����½ű����Խ��������̴���ɣ�
```
mvn package
```
package�������ִ��compile��test���


#�������������زֿ�#
Ҫ���������������ص�Maven�ֿ����ʹ���������
```
mvn install
```

#��˾��Maven�ֿ�#
Ҫ������˾����Maven�ֿ⣬����ʹ��Nexus˽����������Բο�Nexus��������ϡ�


#��������������Դ�����JavaDoc#
ִ���������������������������Դ�����JavaDoc
```
mvn dependency:resolve -Dclassifier=sources  
mvn dependency:resolve -Dclassifier=javadoc 
```
