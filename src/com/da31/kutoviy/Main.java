package com.da31.kutoviy;

import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.classifiers.functions.LinearRegression;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            String file2 = "D://temp/dermatology.arff";
            ArffLoader loader = new ArffLoader();
            loader.setFile(new File(file2));
            Instances structure = loader.getDataSet();
            structure.setClassIndex(structure.numAttributes() - 2);
            LinearRegression lr = new LinearRegression();
            lr.buildClassifier(structure);
            double[] coef = lr.coefficients();
            for (int i = 0; i < coef.length; i++) {
                String coefName;
                if (i == coef.length - 1)
                    coefName = "Free coef";
                else {
                    coefName = structure.attribute(i).name();
                }
                if (coef[i] != 0)
                    System.out.println(String.format("%48s", coefName) + ": " + String.format("%10.4f", coef[i]));
            }
            double averageerror = 0;
            int errorcount = 0;
            for (int i = 0; i < structure.size(); i++) {
                double currentvalue = 0;
                for (int j = 0; j < structure.numAttributes(); j++) {
                    currentvalue += coef[j] * structure.get(i).value(j);
                }
                currentvalue += coef[coef.length - 1];
                double currentage = structure.get(i).value(structure.numAttributes() - 2);
                if (currentage == 0)
                    currentage++;
                double currenterror = Math.abs(currentage - currentvalue) / currentage;
                if (!Double.isNaN(currenterror)) {
                    averageerror += currenterror;
                    errorcount++;
                }
            }
            averageerror /= errorcount;
            System.out.println(averageerror * 100 + "%");
            System.out.println(structure.numAttributes() + " Attributes");
            System.out.println(structure.size() + " Instances");
        }catch (Exception exp){
            throw new RuntimeException();
        }
    }
}