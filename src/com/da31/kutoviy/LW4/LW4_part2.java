package com.da31.kutoviy.LW4;

import java.io.File;
 
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
 
public class LW4_part2 {
	public static void main(String[] args) throws Exception {
		SimpleKMeans kmeans = new SimpleKMeans();
 
		kmeans.setNumClusters(7);
		String file2="D://temp/bmw-training.arff";
		  
		ArffLoader loader = new ArffLoader();
		loader.setFile(new File(file2));
		Instances data = loader.getDataSet();
		kmeans.buildClusterer(data);
		System.out.println(kmeans.toString());
		int correctclust=0;
		for(int i=0;i<data.size();i++)
		{
			int attribute=3;
			if(data.get(i).value(attribute)==kmeans.getClusterCentroids().get(kmeans.clusterInstance(data.get(i))).value(attribute))
				correctclust++;
		}
		System.out.println(correctclust+"/"+data.size()+":  "+correctclust*100/data.size()+"%");
	}
}