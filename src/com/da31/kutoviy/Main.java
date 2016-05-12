package com.da31.kutoviy;

import com.da31.kutoviy.utils.WekaUtils;

public class Main {

    public static void main(String[] args) {
        //preparations
        String FILE_PATH = "D://temp/dermatology.arff";
        WekaUtils wekaUtils = WekaUtils.setUtils(FILE_PATH);
        final Double averageError = new Double(0);
        Integer errorCount = new Integer(0);
        //body
        wekaUtils.coefficients.stream().forEach(currentCoef -> WekaUtils.setCoefficientNames(wekaUtils, currentCoef));
        wekaUtils.structure.stream().forEach(instance -> WekaUtils.performModeling(averageError, errorCount, wekaUtils, instance));
        //out
        double error = averageError / errorCount;
        System.out.println(error * 100 + "%");
        System.out.println(wekaUtils.structure.numAttributes() + " Attributes");
        System.out.println(wekaUtils.structure.size() + " Instances");
    }
}