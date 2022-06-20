## 代码架构
- bean
  Http的request/response结构体(用于controller)
  字段名称即为body的json字段
- config
  配置
  比如LogicConfig加载 `application-{env}.yaml` 的logic相关配置
- controller
  Http请求接口
- dao & entity & resources/mapper
  SQL数据库操作
  entity是数据库表的对象(每个字段对应SQL表的列)
  dao是MyBatis的Mapper接口定义
  resources/mapper包含接口实现
- service
  业务逻辑

## 配置说明
- `application.yml` 
  设置通用配置设置
  `spring.profiles.active` 环境值
- `application-{env}.yml`
  按照环境值加载对应的配置文件

## 依赖管理
- 使用maven进行依赖管理
- 依赖配置在pom.xml, 每次修改后需要Reload重新加载依赖

## 开发过程
- controller定义接口 & bean定义接口入参和返回
- service实现接口功能(操作数据库或者请求其他服务)
- dao/entity/mapper定义SQL操作

#### 文件描述

- dao: 直接操作数据库的接口
- entity: 数据库对象定义
- service: 实现读写数据库的业务代码

## 构建运行
```
# 构建
mvn clean
mvn package -DskipTests=true

# 运行
java -jar target/collection-svr-1.0-SNAPSHOT.jar
```
