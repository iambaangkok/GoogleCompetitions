
import math
from xml.etree.ElementTree import tostring

DEBUG = False

T = int(input())

for testcase in range(0,T):

    numbers = input().split(' ')
    nRange = int(numbers[0])
    X = int(numbers[1])
    Y = int(numbers[2])
    nParts = X+Y

    totalSum = nRange * (nRange+1) / 2
    targetSumX = totalSum*X/nParts
    targetSumY = totalSum*Y/nParts
    
    if DEBUG:
        print(nRange, ' ', X, ' ', Y, ' ', totalSum, ' ', targetSumX, ' ', targetSumY)
    
    if (math.floor(targetSumX) != targetSumX) or (math.floor(targetSumY) != targetSumY):
        print("Case #"+str(testcase+1)+": IMPOSSIBLE")
        continue
    
    pickedX = []
    for i in range(nRange, 0, -1):
        if targetSumX == 0:
            break
        if(targetSumX - i >= 0):
            pickedX.append(i)
            targetSumX -= i

    if targetSumX == 0:
        print("Case #"+str(testcase+1)+": POSSIBLE")
        print(len(pickedX))
        print(' '.join(map(str,pickedX)))
        continue


    notPickedY = []
    for i in range(nRange, 0, -1):
        if(targetSumY - i >= 0):
            targetSumY -= i
        else:
            notPickedY.append(i)
    
    pickedX = notPickedY
    if targetSumY == 0:
        print("Case #"+str(testcase+1)+": POSSIBLE")
        print(len(pickedX))
        print(' '.join(map(str,pickedX)))
        continue
    print("Case #", testcase+1, ": ", "IMPOSSIBLE")


            


    

    
    

    