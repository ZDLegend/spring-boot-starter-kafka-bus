# spring-boot-start-kafka-bus
一个轻量级基于kafka的消息总线，避免使用太多topic消耗kafka内存

## 说明

### 支持功能
* 每个endpoint对特定数据结构进行相关增、删、改、加载操作
* 发送kafka消息，同一个topic，将消息分发给特定的endpoint
* 支持指定发送消息给某个或多个服务的endpoint
* endpoint支持指定接收来自某个或多个服务的消息
* 一个服务的endpoint可以感知其他服务相同标识的endpoint对该服务发送的消息消费情况

### endpoint
消息总线的endpoint，用来处理特定消息的相关操作。需要注解@BaseBusEndpoint和接口BaseBusEndpoint<T>搭配使用。
同一个服务只能设置一个endpoint.

### endpoint标识
用来区分同一个服务中的不同endpoint.



