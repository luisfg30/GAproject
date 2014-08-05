package Main;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luis
 */
public class Stats {
    //SINGLETON PATTERN
    private static Stats singleton=null;
    
//    private GA myGA= GA.getInstance();
    // EACH STEP VALUES
     
     private int totalGenerations=0;
     private ArrayList<Double> fitnessValues = new ArrayList();
     private ArrayList<Double> bestFitnessValues= new ArrayList();
     private ArrayList<Double> zValues = new ArrayList();
    
    //WHOLE PROCESS VALUES
    
    private Stats()
    {
        
    }
    
    public static Stats getInstance()
    {
        if(singleton==null)
        {
            singleton = new Stats();
        }
        return singleton;
    }
    
    public void addZValue(double v)
    {
        zValues.add(v);
    }
    
    public void updateFitnessValues(double fitness, double bestFitness)
    {
        fitnessValues.add(fitness);
        bestFitnessValues.add(bestFitness);
        totalGenerations++;
    }
    public void clearData(){
        fitnessValues.clear();
        bestFitnessValues.clear();
        zValues.clear();
        totalGenerations=0;
    }
    public void setGenerations(int g){totalGenerations=g;}
    public int getGenerations(){return totalGenerations;}
    public double[] getArrayFitness()
    {
        double array[]= new double[fitnessValues.size()];
        for(int i=0;i<fitnessValues.size();i++)
        {
            array[i]=fitnessValues.get(i);
        }
        return array;
    }
    public double[]getArrayBestFitness()
    {
        double array[]= new double[bestFitnessValues.size()];
        for(int i=0;i<bestFitnessValues.size();i++)
        {
            array[i]=bestFitnessValues.get(i);
        }
        return array;
    }
    
    public double[]getArrayZValues()
    {
        double array[]= new double[zValues.size()];
        for(int i=0;i<zValues.size();i++)
        {
            array[i]=zValues.get(i);
        }
        return array;
    }
 
}
