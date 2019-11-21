# encoding:utf-8

from array import array
from math import fabs
from random import uniform, sample
from copy import deepcopy
from numpy import *


class TransE:

    def __init__(self, entityList, relationList, tripleList, margin = 1, learingRate = 0.01, k = 20, L1 = True):
        self.margin = margin
        self.learingRate = learingRate
        self.k = k  #向量维度
        self.entityList = entityList
        #开始，entity是list；
        # 初始化后，变为字典，key是entity，values是其向量（使用narray）。
        self.relationList = relationList    #同上
        self.tripleList = tripleList    #同上
        self.loss = 0
        self.L1 = L1

    def initialize(self):
        '''
        初始化向量
        '''
        entityVectorList = {}
        relationVectorList = {}

        for entity in self.entityList:
            n = 0
            entityVector = []

            while n < self.k:
                ram = uniform(-6/math.sqrt(self.k),6/math.sqrt(self.k))   #初始化的范围
                entityVector.append(ram)
                n += 1

            entityVector = norm(entityVector)   #归一化
            entityVectorList[entity] = entityVector
        print("entityVector初始化完成，数量是%d"%len(entityVectorList))

        for relation in self. relationList:
            n = 0
            relationVector = []

            while n < self.k:
                ram = uniform(-6/math.sqrt(self.k),6/math.sqrt(self.k))    #初始化的范围
                relationVector.append(ram)
                n += 1

            relationVector = norm(relationVector)#归一化
            relationVectorList[relation] = relationVector

        print("relationVectorList初始化完成，数量是%d"%len(relationVectorList))
        self.entityList = entityVectorList
        self.relationList = relationVectorList

    def train(self, cI):
        batch_size = len(self.tripleList) // cI  #取整
        for cycleIndex in range(cI):
            Sbatch = sample(self.tripleList, batch_size)
            Tbatch = [] #元组对（原三元组，打碎的三元组）的列表 ：{((h,r,t),(h',r,t'))}

            for sbatch in Sbatch:
                corruptedTriplet = self.getCorruptedTriplet(sbatch)
                #原三元组和打碎的三元组的元组tuple
                if((sbatch, corruptedTriplet) not in Tbatch):
                    Tbatch.append((sbatch, corruptedTriplet))
            self.update(Tbatch)

            if cycleIndex % 100 == 0:
                print("第%d次循环"%cycleIndex)
                print("loss: ")
                print(self.loss)
                self.writeRelationVector("F:\\KG\\TranE\\data\\relationVector.txt")
                self.writeEntityVector("F:\\KG\\TranE\\data\\entityVector.txt")
                self.loss = 0

    def getCorruptedTriplet(self, triplet):
        '''
        training triplets with either the head or tail replaced by a random entity (but not both at the same time)
        :param triplet:
        :return corruptedTriplet:
        '''
        i = uniform(-1, 1)
        if i < 0:   #小于0，打坏三元组的第一项
            while True:
                entityTemp = sample(self.entityList.keys(), 1)[0]
                if entityTemp != triplet[0]:
                    break
            corruptedTriplet = (entityTemp, triplet[1], triplet[2])

        else:#大于等于0，打坏三元组的第三项
            while True:
                entityTemp = sample(self.entityList.keys(), 1)[0]
                if entityTemp != triplet[2]:
                    break
            corruptedTriplet = (triplet[0], triplet[1], entityTemp)

        return corruptedTriplet

    def update(self, Tbatch):

        copyEntityList = deepcopy(self.entityList)
        copyRelationList = deepcopy(self.relationList)

        for sbatch, corruptedTriplet in Tbatch:
            #原三元组,打碎的三元组

            # 取copy里的vector累积更新
            headEntityVector = copyEntityList[sbatch[0]]
            relationVector = copyRelationList[sbatch[1]]
            tailEntityVector = copyEntityList[sbatch[2]]

            headEntityVectorWithCorruptedTriplet = copyEntityList[corruptedTriplet[0]]
            tailEntityVectorWithCorruptedTriplet = copyEntityList[corruptedTriplet[2]]

            # 取原始的vector计算梯度
            headEntityVectorBeforeBatch = self.entityList[sbatch[0]]
            relationVectorBeforeBatch = self.relationList[sbatch[1]]
            tailEntityVectorBeforeBatch = self.entityList[sbatch[2]]

            headEntityVectorWithCorruptedTripletBeforeBatch = self.entityList[corruptedTriplet[0]]
            tailEntityVectorWithCorruptedTripletBeforeBatch = self.entityList[corruptedTriplet[2]]

            if self.L1:
                distTriplet = distanceL1(headEntityVectorBeforeBatch, relationVectorBeforeBatch, tailEntityVectorBeforeBatch)
                distCorruptedTriplet = distanceL1(headEntityVectorWithCorruptedTripletBeforeBatch, relationVectorBeforeBatch, tailEntityVectorWithCorruptedTripletBeforeBatch)
            else:
                distTriplet = distanceL2(headEntityVectorBeforeBatch, relationVectorBeforeBatch, tailEntityVectorBeforeBatch)
                distCorruptedTriplet = distanceL2(headEntityVectorWithCorruptedTripletBeforeBatch, relationVectorBeforeBatch, tailEntityVectorWithCorruptedTripletBeforeBatch)

            eg = self.margin + distTriplet - distCorruptedTriplet

            if eg > 0: #[function]+ 是一个取正值的函数
                self.loss += eg
                #梯度
                tempPositive = 2 * self.learingRate * (tailEntityVectorBeforeBatch - headEntityVectorBeforeBatch - relationVectorBeforeBatch)
                tempNegtative = 2 * self.learingRate * (tailEntityVectorWithCorruptedTripletBeforeBatch - headEntityVectorWithCorruptedTripletBeforeBatch - relationVectorBeforeBatch)

                if self.L1:

                    for i in range(self.k):
                        if tempPositive[i] >= 0:
                            tempPositive[i] = 1
                        else:
                            tempPositive[i] = -1

                        if tempNegtative[i] >= 0:
                            tempNegtative[i] = 1
                        else:
                            tempNegtative[i] = -1

                headEntityVector = headEntityVector + tempPositive
                tailEntityVector = tailEntityVector - tempPositive
                relationVector = relationVector + tempPositive - tempNegtative
                headEntityVectorWithCorruptedTriplet = headEntityVectorWithCorruptedTriplet - tempNegtative
                tailEntityVectorWithCorruptedTriplet = tailEntityVectorWithCorruptedTriplet + tempNegtative

                #归一化刚更新的向量
                copyEntityList[sbatch[0]] = norm(headEntityVector)
                copyRelationList[sbatch[1]] = norm(relationVector)
                copyEntityList[sbatch[2]] = norm(tailEntityVector)
                copyEntityList[corruptedTriplet[0]] = norm(headEntityVectorWithCorruptedTriplet)
                copyEntityList[corruptedTriplet[2]] = norm(tailEntityVectorWithCorruptedTriplet)

        self.entityList = copyEntityList
        self.relationList = copyRelationList

    def writeEntityVector(self, dir):

        print("写入实体")
        entityVectorFile = open(dir, 'w')

        for entity in self.entityList.keys():
            entityVectorFile.write(entity+"\t")
            entityVectorFile.write(str(self.entityList[entity].tolist()))
            entityVectorFile.write("\n")

        entityVectorFile.close()

    def writeRelationVector(self, dir):

        print("写入关系")
        relationVectorFile = open(dir, 'w')

        for relation in self.relationList.keys():
            relationVectorFile.write(relation + "\t")
            relationVectorFile.write(str(self.relationList[relation].tolist()))
            relationVectorFile.write("\n")

        relationVectorFile.close()

def norm(list):
    '''
    归一化
    :param 向量
    :return: 向量的平方和的开方后的向量
    '''
    var = linalg.norm(list)
    i = 0
    while i < len(list):
        list[i] = list[i]/var
        i += 1
    return array(list)

def distanceL1(h,r,t):
    return sum(fabs(h + r - t))

def distanceL2(h,r,t):
    #为方便求梯度，去掉sqrt
    return sum(math.square(h + r - t))

def openDetailsAndId(dir):

    list = []
    with open(dir) as file:
        lines = file.readlines()
        for line in lines:
            DetailsAndId = line.strip().split('\t')
            list.append(DetailsAndId[0])
    return list

def openTrain(dir):

    list = []
    with open(dir) as file:
        lines = file.readlines()
        for line in lines:
            triple = line.strip().split('\t')
            if(len(triple)<3):
                continue
            list.append(tuple(triple))

    return list

if __name__ == '__main__':

    dirEntity = "F:\\KG\\TransE\\data\\entity2id.txt"
    entityList = openDetailsAndId(dirEntity)

    dirRelation = "F:\\KG\\TransE\\data\\relation2id.txt"
    relationList = openDetailsAndId(dirRelation)

    dirTrain = "F:\\KG\\TransE\\data\\train.txt"
    tripleList = openTrain(dirTrain)

    transE = TransE(entityList,relationList,tripleList, margin=1, k = 20)

    print("TranE初始化")
    transE.initialize()

    print("开始训练")
    transE.train(cI=800)

    transE.writeRelationVector(dir = "F:\\KG\\TransE\\data\\relationVector.txt")
    transE.writeEntityVector(dir = "F:\\KG\\TransE\\data\\entityVector.txt")