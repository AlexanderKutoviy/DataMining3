package com.da31.kutoviy.LW4;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.classifiers.trees.REPTree;

import java.io.File;

public class LW4 {
  public static void main(String[] args) throws Exception {
	String file1="D://temp/bmw-training.arff";
	String file2="D://temp/bmw-test.arff";
	  
	ArffLoader loader = new ArffLoader();
	loader.setFile(new File(file1));
	Instances structure = loader.getDataSet();
	structure.setClassIndex(structure.numAttributes() - 1);
	
	REPTree lr=new REPTree();
	lr.setMaxDepth(3);
	lr.buildClassifier(structure);
	System.out.println(lr);
//	System.out.println(lr.getCapabilities());
//	double[] mv=lr.distributionForInstance(structure.get(10));
//	double[] mv=lr.getMembershipValues(structure.get(20));
//	for(int i=0;i<mv.length;i++)
//		System.out.print(mv[i]+", ");
//	System.out.println("");
//	System.out.println(lr.getRevision());


	loader.setFile(new File(file2));
	Instances structure1 = loader.getDataSet();
	structure1.setClassIndex(structure.numAttributes() - 1);
	int correctclass=0;
	for(int i=0;i<structure1.size();i++)
	{
		Instance current=structure1.get(i);
		double currentclass=lr.classifyInstance(current);
		if(currentclass==current.value(3))
			correctclass++;
	}
	System.out.println(correctclass+"/"+structure1.size()+":  "+correctclass*100/structure1.size()+"%");
  }
}
