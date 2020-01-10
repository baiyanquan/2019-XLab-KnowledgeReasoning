# 2019-XLab-KnowledgeReasoning

[TOC]

------

## Introduction

Maintenance of established network hardware and software in large organizations.

However, in the face of massive monitoring data and huge distributed system, all kinds of operation and maintenance subjects have their own characteristics and rules and are interrelated, which increases the difficulty of operation and maintenance decision-making, and operation and maintenance knowledge graph emerges at the right moment.

## How to Build 

### Environment Requirements 

- IntelliJ IDEA
  
- Springboot 2.2.1
- apache-jena-3.13.1
- apache-jena-fuseki-3.13.1
- neo4j-community-3.5.5
  
### Get the Project 

- get the code from github
  
  > git clone https://github.com/baiyanquan/2019-XLab-KnowledgeReasoning.git
  
### Import the Project to IDE

Open the project in IntelliJ IDEA.

## How to Run

1. run `src/main/java/com/tongji/knowledgereasoning/KnowledgereasoningApplication.java` to initiate the project
2. visit `http://localhost/8080` in your browser
3. construct the metadata layer first
4. ontology reasoning
5. rule reasoning
6. require for the event

## How to Use 

- Construct metadata layer
- Ontology reasoning
- Rule reasoning
- Deep learning reasoning based on TransE

## Code Structure 

```
.
├── README.md
├── data
│   ├── SaveData
│   │   └── labdata.ttl
│   ├── event.ttl
│   ├── newOntology.ttl
│   ├── newOntology_after_reasoning.ttl
│   └── newOntology_fix_typo.ttl
├── info.xml
├── knowledgereasoning.iml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── tongji
    │   │           └── knowledgereasoning
    │   │               ├── KnowledgereasoningApplication.java
    │   │               ├── controller
    │   │               │   ├── EventController.java
    │   │               │   ├── HomeController.java
    │   │               │   ├── MetadataLayerConstructController.java
    │   │               │   ├── OntologyReasonerController.java
    │   │               │   ├── QueryController.java
    │   │               │   ├── RefactorDataController.java
    │   │               │   ├── RuleReasonerController.java
    │   │               │   └── TranseReasonerController.java
    │   │               ├── dao
    │   │               │   ├── FusekiDao.java
    │   │               │   ├── LabDao.java
    │   │               │   └── NeoDao.java
    │   │               ├── service
    │   │               │   ├── EntityExtraction.java
    │   │               │   ├── EventService.java
    │   │               │   ├── MetadataLayerConstructService.java
    │   │               │   ├── OntologyReasonerService.java
    │   │               │   ├── OntologyReasonerServiceImp.java
    │   │               │   ├── PredicateExtraction.java
    │   │               │   ├── QueryService.java
    │   │               │   ├── RefactorDataService.java
    │   │               │   ├── RuleReasonerService.java
    │   │               │   ├── RuleReasonerServiceImp.java
    │   │               │   └── TranseService.java
    │   │               └── util
    │   │                   ├── FusekiDao.java
    │   │                   └── Operations.java
    │   └── resources
    │       ├── application.properties
    │       ├── static
    │       │   ├── css
    │       │   │   └── style.css
    │       │   ├── img
    │       │   │   └── bg.jpg
    │       │   └── js
    │       │       ├── Event.js
    │       │       ├── MetadataLayer.js
    │       │       ├── Ontology.js
    │       │       ├── Query.js
    │       │       ├── Rule.js
    │       │       ├── jquery-1.7.1.js
    │       │       ├── jquery.js
    │       │       └── main.js
    │       └── templates
    │           └── index.html
    └── test
        └── java
            └── com
                └── tongji
                    └── knowledgereasoning
                        └── KnowledgereasoningApplicationTests.java

23 directories, 49 files
```

