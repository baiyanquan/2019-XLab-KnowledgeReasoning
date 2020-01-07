# README-Kubernetes Fault Inject Tool

## Introduction (项目简介)

本项目为X-Lab实验室针对云平台微服务系统可靠性测试的故障注入项目。

项目使用Kubernetes、Docker构建微服务受测系统Sock-Shop；使用Conductor、Ansible、Chaosblade进行故障注入；使用Docker、Prometheus、Influxdb、Grafana对集群的性能进行监控；使用Ansible-Operator向实验室的其他项目组提供数据收集的接口。使用Jenkins将上述过程整合为自动化流程，最终达到测试云平台可靠性的目的。

## How to Build (项目构建方法)

### Environment Requirements (环境准备)

#### 通过在本机搭建环境运行

- Python 2.7
- Ansible 2.7.10
- Flask ~>0.12.2

项目使用Ansible进行自动化运维，需要在本机/etc/ansible/hosts配置文件中配置集群基本信息：

```shell
[Lab409-master]
10.60.38.181:20022 ansible_user=root

[Lab409-hm]
192.168.199.31 ansible_user=root
192.168.199.32 ansible_user=root
192.168.199.33 ansible_user=root
192.168.199.34 ansible_user=root
192.168.199.35 ansible_user=root
192.168.199.41 ansible_user=root
192.168.199.42 ansible_user=root
192.168.199.43 ansible_user=root
192.168.199.44 ansible_user=root
192.168.199.45 ansible_user=root

[Lab409-vm] 
192.168.199.21 ansible_user=root
192.168.199.22 ansible_user=root
192.168.199.23 ansible_user=root
192.168.199.24 ansible_user=root
192.168.199.25 ansible_user=root

[Lab409-vm:vars]
ansible_ssh_common_args='-o "ProxyCommand ssh -o ConnectTimeout=6000000 -p 20022 root@10.60.38.181 nc -w 100000 %h %p"'

[Lab409-hm:vars]
ansible_ssh_common_args='-o "ProxyCommand ssh -o ConnectTimeout=6000000 -p 20022 root@10.60.38.181 nc -w 100000 %h %p"'
```

同时，需要为每一台主机配置ssh免密登陆：

```shell
# 将本机ssh复制到远程主机
ssh-copy-id root@xxx.xxx.xxx.xxx

# 保存远程主机ip到当前主机的know_hosts
ssh-keyscan xxx.xxx.xxx.xxx >> ~/.ssh/known_hosts
```

最后，查看是否连接成功：

```shell
#as root
ansible all -m ping -u root
```

之后运行项目，则可以通过本项目提供的接口文档的内容进行实验。

---

#### 通过docker容器运行项目

- 安装docker

- 获取源码后，在有Dockerfile的目录中执行：

  > docker build -t xlab/faultInjectorTool:v1

  > docker run -it xlab/faultInjectorTool:v1 /bin/bash

---



### Get the Project (获取项目)

- 获取项目源码：
	
	> git clone https://github.com/baiyanquan/2019-XLab-KubernetesTools.git

### Import the Project to IDE (导入项目)

- 直接在bash中定位到项目源码根目录
- 或者使用PyCharm打开项目


### Build the Project (构建项目)
本项目采用持续集成的方式，通过跟踪github上的版本更新，使用Dockerfile进行自动化安装和部署，通过`docker build`和`docker run `的方式进行docker镜像和容器的构建

## How to Run (项目运行方法)

- 使用docker构建当前版本的项目的dockerfile并运行：

> docker build -t xlab/faultInjectorTool:v1

> docker run -it xlab/faultInjectorTool:v1 /bin/bash

用户自行设置容器名、端口号映射（内部端口号5000）、volumn（内部日志文件和数据库csv问价地址：  ）

- 若通过docker当时运行，将项目源码文件映射到docker容器内部数据卷，再运行manage.py
- 若本机搭建环境运行，则直接运行manage.py

---



## How to Use (项目基本功能)

### 故障注入
#### CPU
项目提供注入“CPU负载”故障的功能，用户可通过改变输入参数，更改故障注入状态持续的时间以及进行故障注入的节点。

#### Network
项目提供注入“网络延迟“故障的功能，用户可以通过自定义网络延迟故障的时间，故障注入持续的时间以及故障注入的节点

#### Memory
项目提供注入”内存不足“故障的功能，用户可以选择注入的内存占比，故障注入持续的时间以及故障注入的节点
#### Disk
项目提供注入“磁盘I/O高“故障的功能，用户可以选择注入是读还是写，故障注入持续的时间以及注入的节点
#### Pod
项目提供注入”杀死单个Kubernetes集群Pod“的功能，用户可以指定Pod的名字，以及注入的节点。由于Kubernetes的Pod的特性，当注入故障的Pod被杀死后，会立即生成新的Pod代替它，所以这个故障恢复不会恢复原来的Pod。
#### Service
项目提供注入“杀死Kubernetes集群Service”的功能，用户可以指定Service的名字以及注入的节点。这同样是不可恢复原来的Pods的。

---



### 故障恢复
#### 恢复单个节点单个故障
项目每发生一次攻击注入，都会为这次攻击提供一个独一无二的tag，用户提供该tag，就可以摧毁这个注入行为，达到故障恢复功能。
#### 恢复单个节点所有故障
项目提供“恢复当个节点所有故障”的功能，用户选择一个节点，就可以摧毁该节点所有的注入。
#### 恢复所有节点故障
项目提供“恢复所有节点所有故障“的功能

---



### 数据采集
#### 集群信息
项目提供获取Kubernetes集群所有namespace，node，特定namespace下的service，deployment，pods的信息。
#### Prometheus+Grafana
项目通过Prometheus获取Kubernetes集群的node，pods，services等状态信息，存入时序数据库，用户可以获取某一时间段sock-shop所有service的状态信息。用户可以通过Grafana可视化数据查看。
#### InfluxDB
项目通过InfluxDB对Prometheus获取的数据进一步持久化，用户可以获取InfluxDB某段时间内的某一数据库内全部信息。

---



### 日志分析
项目为每一个攻击注入，故障恢复操作都进行了日志记录。用户可以获取基本的项目日志，也可以 获取Kubernetes集群所有namespace为sock-shop的Pod的日志

---



### 消息队列
项目将每一次攻击注入，故障恢复操作的行为消息放入了消息队列，用户可以查看服务器消息队列可视化界面，可以选择消息队列的打开还是关闭，也可以选择清空消息队列。

---




## Code Structure (代码结构说明)

```bash
.
├── Dockerfile  ##### docker镜像描述文
├── LICENSE
├── README.md
├── app
│   ├── __init__.py
│   ├── chaosblade
│   │   ├── __init__.py
│   │   └── chaosblade.py  ##### chaosblade Restful API接口文件
│   ├── common_options
│   │   ├── __init__.py
│   │   └── get_info.py  ##### get_info Restful API接口文件(获取Kubernetes集群信息)
│   ├── influxdb_and_prometheus
│   │   ├── __init__.py
│   │   └── get_data.p  ##### get_data Restful API接口文件(获取influxdb和prometheus数据库信息)
│   ├── message_mq
│   │   ├── __init__.py
│   │   └── mq_control.py  ##### mq_control Restful API接口文件(控制消息队列，开关与清除)
│   └── stress
│       ├── __init__.py
│       └── stress_inject.py
├── auto_ssh.sh
├── chaosblade.dat
├── controller
│   ├── __init__.py
│   └── prometheus
│       ├── PerformanceDataPicker.py  ##### Prometheus数据获取
│       ├── PerformanceDataWriter.py  ##### 将Prometheus数据写入文件
│       └── __init__.py
├── data
│   └── sock-results-HW_10-23.csv  ##### Prometheus获取的数据写入的文件
├── hosts
├── manage.py  ##### 启动文件
├── old_version
│   └── flask_ansible.py
├── record.log  ##### 生成的日志
├── remove.sh
├── run.sh
├── services
│   ├── __init__.py
│   ├── fault_injector.py  ##### Chaosblade攻击注入与恢复(cpu, network, disk...... )
│   ├── influxdb_observer.py  ##### InfluxDB数据获取
│   ├── k8s_observer.py  ##### Kubernetes集群信息获取(node，namespace，service，pods......)
│   ├── message_queue.py  ##### 消息队列(开启，关闭，清空)
│   └── prometheus_observer.py  ##### Prometheus数据获取
├── static
│   └── playbook  ##### ansible playbook 获取Kubernetes集群信息配置
│       ├── batch_deliver_ssh.yaml
│       ├── get_deployment.yaml
│       ├── get_namespace.retry
│       ├── get_namespace.yaml
│       ├── get_node.retry
│       ├── get_node.yaml
│       ├── get_pod.yaml
│       ├── get_pods.retry
│       └── get_svc.yaml
├── utils
│   ├── SockConfig.py  ##### Prometheus数据获取配置文件（新）
│   ├── __init__.py
│   ├── ansible_runner.py  ##### ansible配置运行文件
│   ├── config.py   ##### Prometheus数据获取配置文件（旧）
│   ├── log_record.py  ##### 日志输出格式转化文件
│   ├── signal.py
│   └── utils.py  ##### Prometheus 时间格式转化文件
└── view_model
    ├── __init__.py
    ├── k8s_repository.py
    └── prometheus_repository.py
```