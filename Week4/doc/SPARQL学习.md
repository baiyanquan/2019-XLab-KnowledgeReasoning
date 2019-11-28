# SPARQL

SPARQL查询是基于图匹配的思想。我们把上述的查询与RDF图进行匹配，找到符合该匹配模式的所有子图，最后得到变量的值。就上面这个例子而言，在RDF图中找到匹配的子图后，将"罗纳尔多·路易斯·纳萨里奥·德·利马"和“?x”绑定，我们就得到最后的结果。简而言之，SPARQL查询分为三个步骤：

1. 构建查询图模式，表现形式就是带有变量的RDF。
2. 匹配，匹配到符合指定图模式的子图。
3. 绑定，将结果绑定到查询图模式对应的变量上。

------

## 查询框架

- **声明：**

    ```SPARQL
  PREFIX st:<http://xxxx.com/yyy>
    ```

- **查询模式：**

  ```SPARQL
  SELECT ?name
  ```

- **数据集：**

    ```SPARQL
    FROM <http://xxxx.com/yyy>
    ```

- **图模式：**

    ```SPARQL
    WHERE {?x st:name ?name.}
    ```

- **结果修饰：**

    ```SPARQL
  ORDER BY ?name  
    ```

------

## ARQ

**ttl数据**

```ttl
@prefix st: <http://studysparql.com/studenttable/> .

st:zz st:schoolID "1754060" .
st:zz st:homeland "Tonghua" .
st:zz st:school "Tongji" .

st:yt st:schoolID "1852137" .
st:yt st:homeland "Taiyuan" .
st:yt st:school "Tongji" .
```

**SPARQL查询语句**

```SPARQL
PREFIX st:<http://studysparql.com/studenttable/>

SELECT ?zz_homeland
WHERE{
 st:zz st:homeland ?zz_homeland .
}
```

**查询命令**

```bash
arq --data student.ttl --query student.rq
```

 **查询结果**

```
---------------
| zz_homeland |
===============
| "Tonghua"   |
---------------
```

------

## 查询实例

### 根据谓语查找object

```SPARQL
PREFIX foaf:  <http://xmlns.com/foaf/0.1/>
SELECT ?name
WHERE {
    ?person foaf:name ?name .
}limit 10
```

### 同一个subject通过某个predicate值查找另一个object

已知名字是7-Eleven, Inc.，查找它的主页

```SPARQL
select ?s ?homepage
where{
  ?s foaf:name "7-Eleven, Inc."@en.
  ?s foaf:homepage ?homepage.
}limit 10
```



### 查询数据集中所有数据类型

```SPARQL
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

select distinct ?class where{
  ?sub rdf:type ?class .
}
```



------

## 查询练习

[维基百科RDF数据查询](http://dbpedia.org/sparql/)

1. 查询一个三元组（基本图形模式）：得克萨斯州城市名单

   ```SPARQL
   prefix ref:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
   select * where{
    ?city ref:type<http://dbpedia.org/class/yago/WikicatCitiesInTexas>.
   }
   ```

2. 查询两个三元组：

   ```SPARQL
   prefix ref:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
   prefix refs:<http://www.w3.org/2000/01/rdf-schema#>
   prefix dbp:<http://dbpedia.org/ontology/>
   select * where{
     ?city rdf:type<http://dbpedia.org/class/yago/WikicatCitiesInTexas>.
     ?city dbp:popolationTotal ?popTotal.
   }
   ```

3. 查询三个三元组

   ```SPARQL
   select * where{
     ?city rdf:type<http://dbpedia.org/class/yago/WikicatCitiesInTexas>.
     dbp:populationTotal ?popTotal.
     dbp:populationMetro ?popMetro.
   }
   ```

   