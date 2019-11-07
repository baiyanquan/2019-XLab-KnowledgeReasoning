# 本体推理  | Ontology Reasoning

Table of Contents
=================

   * [本体推理  | Ontology Reasoning](#本体推理---ontology-reasoning)
      * [测试数据](#测试数据)
      * [Triples Before Reasoning](#triples-before-reasoning)
      * [Triples After Reasoning](#triples-after-reasoning)
      * [核心代码](#核心代码)

------

## 测试数据

```ttl
@prefix owl: <http://www.w3.org/2002/07/owl#> .
@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix rel: <http://localhost/KGns/relationship/> .

@prefix : <http://localhost/KGns/> .
@prefix namespace: <http://localhost/KGns/namespace/> .
@prefix pods: <http://localhost/KGns/Pod/> .
@prefix containers: <http://localhost/KGns/Container/> .
@prefix services: <http://localhost/KGns/Service/> .
@prefix environment: <http://localhost/KGns/Environment/> .

:Pod rdf:type owl:Class .
:Container rdf:type owl:Class .
:Service rdf:type owl:Class .

rel:contain rdf:type owl:ObjectProperty;
        rdfs:domain :Pod;
        rdfs:range :Container .

rel:provide rdf:type owl:ObjectProperty;
        rdfs:domain :Pod;
        rdfs:range :Service .

pods:1 rdf:type :Pod .
pods:2 rdf:type :Pod .

pods:1 rel:contain containers:1 .
pods:1 rel:contain containers:2 .
pods:2 rel:contain containers:3 .
pods:2 rel:contain containers:4 .
pods:1 rel:provide services:1 .
pods:2 rel:provide services:1 .
```

------

## Triples Before Reasoning

```txt
# Class
Container type Class
Pod type Class
Service type Class

# ObjectProperty
rel:contain domain Pod
rel:contain range Container
rel:contain type ObjectProperty
rel:provide domain Pod
rel:provide range Service
rel:provide type ObjectProperty

# object
pod1 type Pod
pod2 type Pod

# relation
pod1 rel:contain container1
pod1 rel:contain container2
pod1 rel:provide service1
pod2 rel:contain container3
pod2 rel:contain container4
pod2 rel:provide service1
```

------

## Triples After Reasoning

本体推理机通过ttl中的规则推出

- container1，container2，container3，container4的类型
- service1的类型

```txt
# Class
Container type Class
Pod type Class
Service type Class

# ObjectProperty
rel:contain domain Pod
rel:contain range Container
rel:contain type ObjectProperty
rel:provide domain Pod
rel:provide range Service
rel:provide type ObjectProperty

# object
container1 type Container
container2 type Container
container3 type Container
container4 type Container
pod1 type Pod
pod2 type Pod
service1 type Service

# relation
pod1 rel:contain container1
pod1 rel:contain container2
pod1 rel:provide service1
pod2 rel:contain container3
pod2 rel:contain container4
pod2 rel:provide service1
```

------

## 核心代码

- **推理**

  ```java
  // 为本体创建Model
  Model ontologyModel = ModelFactory.createDefaultModel();
  ontologyModel.read("data/demo.ttl");
  
  // 创建一个新Model将本体与实例数据进行合并
  Model fusionModel = ModelFactory.createDefaultModel();
  fusionModel.add(ontologyModel);
  
  // 在合并后的数据模型上进行OWL推理
  Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
  InfModel inf = ModelFactory.createInfModel(reasoner, fusionModel);
  
  ontologyModel.close();
  fusionModel.close();
  ```

- **过滤数据**

  ```java
  String  subject   = stmt.getSubject().toString();
  String  predicate = stmt.getPredicate().toString();
  String  object    = stmt.getObject().toString();
  
  subject = prefix_map.containsKey(subject)?prefix_map.get(subject):subject;
  predicate = prefix_map.containsKey(predicate)?prefix_map.get(predicate):predicate;
  object = prefix_map.containsKey(object)?prefix_map.get(object):object;
  
  if(object.equals("Class") &&                            //类信息
     (subject.equals("Container") || subject.equals("Pod") || subject.equals("Service"))){
    stmt_container.get(0).add(subject + " " + predicate + " " + object);
  }else if((subject.substring(0,3).equals("rel")) &&      //属性信息
           (predicate.equals("type") || predicate.equals("domain") || predicate.equals("range")) &&
           (object.equals("ObjectProperty") || object.equals("Container") || object.equals("Pod") || object.equals("Service"))){
    stmt_container.get(1).add(subject + " " + predicate + " " + object);
  }else if(predicate.equals("type") &&                     //对象
           (object.equals("Container") || object.equals("Pod") || object.equals("Service"))){
    stmt_container.get(2).add(subject + " " + predicate + " " + object);
  }else if(predicate.substring(0,3).equals("rel")) {      //对象之间的关系
    stmt_container.get(3).add(subject + " " + predicate + " " + object);
  }else{
    System.out.println(stmt);
  }
  ```

  