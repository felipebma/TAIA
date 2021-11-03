from typing import Text
import matplotlib.pyplot as plt
import numpy as np
import csv
import sys
import os


def plotScatter(testName, iterations, plotName="plot"):
    print('scatter')
    for i in range(len(iterations)):
        fig, ax = plt.subplots()
        x_values = iterations[i][0]
        y_values = iterations[i][1]
        ax.scatter(x_values, y_values)
        ax.set_xticks(x_values)
        plt.savefig('{}/scatter/{}{}.png'.format(testName, plotName, i))
        plt.close()


def plotHist2d(testName, iterations, plotName="plot"):
    print('hist2d')
    for i in range(len(iterations)):
        x_values = iterations[i][0]
        max_y = max(iterations[i][1])
        y_values = [y for y in iterations[i][1]]
        plt.hist2d(x_values, y_values, cmap=plt.cm.Reds,
                   bins=(len(set(x_values)), 8))
        plt.colorbar()
        plt.savefig('{}/hist2d/{}{}.png'.format(testName, plotName, i))
        plt.close()


def plotBoxplot(testName, iterations, plotName="plot"):
    print('boxplot')
    for i in range(len(iterations)):

        map = {}
        for j in iterations[i][0]:
            map[j] = []
        for j in range(len(iterations[i][0])):
            map[iterations[i][0][j]].append(iterations[i][1][j])

        x_values = [x for x in map.keys()]
        y_values = []
        for x in x_values:
            y_values.append(np.array(map[x]))

        fig, ax = plt.subplots()

        ax.boxplot(y_values, positions=x_values)
        ax.set_xticks(x_values)
        plt.savefig('{}/boxplot/{}{}.png'.format(testName, plotName, i))
        plt.close()


def plotFit(testName):

    iterations = [[[], []]]

    with open('../data/{}_fit.csv'.format(testName)) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=",")
        it = 0
        count = 0
        for row in csv_reader:
            if row == []:
                it += 1
                count = 0
                iterations.append([[], []])
            else:
                for value in row:
                    iterations[it][0].append(count)
                    iterations[it][1].append(int(value))
                count += 1

    # plotScatter(testName, iterations)
    plotBoxplot(testName, iterations)
    plotHist2d(testName, iterations)


def plotAverageFitnessForAll(testName):
    averageForAll = []

    for generation in range(100):
        averageForAll.append([])
        for individual in range(100):
            averageForAll[generation].append(0)

    with open('../data/{}_fit.csv'.format(testName)) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=",")
        generation = 0

        for row in csv_reader:
            if row == []:
                generation = 0
            else:
                individual = 0
                for value in row:
                    averageForAll[generation][individual] += int(value)
                    individual += 1
                generation += 1

    iterations = [[], []]

    for generation in range(100):
        for individual in range(100):
            fitSum = averageForAll[generation][individual]
            fitAvg = fitSum / 30

            iterations[0].append(generation)
            iterations[1].append(fitAvg)

    plotScatter(testName, [iterations], "averageFitnessForAll")
    plotBoxplot(testName, [iterations], "averageFitnessForAll")
    plotHist2d(testName, [iterations], "averageFitnessForAll")


def plotAverageFitnessPerExecution(testName):
    averagePerExec = [[]]

    with open('../data/{}_fit.csv'.format(testName)) as csv_file:
        execution = 0

        csv_reader = csv.reader(csv_file, delimiter=",")

        for row in csv_reader:
            if row == []:
                execution += 1
                averagePerExec.append([])
            else:
                fitSum = 0
                for value in row:
                    fitSum += int(value)
                averagePerExec[execution].append(fitSum/100)

    iterations = [[], []]
    execution = 0

    for averagePerGen in averagePerExec:
        for genAverage in averagePerGen:
            iterations[0].append(execution)
            iterations[1].append(genAverage)
        execution += 1

    plotScatter(testName, [iterations], "averageFitnessPerExecution")
    plotBoxplot(testName, [iterations], "averageFitnessPerExecution")
    plotHist2d(testName, [iterations], "averageFitnessPerExecution")


path = sys.argv[1]
testName = sys.argv[2]
if not os.path.exists('{}/charts/{}/boxplot'.format(path, testName)):
    os.makedirs("{}/charts/{}/boxplot".format(path, testName))
if not os.path.exists('{}/charts/{}/scatter'.format(path, testName)):
    os.makedirs("{}/charts/{}/scatter".format(path, testName))
if not os.path.exists('{}/charts/{}/hist2d'.format(path, testName)):
    os.makedirs("{}/charts/{}/hist2d".format(path, testName))

print(testName)
plotFit(testName)
plotAverageFitnessForAll(testName)
plotAverageFitnessPerExecution(testName)
