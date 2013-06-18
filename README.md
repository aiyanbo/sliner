sliner
======

sliner 全称SQL Liner，是一个sql生成器。主要通过外部参入的表达式生成SQL查询语句。
支持的查询操作：

 - GT
 - LT
 - GTE
 - LTE
 - IS
 - LIKE

Keyword
======

4: 指定查询操作符

2: 值包装方式，主要用于LIKE操作。

Expression
======

name4operator = value

name4operator2valueWrapper = value

例如：

```java

name4Like2a = "Andy";
解析成: name LIKE '%Andy%'

age4Gt = 23;
解析成: age > 23

id = 12345;
解析成: id = 12345

```
Using
======
为了满足各种各样的查询服务，我们需要编写不同的查询接口。使用sliner能让这些接口和SQL查询语句变得统一。
例如，我们使用RESTful接口对外提供服务

```sh

http://domain.com/search/user?name4Like2a=Andy&age4Gt=23

```

sliner 根据目标实体和参数生成SQL查询语句

```sql

select * from user where name LIKE '%Andy%' and age > 23

```
