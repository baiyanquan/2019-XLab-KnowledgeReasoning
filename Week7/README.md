# 基于实验室数据的本体推理

Table of Contents
=================

   * [基于实验室数据的本体推理](#基于实验室数据的本体推理)
     * [实验室数据组成](#实验室数据组成)
       * [Prefix](#prefix)
       * [Class](#class)
       * [ObjectProperty](#objectproperty)
       * [Objects](#objects)
       * [Relation](#relation)
     * [推理前的数据(with filter)](#推理前的数据with-filter)
     * [推理后的数据(with filter)](#推理后的数据with-filter)
     * [推理结果](#推理结果)

------

## 实验室数据组成

### Prefix

- rel
- namespace_rel
- pods_rel
- containers_rel
- services_rel
- servers_rel
- environment_rel

### Class

- Namespace
- Pod
- Container
- Service
- Server
- Environment

### ObjectProperty

- namespace_rel:supervises
- pods_rel:contains
- pods_rel:deployed_in
- pods_rel:provides
- containers_rel:ss_profile
- services_rel:ss_profile
- servers_rel:manage
- environment_rel:has

### Objects

### Relation

------

## 推理前的数据(with filter)

```ttl
# Class
Container type Class
Environment type Class
Namespace type Class
Pod type Class
Server type Class
Service type Class

# ObjectProperty
rel:cc_profile domain Container
rel:cc_profile range Container
rel:cc_profile type ObjectProperty
rel:contains domain Pod
rel:contains range Container
rel:contains type ObjectProperty
rel:deployed_in domain Pod
rel:deployed_in range Server
rel:deployed_in type ObjectProperty
rel:has domain Environment
rel:has range Server
rel:has type ObjectProperty
rel:manage domain Server
rel:manage range Server
rel:manage type ObjectProperty
rel:provides domain Pod
rel:provides range Service
rel:provides type ObjectProperty
rel:ss_profile domain Service
rel:ss_profile range Service
rel:ss_profile type ObjectProperty
rel:supervises domain Namespace
rel:supervises range Namespace
rel:supervises type ObjectProperty

# object
http://containers/10.60.38.181/sock-shop/carts type Container
http://containers/10.60.38.181/sock-shop/carts-db type Container
http://containers/10.60.38.181/sock-shop/carts-db/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/carts-db/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/carts-db/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/carts-db/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/carts-db/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/carts-db/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/carts/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/carts/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/carts/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/carts/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/carts/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/carts/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/catalogue type Container
http://containers/10.60.38.181/sock-shop/catalogue-db type Container
http://containers/10.60.38.181/sock-shop/catalogue-db/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/catalogue-db/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/catalogue-db/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/catalogue-db/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/catalogue-db/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/catalogue-db/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/catalogue/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/catalogue/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/catalogue/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/catalogue/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/catalogue/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/catalogue/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/front-end type Container
http://containers/10.60.38.181/sock-shop/front-end/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/front-end/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/front-end/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/front-end/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/front-end/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/front-end/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/orders type Container
http://containers/10.60.38.181/sock-shop/orders-db type Container
http://containers/10.60.38.181/sock-shop/orders-db/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/orders-db/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/orders-db/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/orders-db/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/orders-db/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/orders/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/orders/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/orders/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/orders/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/orders/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/orders/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/payment type Container
http://containers/10.60.38.181/sock-shop/payment/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/payment/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/payment/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/payment/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/payment/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/payment/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/queue-master type Container
http://containers/10.60.38.181/sock-shop/queue-master/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/queue-master/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/queue-master/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/queue-master/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/queue-master/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/queue-master/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/rabbitmq type Container
http://containers/10.60.38.181/sock-shop/rabbitmq/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/rabbitmq/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/rabbitmq/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/rabbitmq/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/rabbitmq/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/rabbitmq/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/session-db type Container
http://containers/10.60.38.181/sock-shop/session-db/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/session-db/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/session-db/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/session-db/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/session-db/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/session-db/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/shipping type Container
http://containers/10.60.38.181/sock-shop/shipping/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/shipping/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/shipping/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/shipping/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/shipping/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/shipping/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/user type Container
http://containers/10.60.38.181/sock-shop/user-db type Container
http://containers/10.60.38.181/sock-shop/user-db/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/user-db/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/user-db/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/user-db/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/user-db/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/user-db/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/user/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/user/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/user/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/user/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/user/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/user/Network_Output_Packets type Container
http://pods/10.60.38.181/sock-shop/carts-648d5f498d-n8hzn type Pod
http://pods/10.60.38.181/sock-shop/carts-648d5f498d-test2 type Pod
http://pods/10.60.38.181/sock-shop/carts-db-64b6cc584f-zd6zz type Pod
http://pods/10.60.38.181/sock-shop/catalogue-b8587c9c5-wfvkt type Pod
http://pods/10.60.38.181/sock-shop/catalogue-db-99cbcbb88-wl52p type Pod
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-55bg5 type Pod
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-6md46 type Pod
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-dbdck type Pod
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-gq9k9 type Pod
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-lkm8b type Pod
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-fqnjt type Pod
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-l9rmn type Pod
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-qcmj7 type Pod
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-tw8sm type Pod
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-zt6zq type Pod
http://pods/10.60.38.181/sock-shop/orders-db-86f5c494b9-xvvhr type Pod
http://pods/10.60.38.181/sock-shop/payment-6c698f689b-vlkhq type Pod
http://pods/10.60.38.181/sock-shop/queue-master-77d854c647-qbgwq type Pod
http://pods/10.60.38.181/sock-shop/rabbitmq-6cf767fcc4-9j5vn type Pod
http://pods/10.60.38.181/sock-shop/session-db-6b57d5cc79-gjxvb type Pod
http://pods/10.60.38.181/sock-shop/shipping-65769d99d7-lmpg7 type Pod
http://pods/10.60.38.181/sock-shop/user-d4cb47c5f-8srcb type Pod
http://pods/10.60.38.181/sock-shop/user-db-6ccbdb454c-q9dxm type Pod
http://services/10.60.38.181/sock-shop/carts type Service
http://services/10.60.38.181/sock-shop/carts-db type Service
http://services/10.60.38.181/sock-shop/carts-db/latency type Service
http://services/10.60.38.181/sock-shop/carts-db/throughput type Service
http://services/10.60.38.181/sock-shop/carts/latency type Service
http://services/10.60.38.181/sock-shop/carts/success_rate type Service
http://services/10.60.38.181/sock-shop/catalogue type Service
http://services/10.60.38.181/sock-shop/catalogue-db type Service
http://services/10.60.38.181/sock-shop/catalogue-db/latency type Service
http://services/10.60.38.181/sock-shop/catalogue-db/throughput type Service
http://services/10.60.38.181/sock-shop/catalogue/latency type Service
http://services/10.60.38.181/sock-shop/catalogue/success_rate type Service
http://services/10.60.38.181/sock-shop/front-end type Service
http://services/10.60.38.181/sock-shop/front-end-external type Service
http://services/10.60.38.181/sock-shop/front-end-external/latency type Service
http://services/10.60.38.181/sock-shop/front-end-external/success_rate type Service
http://services/10.60.38.181/sock-shop/front-end/latency type Service
http://services/10.60.38.181/sock-shop/front-end/success_rate type Service
http://services/10.60.38.181/sock-shop/orders type Service
http://services/10.60.38.181/sock-shop/orders-db type Service
http://services/10.60.38.181/sock-shop/orders-db/latency type Service
http://services/10.60.38.181/sock-shop/orders-db/throughput type Service
http://services/10.60.38.181/sock-shop/orders/latency type Service
http://services/10.60.38.181/sock-shop/orders/success_rate type Service
http://services/10.60.38.181/sock-shop/payment type Service
http://services/10.60.38.181/sock-shop/payment/latency type Service
http://services/10.60.38.181/sock-shop/payment/success_rate type Service
http://services/10.60.38.181/sock-shop/queue-master type Service
http://services/10.60.38.181/sock-shop/queue-master/latency type Service
http://services/10.60.38.181/sock-shop/queue-master/success_rate type Service
http://services/10.60.38.181/sock-shop/rabbitmq type Service
http://services/10.60.38.181/sock-shop/rabbitmq/latency type Service
http://services/10.60.38.181/sock-shop/rabbitmq/success_rate type Service
http://services/10.60.38.181/sock-shop/session-db type Service
http://services/10.60.38.181/sock-shop/session-db/latency type Service
http://services/10.60.38.181/sock-shop/session-db/throughput type Service
http://services/10.60.38.181/sock-shop/shipping type Service
http://services/10.60.38.181/sock-shop/shipping/latency type Service
http://services/10.60.38.181/sock-shop/shipping/success_rate type Service
http://services/10.60.38.181/sock-shop/user type Service
http://services/10.60.38.181/sock-shop/user-db type Service
http://services/10.60.38.181/sock-shop/user-db/latency type Service
http://services/10.60.38.181/sock-shop/user-db/throughput type Service
http://services/10.60.38.181/sock-shop/user/latency type Service
http://services/10.60.38.181/sock-shop/user/success_rate type Service

# relation
http://environment/10.60.38.181 rel:has http://server/10.60.38.181/192.168.199.31
http://environment/10.60.38.181 rel:has http://server/10.60.38.181/192.168.199.32
http://environment/10.60.38.181 rel:has http://server/10.60.38.181/192.168.199.33
http://environment/10.60.38.181 rel:has http://server/10.60.38.181/192.168.199.34
http://environment/10.60.38.181 rel:has http://server/10.60.38.181/192.168.199.35
http://namespace/10.60.38.181/monitoring rel:supervises http://namespace/10.60.38.181/sock-shop
http://pods/10.60.38.181/sock-shop/carts-648d5f498d-n8hzn rel:contains http://containers/10.60.38.181/sock-shop/carts
http://pods/10.60.38.181/sock-shop/carts-648d5f498d-n8hzn rel:deployed_in http://server/10.60.38.181/192.168.199.32
http://pods/10.60.38.181/sock-shop/carts-648d5f498d-n8hzn rel:provides http://services/10.60.38.181/sock-shop/carts
http://pods/10.60.38.181/sock-shop/carts-db-64b6cc584f-zd6zz rel:contains http://containers/10.60.38.181/sock-shop/carts-db
http://pods/10.60.38.181/sock-shop/carts-db-64b6cc584f-zd6zz rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/carts-db-64b6cc584f-zd6zz rel:provides http://services/10.60.38.181/sock-shop/carts-db
http://pods/10.60.38.181/sock-shop/catalogue-b8587c9c5-wfvkt rel:contains http://containers/10.60.38.181/sock-shop/catalogue
http://pods/10.60.38.181/sock-shop/catalogue-b8587c9c5-wfvkt rel:deployed_in http://server/10.60.38.181/192.168.199.34
http://pods/10.60.38.181/sock-shop/catalogue-b8587c9c5-wfvkt rel:provides http://services/10.60.38.181/sock-shop/catalogue
http://pods/10.60.38.181/sock-shop/catalogue-db-99cbcbb88-wl52p rel:contains http://containers/10.60.38.181/sock-shop/catalogue-db
http://pods/10.60.38.181/sock-shop/catalogue-db-99cbcbb88-wl52p rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/catalogue-db-99cbcbb88-wl52p rel:provides http://services/10.60.38.181/sock-shop/catalogue-db
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-55bg5 rel:contains http://containers/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-55bg5 rel:deployed_in http://server/10.60.38.181/192.168.199.32
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-55bg5 rel:provides http://services/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-6md46 rel:contains http://containers/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-6md46 rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-6md46 rel:provides http://services/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-dbdck rel:contains http://containers/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-dbdck rel:deployed_in http://server/10.60.38.181/192.168.199.32
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-dbdck rel:provides http://services/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-gq9k9 rel:contains http://containers/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-gq9k9 rel:deployed_in http://server/10.60.38.181/192.168.199.34
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-gq9k9 rel:provides http://services/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-lkm8b rel:contains http://containers/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-lkm8b rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-lkm8b rel:provides http://services/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-fqnjt rel:contains http://containers/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-fqnjt rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-fqnjt rel:provides http://services/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-l9rmn rel:contains http://containers/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-l9rmn rel:deployed_in http://server/10.60.38.181/192.168.199.32
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-l9rmn rel:provides http://services/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-qcmj7 rel:contains http://containers/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-qcmj7 rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-qcmj7 rel:provides http://services/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-tw8sm rel:contains http://containers/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-tw8sm rel:deployed_in http://server/10.60.38.181/192.168.199.34
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-tw8sm rel:provides http://services/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-zt6zq rel:contains http://containers/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-zt6zq rel:deployed_in http://server/10.60.38.181/192.168.199.32
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-zt6zq rel:provides http://services/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-db-86f5c494b9-xvvhr rel:contains http://containers/10.60.38.181/sock-shop/orders-db
http://pods/10.60.38.181/sock-shop/orders-db-86f5c494b9-xvvhr rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/orders-db-86f5c494b9-xvvhr rel:provides http://services/10.60.38.181/sock-shop/orders-db
http://pods/10.60.38.181/sock-shop/payment-6c698f689b-vlkhq rel:contains http://containers/10.60.38.181/sock-shop/payment
http://pods/10.60.38.181/sock-shop/payment-6c698f689b-vlkhq rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/payment-6c698f689b-vlkhq rel:provides http://services/10.60.38.181/sock-shop/payment
http://pods/10.60.38.181/sock-shop/queue-master-77d854c647-qbgwq rel:contains http://containers/10.60.38.181/sock-shop/queue-master
http://pods/10.60.38.181/sock-shop/queue-master-77d854c647-qbgwq rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/queue-master-77d854c647-qbgwq rel:provides http://services/10.60.38.181/sock-shop/queue-master
http://pods/10.60.38.181/sock-shop/rabbitmq-6cf767fcc4-9j5vn rel:contains http://containers/10.60.38.181/sock-shop/rabbitmq
http://pods/10.60.38.181/sock-shop/rabbitmq-6cf767fcc4-9j5vn rel:deployed_in http://server/10.60.38.181/192.168.199.34
http://pods/10.60.38.181/sock-shop/rabbitmq-6cf767fcc4-9j5vn rel:provides http://services/10.60.38.181/sock-shop/rabbitmq
http://pods/10.60.38.181/sock-shop/session-db-6b57d5cc79-gjxvb rel:contains http://containers/10.60.38.181/sock-shop/session-db
http://pods/10.60.38.181/sock-shop/session-db-6b57d5cc79-gjxvb rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/session-db-6b57d5cc79-gjxvb rel:provides http://services/10.60.38.181/sock-shop/session-db
http://pods/10.60.38.181/sock-shop/shipping-65769d99d7-lmpg7 rel:contains http://containers/10.60.38.181/sock-shop/shipping
http://pods/10.60.38.181/sock-shop/shipping-65769d99d7-lmpg7 rel:deployed_in http://server/10.60.38.181/192.168.199.34
http://pods/10.60.38.181/sock-shop/shipping-65769d99d7-lmpg7 rel:provides http://services/10.60.38.181/sock-shop/shipping
http://pods/10.60.38.181/sock-shop/user-d4cb47c5f-8srcb rel:contains http://containers/10.60.38.181/sock-shop/user
http://pods/10.60.38.181/sock-shop/user-d4cb47c5f-8srcb rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/user-d4cb47c5f-8srcb rel:provides http://services/10.60.38.181/sock-shop/user
http://pods/10.60.38.181/sock-shop/user-db-6ccbdb454c-q9dxm rel:contains http://containers/10.60.38.181/sock-shop/user-db
http://pods/10.60.38.181/sock-shop/user-db-6ccbdb454c-q9dxm rel:deployed_in http://server/10.60.38.181/192.168.199.32
http://pods/10.60.38.181/sock-shop/user-db-6ccbdb454c-q9dxm rel:provides http://services/10.60.38.181/sock-shop/user-db
```

------

## 推理后的数据(with filter)

```ttl
# Class
Container type Class
Environment type Class
Namespace type Class
Pod type Class
Server type Class
Service type Class

# ObjectProperty
rel:cc_profile domain Container
rel:cc_profile range Container
rel:cc_profile type ObjectProperty
rel:contains domain Pod
rel:contains range Container
rel:contains type ObjectProperty
rel:deployed_in domain Pod
rel:deployed_in range Server
rel:deployed_in type ObjectProperty
rel:has domain Environment
rel:has range Server
rel:has type ObjectProperty
rel:manage domain Server
rel:manage range Server
rel:manage type ObjectProperty
rel:provides domain Pod
rel:provides range Service
rel:provides type ObjectProperty
rel:ss_profile domain Service
rel:ss_profile range Service
rel:ss_profile type ObjectProperty
rel:supervises domain Namespace
rel:supervises range Namespace
rel:supervises type ObjectProperty

# object
http://containers/10.60.38.181/sock-shop/carts type Container
http://containers/10.60.38.181/sock-shop/carts-db type Container
http://containers/10.60.38.181/sock-shop/carts-db/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/carts-db/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/carts-db/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/carts-db/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/carts-db/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/carts-db/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/carts/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/carts/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/carts/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/carts/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/carts/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/carts/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/catalogue type Container
http://containers/10.60.38.181/sock-shop/catalogue-db type Container
http://containers/10.60.38.181/sock-shop/catalogue-db/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/catalogue-db/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/catalogue-db/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/catalogue-db/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/catalogue-db/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/catalogue-db/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/catalogue/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/catalogue/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/catalogue/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/catalogue/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/catalogue/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/catalogue/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/front-end type Container
http://containers/10.60.38.181/sock-shop/front-end/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/front-end/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/front-end/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/front-end/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/front-end/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/front-end/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/orders type Container
http://containers/10.60.38.181/sock-shop/orders-db type Container
http://containers/10.60.38.181/sock-shop/orders-db/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/orders-db/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/orders-db/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/orders-db/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/orders-db/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/orders/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/orders/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/orders/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/orders/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/orders/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/orders/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/payment type Container
http://containers/10.60.38.181/sock-shop/payment/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/payment/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/payment/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/payment/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/payment/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/payment/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/queue-master type Container
http://containers/10.60.38.181/sock-shop/queue-master/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/queue-master/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/queue-master/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/queue-master/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/queue-master/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/queue-master/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/rabbitmq type Container
http://containers/10.60.38.181/sock-shop/rabbitmq/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/rabbitmq/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/rabbitmq/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/rabbitmq/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/rabbitmq/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/rabbitmq/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/session-db type Container
http://containers/10.60.38.181/sock-shop/session-db/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/session-db/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/session-db/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/session-db/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/session-db/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/session-db/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/shipping type Container
http://containers/10.60.38.181/sock-shop/shipping/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/shipping/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/shipping/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/shipping/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/shipping/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/shipping/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/user type Container
http://containers/10.60.38.181/sock-shop/user-db type Container
http://containers/10.60.38.181/sock-shop/user-db/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/user-db/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/user-db/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/user-db/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/user-db/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/user-db/Network_Output_Packets type Container
http://containers/10.60.38.181/sock-shop/user/CPU_Usage type Container
http://containers/10.60.38.181/sock-shop/user/MEM_Usage type Container
http://containers/10.60.38.181/sock-shop/user/Network_Input_Bytes type Container
http://containers/10.60.38.181/sock-shop/user/Network_Input_Packets type Container
http://containers/10.60.38.181/sock-shop/user/Network_Output_Bytes type Container
http://containers/10.60.38.181/sock-shop/user/Network_Output_Packets type Container
http://pods/10.60.38.181/sock-shop/carts-648d5f498d-n8hzn type Pod
http://pods/10.60.38.181/sock-shop/carts-648d5f498d-test2 type Pod
http://pods/10.60.38.181/sock-shop/carts-db-64b6cc584f-zd6zz type Pod
http://pods/10.60.38.181/sock-shop/catalogue-b8587c9c5-wfvkt type Pod
http://pods/10.60.38.181/sock-shop/catalogue-db-99cbcbb88-wl52p type Pod
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-55bg5 type Pod
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-6md46 type Pod
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-dbdck type Pod
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-gq9k9 type Pod
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-lkm8b type Pod
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-fqnjt type Pod
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-l9rmn type Pod
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-qcmj7 type Pod
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-tw8sm type Pod
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-zt6zq type Pod
http://pods/10.60.38.181/sock-shop/orders-db-86f5c494b9-xvvhr type Pod
http://pods/10.60.38.181/sock-shop/payment-6c698f689b-vlkhq type Pod
http://pods/10.60.38.181/sock-shop/queue-master-77d854c647-qbgwq type Pod
http://pods/10.60.38.181/sock-shop/rabbitmq-6cf767fcc4-9j5vn type Pod
http://pods/10.60.38.181/sock-shop/session-db-6b57d5cc79-gjxvb type Pod
http://pods/10.60.38.181/sock-shop/shipping-65769d99d7-lmpg7 type Pod
http://pods/10.60.38.181/sock-shop/user-d4cb47c5f-8srcb type Pod
http://pods/10.60.38.181/sock-shop/user-db-6ccbdb454c-q9dxm type Pod
http://services/10.60.38.181/sock-shop/carts type Service
http://services/10.60.38.181/sock-shop/carts-db type Service
http://services/10.60.38.181/sock-shop/carts-db/latency type Service
http://services/10.60.38.181/sock-shop/carts-db/throughput type Service
http://services/10.60.38.181/sock-shop/carts/latency type Service
http://services/10.60.38.181/sock-shop/carts/success_rate type Service
http://services/10.60.38.181/sock-shop/catalogue type Service
http://services/10.60.38.181/sock-shop/catalogue-db type Service
http://services/10.60.38.181/sock-shop/catalogue-db/latency type Service
http://services/10.60.38.181/sock-shop/catalogue-db/throughput type Service
http://services/10.60.38.181/sock-shop/catalogue/latency type Service
http://services/10.60.38.181/sock-shop/catalogue/success_rate type Service
http://services/10.60.38.181/sock-shop/front-end type Service
http://services/10.60.38.181/sock-shop/front-end-external type Service
http://services/10.60.38.181/sock-shop/front-end-external/latency type Service
http://services/10.60.38.181/sock-shop/front-end-external/success_rate type Service
http://services/10.60.38.181/sock-shop/front-end/latency type Service
http://services/10.60.38.181/sock-shop/front-end/success_rate type Service
http://services/10.60.38.181/sock-shop/orders type Service
http://services/10.60.38.181/sock-shop/orders-db type Service
http://services/10.60.38.181/sock-shop/orders-db/latency type Service
http://services/10.60.38.181/sock-shop/orders-db/throughput type Service
http://services/10.60.38.181/sock-shop/orders/latency type Service
http://services/10.60.38.181/sock-shop/orders/success_rate type Service
http://services/10.60.38.181/sock-shop/payment type Service
http://services/10.60.38.181/sock-shop/payment/latency type Service
http://services/10.60.38.181/sock-shop/payment/success_rate type Service
http://services/10.60.38.181/sock-shop/queue-master type Service
http://services/10.60.38.181/sock-shop/queue-master/latency type Service
http://services/10.60.38.181/sock-shop/queue-master/success_rate type Service
http://services/10.60.38.181/sock-shop/rabbitmq type Service
http://services/10.60.38.181/sock-shop/rabbitmq/latency type Service
http://services/10.60.38.181/sock-shop/rabbitmq/success_rate type Service
http://services/10.60.38.181/sock-shop/session-db type Service
http://services/10.60.38.181/sock-shop/session-db/latency type Service
http://services/10.60.38.181/sock-shop/session-db/throughput type Service
http://services/10.60.38.181/sock-shop/shipping type Service
http://services/10.60.38.181/sock-shop/shipping/latency type Service
http://services/10.60.38.181/sock-shop/shipping/success_rate type Service
http://services/10.60.38.181/sock-shop/user type Service
http://services/10.60.38.181/sock-shop/user-db type Service
http://services/10.60.38.181/sock-shop/user-db/latency type Service
http://services/10.60.38.181/sock-shop/user-db/throughput type Service
http://services/10.60.38.181/sock-shop/user/latency type Service
http://services/10.60.38.181/sock-shop/user/success_rate type Service

# relation
http://environment/10.60.38.181 rel:has http://server/10.60.38.181/192.168.199.31
http://environment/10.60.38.181 rel:has http://server/10.60.38.181/192.168.199.32
http://environment/10.60.38.181 rel:has http://server/10.60.38.181/192.168.199.33
http://environment/10.60.38.181 rel:has http://server/10.60.38.181/192.168.199.34
http://environment/10.60.38.181 rel:has http://server/10.60.38.181/192.168.199.35
http://namespace/10.60.38.181/monitoring rel:supervises http://namespace/10.60.38.181/sock-shop
http://pods/10.60.38.181/sock-shop/carts-648d5f498d-n8hzn rel:contains http://containers/10.60.38.181/sock-shop/carts
http://pods/10.60.38.181/sock-shop/carts-648d5f498d-n8hzn rel:deployed_in http://server/10.60.38.181/192.168.199.32
http://pods/10.60.38.181/sock-shop/carts-648d5f498d-n8hzn rel:provides http://services/10.60.38.181/sock-shop/carts
http://pods/10.60.38.181/sock-shop/carts-db-64b6cc584f-zd6zz rel:contains http://containers/10.60.38.181/sock-shop/carts-db
http://pods/10.60.38.181/sock-shop/carts-db-64b6cc584f-zd6zz rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/carts-db-64b6cc584f-zd6zz rel:provides http://services/10.60.38.181/sock-shop/carts-db
http://pods/10.60.38.181/sock-shop/catalogue-b8587c9c5-wfvkt rel:contains http://containers/10.60.38.181/sock-shop/catalogue
http://pods/10.60.38.181/sock-shop/catalogue-b8587c9c5-wfvkt rel:deployed_in http://server/10.60.38.181/192.168.199.34
http://pods/10.60.38.181/sock-shop/catalogue-b8587c9c5-wfvkt rel:provides http://services/10.60.38.181/sock-shop/catalogue
http://pods/10.60.38.181/sock-shop/catalogue-db-99cbcbb88-wl52p rel:contains http://containers/10.60.38.181/sock-shop/catalogue-db
http://pods/10.60.38.181/sock-shop/catalogue-db-99cbcbb88-wl52p rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/catalogue-db-99cbcbb88-wl52p rel:provides http://services/10.60.38.181/sock-shop/catalogue-db
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-55bg5 rel:contains http://containers/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-55bg5 rel:deployed_in http://server/10.60.38.181/192.168.199.32
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-55bg5 rel:provides http://services/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-6md46 rel:contains http://containers/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-6md46 rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-6md46 rel:provides http://services/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-dbdck rel:contains http://containers/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-dbdck rel:deployed_in http://server/10.60.38.181/192.168.199.32
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-dbdck rel:provides http://services/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-gq9k9 rel:contains http://containers/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-gq9k9 rel:deployed_in http://server/10.60.38.181/192.168.199.34
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-gq9k9 rel:provides http://services/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-lkm8b rel:contains http://containers/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-lkm8b rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/front-end-858b4ff57-lkm8b rel:provides http://services/10.60.38.181/sock-shop/front-end
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-fqnjt rel:contains http://containers/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-fqnjt rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-fqnjt rel:provides http://services/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-l9rmn rel:contains http://containers/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-l9rmn rel:deployed_in http://server/10.60.38.181/192.168.199.32
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-l9rmn rel:provides http://services/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-qcmj7 rel:contains http://containers/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-qcmj7 rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-qcmj7 rel:provides http://services/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-tw8sm rel:contains http://containers/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-tw8sm rel:deployed_in http://server/10.60.38.181/192.168.199.34
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-tw8sm rel:provides http://services/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-zt6zq rel:contains http://containers/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-zt6zq rel:deployed_in http://server/10.60.38.181/192.168.199.32
http://pods/10.60.38.181/sock-shop/orders-6496fcd6f7-zt6zq rel:provides http://services/10.60.38.181/sock-shop/orders
http://pods/10.60.38.181/sock-shop/orders-db-86f5c494b9-xvvhr rel:contains http://containers/10.60.38.181/sock-shop/orders-db
http://pods/10.60.38.181/sock-shop/orders-db-86f5c494b9-xvvhr rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/orders-db-86f5c494b9-xvvhr rel:provides http://services/10.60.38.181/sock-shop/orders-db
http://pods/10.60.38.181/sock-shop/payment-6c698f689b-vlkhq rel:contains http://containers/10.60.38.181/sock-shop/payment
http://pods/10.60.38.181/sock-shop/payment-6c698f689b-vlkhq rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/payment-6c698f689b-vlkhq rel:provides http://services/10.60.38.181/sock-shop/payment
http://pods/10.60.38.181/sock-shop/queue-master-77d854c647-qbgwq rel:contains http://containers/10.60.38.181/sock-shop/queue-master
http://pods/10.60.38.181/sock-shop/queue-master-77d854c647-qbgwq rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/queue-master-77d854c647-qbgwq rel:provides http://services/10.60.38.181/sock-shop/queue-master
http://pods/10.60.38.181/sock-shop/rabbitmq-6cf767fcc4-9j5vn rel:contains http://containers/10.60.38.181/sock-shop/rabbitmq
http://pods/10.60.38.181/sock-shop/rabbitmq-6cf767fcc4-9j5vn rel:deployed_in http://server/10.60.38.181/192.168.199.34
http://pods/10.60.38.181/sock-shop/rabbitmq-6cf767fcc4-9j5vn rel:provides http://services/10.60.38.181/sock-shop/rabbitmq
http://pods/10.60.38.181/sock-shop/session-db-6b57d5cc79-gjxvb rel:contains http://containers/10.60.38.181/sock-shop/session-db
http://pods/10.60.38.181/sock-shop/session-db-6b57d5cc79-gjxvb rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/session-db-6b57d5cc79-gjxvb rel:provides http://services/10.60.38.181/sock-shop/session-db
http://pods/10.60.38.181/sock-shop/shipping-65769d99d7-lmpg7 rel:contains http://containers/10.60.38.181/sock-shop/shipping
http://pods/10.60.38.181/sock-shop/shipping-65769d99d7-lmpg7 rel:deployed_in http://server/10.60.38.181/192.168.199.34
http://pods/10.60.38.181/sock-shop/shipping-65769d99d7-lmpg7 rel:provides http://services/10.60.38.181/sock-shop/shipping
http://pods/10.60.38.181/sock-shop/user-d4cb47c5f-8srcb rel:contains http://containers/10.60.38.181/sock-shop/user
http://pods/10.60.38.181/sock-shop/user-d4cb47c5f-8srcb rel:deployed_in http://server/10.60.38.181/192.168.199.33
http://pods/10.60.38.181/sock-shop/user-d4cb47c5f-8srcb rel:provides http://services/10.60.38.181/sock-shop/user
http://pods/10.60.38.181/sock-shop/user-db-6ccbdb454c-q9dxm rel:contains http://containers/10.60.38.181/sock-shop/user-db
http://pods/10.60.38.181/sock-shop/user-db-6ccbdb454c-q9dxm rel:deployed_in http://server/10.60.38.181/192.168.199.32
http://pods/10.60.38.181/sock-shop/user-db-6ccbdb454c-q9dxm rel:provides http://services/10.60.38.181/sock-shop/user-db
```

------

## 推理结果

当关系充足的时候，如果去掉Object中定义的一些类，也可以通过本体推理及推理出这些对象所属的类，该ttl数据符合实验室数据库情况