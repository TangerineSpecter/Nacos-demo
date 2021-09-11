# Nacos-demo

Spring Cloud Alibaba Nacos组件演示

## Nacos介绍

#### 基本功能

- 服务发现和健康检测
- 动态配置服务
- 动态DNS服务
- 服务以及元数据管理

#### 与Eureka对比

> Nacos使用raft协议，Nacos集群的一致性要远大于Eureka集群

#### Nacos使用场景

收集和维护所有服务的ip和端口

## Nacos环境搭建部署

#### 下载启动Nacos

> 下载安装：https://github.com/alibaba/nacos/releases

- 解压缩启动Nacos
```bash
sh startup.sh -m standalone #standalone表示以单机的方式启动

tail -f .../nacos-2.0.3/logs/start.out 查看输出日志
```
> 访问Nacos：https://localhost:8848/nacos 账号密码：nacos

#### 配置alibaba和nacos相关依赖
```xml
<!-- nacos组件 -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>

<dependencyManagement>
    <dependencies>
        <!-- 保证com.alibaba.cloud相关依赖都是2.2.5版本 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>2.2.5.RELEASE</version>
            <scope>import</scope>
            <type>pom</type>
        </dependency>
    </dependencies>
</dependencyManagement>
```
> Application上使用服务发现注解

```java
@EnableDiscoveryClient
```

> 使用负载均衡注解

```java
@LoadBalanced
```
之后通过服务名进行调用就会在多台服务器之间轮询

> 服务对nacos地址进行配置

```yaml
spring:
  cloud:
    nacos:
      discovery:
        service: order-sever #发现服务名
        server-addr: localhost:8848 #nacos地址
```
## 集成dubbo

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-dubbo</artifactId>
</dependency>
```
> Applicatoin贴上注解启用dubbo

```java
@EnableDubbo
```
> dubbo生产者以及nacos配置

```yaml
# 应用配置，用于配置当前应用信息，不管该应用是提供者还是消费者。
dubbo:
  application:
    name: stock-server-provider
    id: stock-server-provider
# 注册中心配置，用于配置连接注册中心相关信息。可以支持zk，这里改成nacos
  registry:
    address: nacos://localhost:8848
# 协议配置，用于配置提供服务的协议信息，协议由提供方指定，消费方被动接受。
  protocol:
    name: dubbo
    port: 20888
```

> dubbo消费者以及nacos配置

```yaml
dubbo:
  application:
    name: order-server-consumer
    id: order-server-consumer
  registry:
    address: nacos://localhost:8848
```

## Nacos配置中心

#### 引入配置

```xml
<dependencies>
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
</dependencies>
```
#### 配置Nacos地址和项目名称

```yaml
spring:
  application:
    name: config-center #表示当前微服务需要想配置中心索要名叫config-center的配置
  cloud:
    nacos:
      config:
        server-addr: localhost:8848 #表示微服务需要找的nacos的地址
        file-extension: yaml #表示支持的扩展文件名
  profiles:  
    active: prod #表示向配置中心索要生产环境的配置
```

#### 自动刷新，通过注解支持
```java
@RefreshScope
```

#### springboot配置文件规则

- **优先级：** bootstrap.properties > bootstrap.yaml > application.properties > application.yaml
- **文件格式：** ${application.name}-#{spring.profiles.active}.${file-extension}，通用配置就不需要加环境名
- **同一个配置在不同环境存在：** 优先读取对应配置环境中的值，再读取公共配置的值

#### 根据环境打包启动

#### 多服务共享配置设置
```bash
java -jar springbootdemo.jar -Dspring.profiles.active=test (配置文件就不需要写)
```

##### 方式一，通过shared-config方式
```yaml
spring:
  application:
    name: config-center
  cloud:
    nacos:
      config:
        server-addr: localhost:8848
        file-extension: yaml
        shared-configs[0]: #shared-configs是一个列表，多个配置，就0、1、2配置下去
          data_id: redis.yaml #nacos建立的需要读取的公共配置data_id
          group: DEFAULT_GROUP #可以不写，默认值为DEFAULT_GROUP
          refresh: true #默认是false， 如果需要支持自动刷新需要配置true，搭配@RefreshScope实现动态刷新
```

##### 方式二，通过extension-configs方式
```yaml
spring:
  application:
    name: config-center
  cloud:
    nacos:
      config:
        server-addr: localhost:8848
        file-extension: yaml
        extension-configs[0]: #extension-configs是一个列表，多个配置，就0、1、2配置下去
          data_id: redis.yaml #nacos建立的需要读取的公共配置data_id
          group: DEFAULT_GROUP #可以不写，默认值为DEFAULT_GROUP
          refresh: true #默认是false， 如果需要支持自动刷新需要配置true，搭配@RefreshScope实现动态刷新
```

#### 动态刷新原理

##### 动态监听

- push表示服务端主动将数据变更信息推送给客户端
- pull表示客户端主动去服务端拉取数据

#### 数据模型

- Namespace：代表不同的环境：Dev/Test/Prod
- Group：代表某一类配置，比如中间件配置、数据库配置
- DataId：某个项目中具体的配置文件

#### 服务分级存储模型

- 服务：
    - 健康检查开关
    - 元数据
    - 路由机制
    - 保护阈值
- 集群
    - 健康检查模式
    - 元数据
    - 同步机制
- 实例
    - IP
    - 端口
    - 权重
    - 健康状态
    - 下线状态
    - 元数据
    - 响应时间
    
#### 服务领域模型

- Namespace：实现环境隔离，默认public
- Group：不同的service可以组成一个Group，默认Default-Group
- Service：服务名称
- Cluster：对指定的微服务虚拟划分，默认值Default
- Instance：某个服务的具体实例

## 常见问题

> 启动报not found使用以下指令
```bash
bash -f ./startup.sh -m standalone
```

> 为什么要使用配置中心

- 统一配置文件管理
- 提供统一标准接口，服务根据标准接口自行拉取配置
- 支持动态更新所有服务

> 常见的配置中心

- Appllo
- Disconf
- SpringCloud Config
- Nacos





