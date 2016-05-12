package com.da31.kutoviy.utils;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class WekaUtils {

    public Instances structure;
    public List<Double> coefficients;

    private WekaUtils(String FILE_PATH) throws Exception {
        ArffLoader arffLoader = new ArffLoader();
        arffLoader.setFile(new File(FILE_PATH));
        structure = arffLoader.getDataSet();
        structure.setClassIndex(structure.numAttributes() - 2);
        weka.classifiers.functions.LinearRegression linearRegression = new weka.classifiers.functions.LinearRegression();
        linearRegression.buildClassifier(structure);
        coefficients = getCoefficients(linearRegression.coefficients());
    }

    public static WekaUtils setUtils(String FILE_PATH) {
        try {
            return new WekaUtils(FILE_PATH);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Double> getCoefficients(double[] list) {
        List<Double> newList = new LinkedList<>();
        for (double item : list) {
            newList.add(item);
        }
        return newList;
    }

    public static void performModeling(Double averageError, Integer errorCount, WekaUtils wekaUtils, Instance instance) {
        double currentvalue = 0;
        for (int j = 0; j < wekaUtils.structure.numAttributes(); j++) {
            currentvalue += wekaUtils.coefficients.get(j) * instance.value(j);
        }
        currentvalue += wekaUtils.coefficients.get(wekaUtils.coefficients.size() - 1);
        double currentage = instance.value(wekaUtils.structure.numAttributes() - 2);
        if (currentage == 0)
            currentage++;
        double currenterror = Math.abs(currentage - currentvalue) / currentage;
        if (!Double.isNaN(currenterror)) {
            averageError += currenterror;
            errorCount++;
        }
    }

    public static void setCoefficientNames(WekaUtils wekaUtils, Double currentCoef) {
        String coefName;
        int i = wekaUtils.coefficients.indexOf(currentCoef);
        if (wekaUtils.coefficients.size() - 1 == i)
            coefName = "Free coefficients";
        else {
            coefName = wekaUtils.structure.attribute(i).name();
        }
        if (currentCoef != 0)
            System.out.println(String.format("%48s", coefName) + ": " + String.format("%10.4f", currentCoef));
    }
}
