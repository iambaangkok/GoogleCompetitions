
from copy import deepcopy
import math
import collections

DEBUG = True

T = int(input())

for testcase in range(0,T):

    inputs = input().split(' ')

    N = int(inputs[0])
    L = int(inputs[1]) 

    
    stick = {}
    # position: distance from left side of stick, 0 to L-1
    # direction : 0 left, 1 right

    for i in range(N):
        inputs = input().split(' ')
        pos = int(inputs[0])
        dir = int(inputs[1])

        ant = {}
        ant["number"] = i+1
        ant["pos"] = pos
        ant["dir"] = dir

        stick[pos] = []
        stick[pos].append(ant)
    stick = collections.OrderedDict(sorted(stick.items()))
    if DEBUG:
        print(stick)


    antOrder = []
    while len(antOrder) < N and len(stick) > 0:
        newStick = collections.OrderedDict()
        for i in range(len(stick)):
            poss, ants = list(stick.items())[i]
            if DEBUG:
                print(poss, ants)
            leftpos = -1
            leftants = []
            rightpos = -1
            rightants = []
            if i > 0:
                leftpos, leftants = list(stick.items())[i-1]
            if i < len(stick)-1:
                rightpos, rightants = list(stick.items())[i+1]
            for ant in ants:
                pos = ant["pos"]
                dir = ant["dir"]
                if dir == 0 : # left
                    for ant2 in leftants:
                        pos2 = ant2["pos"]
                        dir2 = ant2["dir"]
                        if pos - pos2 == 1 and leftpos != -1:
                            print("L1")
                            if dir2 == 0:
                                pass
                            elif dir2 == 1: # bounce
                                if not pos in newStick:
                                    newStick[pos] = []
                                newAnt = deepcopy(ant)
                                newAnt["dir"] = 1
                                newStick[pos].append(newAnt)
                                break
                        elif pos - pos2 == 2 and leftpos != -1:
                            print("L2")
                            if dir2 == 0:
                                pass
                            elif dir2 == 1: # bounce
                                if not pos in newStick:
                                    newStick[pos] = []
                                newAnt = deepcopy(ant)
                                newAnt["pos"] = pos-1
                                newAnt["dir"] = 1
                                if pos-1 < 0:
                                    antOrder.append(newAnt["label"])
                                else:
                                    newStick[pos].append(newAnt)
                                break
                        else:
                            print("L3")
                            if not pos in newStick:
                                newStick[pos] = []
                            newAnt = deepcopy(ant)
                            newAnt["pos"] = pos-1
                            if pos-1 < 0:
                                antOrder.append(newAnt["label"])
                            else:
                                newStick[pos].append(newAnt)
                            break
                elif dir == 1: # right
                    for ant2 in rightants:
                        pos2 = ant2["pos"]
                        dir2 = ant2["dir"]
                        if pos2 - pos == 1 and rightpos != -1:
                            print("R1")
                            if dir2 == 0: # bounce
                                if not pos in newStick:
                                    newStick[pos] = []
                                newAnt = deepcopy(ant)
                                newAnt["dir"] = 0
                                newStick[pos].append(newAnt)
                                break
                            elif dir2 == 1:
                                pass
                        elif pos - pos2 == 2 and rightpos != -1:
                            print("R2")
                            if dir2 == 0: # bounce
                                if not pos in newStick:
                                    newStick[pos] = []
                                newAnt = deepcopy(ant)
                                newAnt["pos"] = pos+1
                                newAnt["dir"] = 0
                                if pos >= L:
                                    antOrder.append(newAnt["label"])
                                else:
                                    newStick[pos].append(newAnt)
                                break
                            elif dir2 == 1:
                                pass
                        else:
                            print("R3")
                            if not pos in newStick:
                                newStick[pos] = []
                            newAnt = deepcopy(ant)
                            newAnt["pos"] = pos+1
                            if pos >= L:
                                antOrder.append(newAnt["label"])
                            else:
                                newStick[pos].append(newAnt)
                            break
        stick = newStick
        if DEBUG:
            print(stick)
    print("Case #"+str(testcase+1)+":", ' '.join(map(str,antOrder)))
    if DEBUG:
        print(newStick)

    
    