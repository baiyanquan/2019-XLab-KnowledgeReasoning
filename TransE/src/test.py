from array import array
import numpy
from numpy import *
import operator

class Test:

    def __init__(self, entityList, entityVectorList, relationList ,relationVectorList, tripleListTrain, tripleListTest, label = "head", isFit = False):

        self.entityList = {}
        self.relationList = {}
        for name, vec in zip(entityList, entityVectorList):  # 打包为元组的列表
            self.entityList[name] = vec
        for name, vec in zip(relationList, relationVectorList):
            self.relationList[name] = vec

        self.tripleListTrain = tripleListTrain
        self.tripleListTest = tripleListTest
        self.rank = []
        self.label = label
        self.isFit = isFit


    def getRank(self):
        cou = 0
        for triplet in self.tripleListTest:
            rankList = {}

            for entityTemp in self.entityList.keys():
                if self.label == "head":
                    corruptedTriplet = (entityTemp, triplet[1], triplet[2])
                    if self.isFit and (corruptedTriplet in self.tripleListTrain):
                        continue
                    rankList[entityTemp] = distance(self.entityList[entityTemp], self.relationList[triplet[1]], self.entityList[triplet[2]])
                else:#
                    corruptedTriplet = (triplet[0], triplet[1], entityTemp)
                    if self.isFit and (corruptedTriplet in self.tripleListTrain):
                        continue
                    rankList[entityTemp] = distance(self.entityList[triplet[0]], self.relationList[triplet[1]], self.entityList[entityTemp])

            nameRank = sorted(rankList.items(), key = operator.itemgetter(1))

            if self.label == 'head':
                numTri = 0
            else:
                numTri = 2
            x = 1

            for i in nameRank:
                if i[0] == triplet[numTri]:
                    break
                x += 1

            self.rank.append((triplet, triplet[numTri], nameRank[0][0], x))
            print(x)
            cou += 1
            if cou % 10000 == 0:
                print(cou)


    def getRelationRank(self):
        cou = 0
        self.rank = []
        for triplet in self.tripleListTest:
            rankList = {}

            for relationTemp in self.relationList.keys():
                corruptedTriplet = (triplet[0], relationTemp, triplet[2] )
                if self.isFit and (corruptedTriplet in self.tripleListTrain):
                    continue
                rankList[relationTemp] = distance(self.entityList[triplet[0]], self.relationList[relationTemp], self.entityList[triplet[2]])
            nameRank = sorted(rankList.items(), key = operator.itemgetter(1))  #字典列表排序 distance
            x = 1
            for i in nameRank:
                if i[0] == triplet[1]:
                    break
                x += 1
            self.rank.append((triplet, triplet[1], nameRank[0][0], x))

            print(x)
            cou += 1

            if cou % 10000 == 0:
                print(cou)

    def getMeanRank(self):
        num = 0
        for r in self.rank:
            num += r[3]
        return num/len(self.rank)

    def writeRank(self, dir):

        print("写入")
        file = open(dir, 'w')
        for r in self.rank:
            file.write(str(r[0]) + "\t")
            file.write(str(r[1]) + "\t")
            file.write(str(r[2]) + "\t")
            file.write(str(r[3]) + "\n")
        file.close()

def distance(h, r, t):
    h = array(h)
    t = array(t)
    r = array(r)
    s = h + r - t
    return numpy.linalg.norm(s)

def openD(dir):
    #triple = (head, tail, relation)
    list = []
    with open(dir) as file:
        lines = file.readlines()
        for line in lines:
            triple = line.strip().split('\t')
            if(len(triple)<3):
                continue
            list.append(tuple(triple))

    return list

def loadData(str):
    fr = open(str)
    sArr = [line.strip().split('\t') for line in fr.readlines()]
    datArr = [[float(s) for s in line[1][1:-1].split(", ")] for line in sArr]
    nameArr = [line[0] for line in sArr]

    return datArr, nameArr

if __name__ == '__main__':

    dirTrain = "F:\\KG\\TransE\\data\\train.txt"
    tripleListTrain = openD(dirTrain)

    dirTest = "F:\\KG\\TransE\\data\\test.txt"
    tripleListTest = openD(dirTest)

    dirEntityVector = "F:\\KG\\TransE\\data\\entityVector.txt"
    entityVectorList, entityList = loadData(dirEntityVector)

    dirRelationVector = "F:\\KG\\TransE\\data\\relationVector.txt"
    relationVectorList, relationList = loadData(dirRelationVector)

    print("test")

    testHeadRaw = Test(entityList, entityVectorList, relationList, relationVectorList, tripleListTrain, tripleListTest,isFit = False, label = "head")
    testHeadRaw.getRank()
    print(testHeadRaw.getMeanRank())
    testHeadRaw.writeRank("F:\\KG\\TransE\\data\\testHeadRaw.txt")

    testHeadRaw.getRelationRank()
    print(testHeadRaw.getMeanRank())
    testHeadRaw.writeRank("F:\\KG\\TransE\\data\\testRelationRaw.txt")

    testTailRaw = Test(entityList, entityVectorList, relationList, relationVectorList, tripleListTrain, tripleListTest, isFit = False, label = "tail")
    testTailRaw.getRank()
    print(testTailRaw.getMeanRank())
    testTailRaw.writeRank("F:\\KG\\TransE\\data\\testTailRaw.txt")



    testHeadFit = Test(entityList, entityVectorList, relationList, relationVectorList, tripleListTrain, tripleListTest, isFit = True, label = "head")
    testHeadFit.getRank()
    print(testHeadFit.getMeanRank())
    testHeadFit.writeRank("F:\\KG\\TransE\\data\\testHeadFit.txt")

    testHeadFit.getRelationRank()
    print(testHeadFit.getMeanRank())
    testHeadFit.writeRank("F:\\KG\\TransE\\data\\testRelationFit.txt")

    testTailFit = Test(entityList, entityVectorList, relationList, relationVectorList, tripleListTrain, tripleListTest, isFit = True, label = "tail")
    testTailFit.getRank()
    print(testTailFit.getMeanRank())
    testTailFit.writeRank("F:\\KG\\TransE\\data\\testTailFit.txt")