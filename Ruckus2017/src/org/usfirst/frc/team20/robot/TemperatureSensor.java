//Author: Saathvik Narra
package org.usfirst.frc.team20.robot;

import java.util.ArrayList;

public class TemperatureSensor { 
	ArrayList<Double> Temperature = new ArrayList<Double>();


	public void ParseTemperature(String str){
		String[] Data = str.split("\\*");
		//ArrayList<Double>Temperature = new ArrayList<Double>();
		Temperature.clear();
		int all = 1;
	
		while (all < Data.length){
			Temperature.add(Double.parseDouble(Data[all]));
			all = all + 1;
		}
	}
	public Double getTemperature(int index){
		return (Double) Temperature.toArray()[index];		
	}
	public int getLength(){
		return Temperature.size();
	}
}

