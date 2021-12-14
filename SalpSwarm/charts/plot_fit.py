from typing import Text
import matplotlib.pyplot as plt
import numpy as np
import csv
import sys
import os


def plotHist2d(testName, iterations, plotName="plot"):
    print('hist2d')

    x_values = []
    y_values = []
    for it in range(len(iterations)):
        for value in iterations[it]:
            x_values.append(it)
            y_values.append(value)
    plt.title("Leader Salp Accuracy Distribution per Iteration")
    plt.xlabel("Iteration")
    plt.ylabel("Accuracy")

    plt.hist2d(x_values, y_values, cmap=plt.cm.Blues,
               bins=(len(set(x_values)), 20))
    plt.colorbar()
    plt.savefig('{}/hist2d/{}{}.png'.format(testName, plotName, 0))
    plt.close()


def plotLine(testName, iterations, plotName="plot"):
    print('line')

    x_values = []
    y_values = []
    best_values = []
    worst_values = []
    for it in range(len(iterations)):
        x_values.append(it)
        y_values.append(sum(iterations[it])/len(iterations[it]))
        best_values.append(min(iterations[it]))
        worst_values.append(max(iterations[it]))
    plt.title("Leader Salp Average Accuracy per Iteration")
    plt.xlabel("Iteration")
    plt.ylabel("Accuracy")

    plt.plot(x_values, y_values)
    # plt.plot(x_values, best_values)
    # plt.plot(x_values, worst_values)
    plt.savefig('{}/line/{}{}.png'.format(testName, plotName, 0))
    plt.close()


def plotAverageFitnessForAll(testName):
    iterations = []

    with open('../data/{}_fit.csv'.format(testName)) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=",")
        for row in csv_reader:
            iterations.append([])
            for value in row:
                iterations[len(iterations) - 1].append(float(value))

    plotHist2d(testName, iterations, "averageFitnessForAll")
    plotLine(testName, iterations, "averageFitnessForAll")


path = sys.argv[1]
testName = sys.argv[2]
if not os.path.exists('{}/charts/{}/hist2d'.format(path, testName)):
    os.makedirs("{}/charts/{}/hist2d".format(path, testName))
if not os.path.exists('{}/charts/{}/line'.format(path, testName)):
    os.makedirs("{}/charts/{}/line".format(path, testName))

print(testName)
plotAverageFitnessForAll(testName)
