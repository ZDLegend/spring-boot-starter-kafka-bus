## 命令说明

### 创建topic
* kafka-topics.sh --zookeeper {zookeeper-ip:port} --create --replication-factor 1 --partitions {partition-num} --topic {topic}

### 为topic增加partition
* kafka-topics.sh --zookeeper {zookeeper-ip:port} -alter -partitions {partition-num} -topic {topic}

### 删除topic
* kafka-topics.sh --zookeeper {zookeeper-ip:port} --delete -topic {topic}

### 查看当前所有添加的topic
* kafka-topics.sh --zookeeper {zookeeper-ip:port} --list

### 查看某topic信息
* kafka-topics.sh --zookeeper {zookeeper-ip:port} --describe -topic {topic}

### KAFKA 接收某topic数据
* kafka-console-consumer.sh  --bootstrap-server {kafka-ip:port} --topic {topic}
  * --from-beginning 从起始阶段订阅数据

### 重置某topic某group的offset为最新offset
* kafka-consumer-groups.sh --bootstrap-server {kafka-ip:port} --group {group} --topic {topic} --reset-offsets --to-latest --execut

### 查看某group的kafka信息
* kafka-consumer-groups.sh --bootstrap-server {kafka-ip:port} --group {group} --describe