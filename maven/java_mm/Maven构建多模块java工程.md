Maven������ģ��Java����
#����#
��Ŀ����ʱ��ͨ���Ὣ��Ŀ��Ϊ���ģ����п������������������Maven������ģ���Java���̡�

#�������#
Java:1.6.0_26
Maven:3.1.1
OS:WindowXP SP3

#��Ŀģ��˵��#
ʾ�����̷�Ϊ���ģ�飬�ֱ��ǣ�
* research_maven_javamm�������̣��ۺϸ�ģ�顣
* App����ģ�飬����ModuleA��ModuleB��ModuleCom
* ModuleA��ģ��A������ModuleCom
* ModuleB��ģ��B������ModuleCom
* ModuleCom����ͨģ�顣

��ģ����Ŀ���幹��ʹ��Maven�ĸ��Ӽ̳�������

#��������#
���ȴ������̺͸�ģ�飬Ȼ�����ø�ģ��֮���������ϵ��

��Ŀ�ĶԵ�����������������ϵ�����ڸ����̵�pom.xml�У�������ģ�����ʡȥ�������������������á�

##����������##
���ȴ���������Ӧ��
###�����ű�###
```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=research_maven_javamm -DarchetypeArtifactId=maven-archetype-quickstart
```
###�޸ĸ�����ΪMaven�ĸ�����###
�����ɹ�����Ҫ�����������͸�ΪMaven�ĸ����̣��޸�pom.xml�ļ���packaging����Ϊpom�����£�
```xml
<packaging>pom</packaging>
```

##������ģ�鹤��##
�����̴����ɹ��󣬴�����ģ�鹤�̡�

**ע�⣺������ģ�鹤��ʱ��Ҫ��cd�������̵ĸ�Ŀ¼:**
```
cd research_maven_javamm
```

###����App��ģ��###
```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=app -DarchetypeArtifactId=maven-archetype-quickstart
```

###����ModuleA����###
```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=module-a -DarchetypeArtifactId=maven-archetype-quickstart
```

###����ModuleB����###
```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=module-b -DarchetypeArtifactId=maven-archetype-quickstart
```

###����ModuleC����###
```
mvn archetype:create -DgroupId=com.github.hubin0011 -DartifactId=module-com -DarchetypeArtifactId=maven-archetype-quickstart
```

������ģ��󣬿��Կ���������research_maven_javamm��pom.xml�ļ���������ӹ��̵���Ϣ��
```xml
  <modules>
    <module>app</module>
    <module>module-a</module>
    <module>module-b</module>
    <module>module-com</module>
  </modules>
```
##���ø�ģ��������ϵ##
������ģ�鹤�̺���Ҫ�Ը�ģ�鹤��֮���������ϵ�������á���֮ǰ˵����ģ��������ϵ��
* ModuleA����ModuleCom
* ModuleB����ModuleCom
* ��ģ��app����ModuleA��ModuleB��ModuleCom

###����ModuleA����ModuleCom###
�޸�ModuleA��pom.xml�ļ������ModuleCom��������
```xml
    <dependency>
		<groupId>com.github.hubin0011</groupId>
		<artifactId>module-com</artifactId>
		<version>1.0-SNAPSHOT</version>
    </dependency>
```
###����ModuleB����ModuleCom###
�޸�ModuleB��pom.xml�ļ������ModuleCom��������
```xml
    <dependency>
		<groupId>com.github.hubin0011</groupId>
		<artifactId>module-com</artifactId>
		<version>1.0-SNAPSHOT</version>
    </dependency>
```
###������ģ��app����ModuleA��ModuleB��ModuleCom###
�޸�app��pom.xml�ļ������ModuleA��ModuleB��ModuleCom��������
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

#���빤��#
##����ģ�鹤��##
��ģ�鹤�̵�Ŀ¼��ִ�����½ű����Ա����ģ�鹤�̣�
```
mvn clean compile
```
##���븸����##
�ڸ����̵�Ŀ¼��ִ�����½ű�������������̣�
```
mvn clean compile
```

#ִ�е������#
##��ģ�鹤�̽��е������##
��ģ�鹤�̵�Ŀ¼��ִ�����½ű��������и�ģ�鹤�̵ĵ�����ԣ�
```
mvn clean test
```
##���������̽��е������##
�ڸ����̵�Ŀ¼��ִ�����½ű��������������̵ĵ�����ԣ�
```
mvn clean test
```

#���#
##���ģ�鹤��##
��ģ�鹤�̵�Ŀ¼��ִ�����½ű����Դ����ģ�飺
```
mvn clean package
```
##�����������##
�ڸ����̵�Ŀ¼��ִ�����½ű���������ģ�飬�ڸ�ģ���targetĿ¼��������Ӧ��jar������
```
mvn clean package
```

#�������������زֿ�#
##����ģ�鹤�̵����زֿ�##
��ģ�鹤�̵�Ŀ¼��ִ�����½ű����Է�����ģ�鹤�̵����زֿ⣺
```
mvn clean install
```
##������������##
�ڸ����̵�Ŀ¼��ִ�����½ű��򷢲��������̵����زֿ⣺
```
mvn clean install
```

#���ʵ��#
* �����̲�Ӧ���д��룬���еĴ��붼Ӧ�ò�ֵ���ģ���п���
* ���̹�ͨ�����ĵ���������Ӧ���ڸ����̵�pom.xml��ά������ģ�����������⹹�������ڸ�ģ���pom.xml��ά��
