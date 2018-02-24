ik-analyzer
===========

#  基于IK中文分词器,添加同义词功能, 兼容lucene 5.x

IKAnalyzer的作者为林良益（linliangyi2007@gmail.com），项目网站为http://code.google.com/p/ik-analyzer/

### 示例:

	mvn compile

#### 基本示例

	mvn exec:java -Dexec.mainClass=org.wltea.analyzer.sample.IKAnalzyerDemo

#### 同义词示例

	mvn exec:java -Dexec.mainClass=org.wltea.analyzer.sample.IKSynonymAnalzyerDemo


### 安装使用:

	mvn clean install

### add dependency to your pom.xml

	<dependency>
		<groupId>org.wltea.ik-analyzer</groupId>
		<artifactId>ik-analyzer</artifactId>
		<version>5.1</version>
	</dependency>
