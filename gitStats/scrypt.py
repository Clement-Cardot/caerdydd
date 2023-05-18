import csv
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

weekData = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}]

for i in range(1, 15):
    with open('gitStat{}.csv'.format(i), 'r') as f:
        reader = csv.reader(f)
        for row in reader:
            if weekData[i-1].keys().__contains__(row[0]):
                weekData[i-1][row[0]][0] += int(row[1])
                weekData[i-1][row[0]][1] -= int(row[2])
            else:
                weekData[i-1][row[0]] = [int(row[1]), int(row[2])]

for week in weekData:

    if week.keys().__contains__('clementcdt25'):
        if week.keys().__contains__('clement.cardot'):
            week['clement.cardot'][0] += week['clementcdt25'][0]
            week['clement.cardot'][1] += week['clementcdt25'][1]
            week.pop('clementcdt25')
        else:
            week['clement.cardot'] = week['clementcdt25']
            week.pop('clementcdt25')

    if week.keys().__contains__('m49.aumont'):
        if week.keys().__contains__('maxime.aumont'):
            week['maxime.aumont'][0] += week['m49.aumont'][0]
            week['maxime.aumont'][1] += week['m49.aumont'][1]
            week.pop('m49.aumont')
        else:
            week['maxime.aumont'] = week['m49.aumont']
            week.pop('m49.aumont')

    if week.keys().__contains__('120374499+TheoSaindrenan'):
        week['theo.saindrenan'] = week['120374499+TheoSaindrenan']
        week.pop('120374499+TheoSaindrenan')

    if week.keys().__contains__('106916654+mariebdt'):
        week['marie.bordet'] = week['106916654+mariebdt']
        week.pop('106916654+mariebdt')


    if week.keys().__contains__('richard.woodward'):
        week.pop('richard.woodward')   

while weekData[0].items().__len__() == 0:
    weekData.append(weekData.pop(0))   

print(weekData)

users = ['clement.cardot', 'nicolas.moerman', 'augustin.baffou', 'maxime.kermagoret', 'alexis.bonamy', 'maxime.aumont', 'theo.saindrenan', 'marie.bordet']


additionData = [[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0]]

deletionData = [[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0]]

for i in range(0, 14):
    for key in users:
        index = users.index(key)
        if weekData[i].keys().__contains__(key):
            for j in range(i, 15):
                additionData[j][index] += weekData[i][key][0]

for i in range(0, 14):
    for key in users:
        index = users.index(key)
        if weekData[i].keys().__contains__(key):
            for j in range(i, 15):
                deletionData[j][index] += weekData[i][key][1]


df_add = pd.DataFrame(additionData, columns=users)
df_add.plot.area()
plt.title('Addition of lines of code per week')
plt.show()

df_suppr = pd.DataFrame(deletionData, columns=users)
df_suppr.plot.area()
plt.title('Deletion of lines of code per week')
plt.show()


