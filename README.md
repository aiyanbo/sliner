sliner
======

[![Build Status](https://travis-ci.org/aiyanbo/sliner.png?branch=master)](https://travis-ci.org/aiyanbo/sliner)

sliner 全称SQL Liner，是一个sql生成器。主要通过外部参入的表达式生成SQL查询语句。
支持的查询操作：

 - GT
 - LT
 - GTE
 - LTE
 - IS
 - LIKE

Blogs
------
https://github.com/aiyanbo/sliner/wiki/Dynamic-Index-Generator


Keyword
-------

4: 指定查询操作符

2: 值包装方式，主要用于LIKE操作。

Expression
----------

name4operator = value

name4operator2valueWrapper = value

例如：

```

name4Like2a = "Andy";
解析成: name LIKE '%Andy%'

age4Gt = 23;
解析成: age > 23

id = 12345;
解析成: id = 12345

```
Using
------
为了满足各种各样的查询服务，我们需要编写不同的查询接口。使用sliner能让这些接口和SQL查询语句变得统一。
例如，我们使用RESTful接口对外提供服务

```sh

http://domain.com/search/user?name4Like2a=Andy&age4Gt=23

```

sliner 根据目标实体和参数生成SQL查询语句

```sql

select * from user where name LIKE '%Andy%' and age > 23

```

Examples
--------
https://github.com/aiyanbo/sliner/blob/master/src/test/java/org/sliner/AppTest.java

Version
-------
0.0.1

Dependencies
------------
 - org.jmotor:jmotor-utility 1.0-SNAPSHOT
 - jaxen:jaxen 1.1.1
 - dom4j:dom4j 1.6.1
 - com.google.guava:guava 15.0

Requires
--------

- jdk1.6+

Build
------
```sh

git clone https://github.com/aiyanbo/jmotor-utility.git

cd jmotor-utility

mvn clean install

cd ..

git clone https://github.com/aiyanbo/sliner.git

cd sliner

mvn clean install

```

Maven
------

```xml
<dependency>
    <groupId>org.sliner</groupId>
    <artifactId>sliner</artifactId>
    <version>0.0.1</version>
</dependency>
```

Gradle
-------

```groovy

dependencies{
    compile 'org.sliner:sliner:0.0.1'
}

```
