from typing import Text
import matplotlib.pyplot as plt
import numpy as np
import csv


def plotScatter(testName, iterations):
    for i in range(len(iterations)):
        fig, ax = plt.subplots()
        x_values = iterations[i][0]
        y_values = iterations[i][1]
        ax.scatter(x_values, y_values)
        ax.set_xticks(x_values)
        plt.savefig('{}/scatter/plot{}.png'.format(testName, i))
        plt.close()


def plotBoxplot(testName, iterations):
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
        plt.savefig('{}/boxplot/plot{}.png'.format(testName, i))
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

    plotScatter(testName, iterations)
    plotBoxplot(testName, iterations)


plotFit('default')
plotFit('alternativeFitness')
