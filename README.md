# Nacos-demo

Spring Cloud Alibaba Nacos组件演示

#### Nacos介绍

- 服务发现和健康检测
- 动态配置服务
- 动态DNS服务
- 服务以及元数据管理

#### Nacos使用场景

收集和维护所有服务的ip和端口

#### Nacos环境搭建部署

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
#### 集成dubbo

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

#### 常见问题

> 启动报not found使用以下指令
```bash
bash -f ./startup.sh -m standalone
```




