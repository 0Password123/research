Maven����Web���̺�Tomcat7���ʹ��
#����#
�������������Maven����͹���JavaWeb���̡�

#�������#
Java:1.6.0_26
Maven:3.1.1
OS:WindowXP SP3
Tomcat��7.0.47

#��������#
##�����ű�##
Maven����JavaWeb���̽ű���
```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=research_maven_web -DarchetypeArtifactId=maven-archetype-webapp
```
Ҫ˵���Ĳ���
* groupId: ��˾���ƣ�һ����������д��Ҳ�ǹ��̵�Ĭ�ϰ�����
* artifactId: ���������ơ�
* archetypeArtifactId: �������̵����ͣ���maven-archetype-webapp����ʾ��Web���̡�

##Web���̱�׼�ṹ##
�������̺�������ļ��ṹ�����У�
* src\main\resources�� ������Դ�ļ�Ŀ¼
* src\main\webapp�� ����webӦ��Ŀ¼

һ���web���̻���Ҫ��������Ŀ¼��
* src\main\java�� ���̵�JavaԴ�����ļ�
* src\test\java�� �����õ�JavaԴ�ļ��ļ�
* src\test\resources�� �����õ���Դ�ļ�
ͨ�����������������Ŀ¼��
```
mkdir src\main\java
mkdir src\test\java
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

#����Web���̵�Tomcat#
##����Tomcat���##
����Tomcat��Ҫ��Ӧ�Ĳ����Tomcat6��7��Ҫ��ͬ�Ĳ����������������ӵ�pom.xml�ļ��У�
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

**ע�⣺Tomcat7��url���������text����������html�����򷢲�Web����ʱ����ʾȨ�޲��㡣**

##����Maven����Tomcat���û���Ϣ##
��%MAVEN_HOME%\conf\settings.xml�ļ���servers�ڵ��У������������ã�
```xml
<server>  
       <id>tomcat</id>  
       <username>admin</username>  
       <password>admin</password>  
</server>  
```

##����Tomcat��֤��Ϣ##
��%TOMCAT_HOME%\conf\tomcat_user.xml�ļ����������½�ɫ���û�������tomcat_maven_plugin�Զ����𹤳̣�
```xml
<role rolename="manager-gui"/>    
<role rolename="manager-script"/>    
<user username="admin" password="admin" roles="manager-gui, manager-script"/>  
```

##����Tomcat##
����Tomcat-7.0.47

##����Web����##
###��Web����ʽ����###
ִ���������
```
mvn package tomcat6/7:redeploy
```
###���ļ�����ʽ����###
ִ���������
```
mvn war:exploded tomcat6/7:redeploy
```

##ж��Web����##
ִ���������
```
mvn tomcat7:undeploy
```

#ʾ����������#
�������õ�ʾ�����̺������ļ����Դ������ַ���أ�
[ʾ������](git "")