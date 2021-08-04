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
```xml
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

```xml
spring:
  cloud:
    nacos:
      discovery:
        service: order-sever #发现服务名
        server-addr: localhost:8848 #nacos地址
```

#### 常见问题

> 启动报not found使用以下指令
```bash
bash -f ./startup.sh -m standalone
```




