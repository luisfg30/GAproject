package Main;

import java.util.ArrayList;

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
    
    private Function myEvaluator= Function.getInstance();

    // EACH STEP VALUES
     
     private int totalGenerations=0,FconvergenceCounter=0,avgConvergenceCounter=0,eliteConvergenceCounter=0;
     boolean FconvSet=false,avgConvset=false,eliteConvset=false;
     private double FconvergenceValue=75;//elite individual
     private ArrayList<Double> fitnessValues = new ArrayList();
     private ArrayList<Double> bestFitnessValues= new ArrayList();
    
    //WHOLE PROCESS VALUES
    private int executionCounter=0;
    private ArrayList<Solution> solutions = new ArrayList();
    private ArrayList<Double> avgOptimality,eliteOptimality;
     
    private Stats()
    {
        avgOptimality=new ArrayList();
        eliteOptimality=new ArrayList();
    }
    
    public static Stats getInstance()
    {
        if(singleton==null)
        {
            singleton = new Stats();
        }
        return singleton;
    }
    
//    public void addZValue(double v)
//    {
//        zValues.add(v);
//    }
    
    public void setConvergenceValue(double v)
    {
        FconvergenceValue=v;
    }
    
    public void updateConvergenceCounter(double value)
    {
        if(value<FconvergenceValue)
        {
            if(FconvSet==false)
                FconvergenceCounter++;
        }
        else
        {
            FconvSet=true;
        }
            
    }
    
    public void updateFitnessValues(double fitness, double bestFitness)
    {
        fitnessValues.add(fitness);
        bestFitnessValues.add(bestFitness);
        updateConvergenceCounter(bestFitness);
        totalGenerations++;
        System.out.println("\n GENERATION: "+totalGenerations);
    }
    
    public void updateEliteOptimality(double opt)
    {System.out.println("update elite");
        eliteOptimality.add(opt*100);
        if(opt*100<FconvergenceValue)
        {
            if(this.eliteConvset==false)
                eliteConvergenceCounter++;
        }
        else
        {
            eliteConvset=true;
        }
    }
    
    public void updateAvgValues(double opt)
    {System.out.println("update avg");
        avgOptimality.add(opt*100);
        if(opt*100<FconvergenceValue)
        {
            if(this.avgConvset==false)
                avgConvergenceCounter++;
        } 
        else
        {
            avgConvset=true;
        }
    }
    
    public void calcAvgValues()
    {

    }
            
    
    public void recordResults(double x, double y, double z, double fit)
    {
        Solution s= new Solution();
        s.x=x;
        s.y=y;
        s.z=z;
        s.optimality=calcOptimality(myEvaluator.OptZ, myEvaluator.MaxZ, myEvaluator.MinZ,s.z);
        s.calcAccuracy(myEvaluator.OptX, myEvaluator.MinX, myEvaluator.MaxX, myEvaluator.OptY, myEvaluator.MinY, myEvaluator.MaxY);
        s.setSensivity();
        s.convValue=this.FconvergenceValue;
        s.convCounter=this.FconvergenceCounter;
        s.totalGen=this.totalGenerations;
        s.fitness=fit;
        solutions.add(s);
              executionCounter++;
        
        //debug
              System.out.println("\nEXECUTION: "+this.executionCounter+"\n");
        for(int i=0;i < solutions.size();i++)
        {
            System.out.println("["+i+"] TotalGens: "+solutions.get(i).totalGen+
                    "\nValues ("+solutions.get(i).x+" , "+solutions.get(i).y+" , "+solutions.get(i).z+
                    ")\n Fitness: "+solutions.get(i).fitness+
                    "\nOptimality: "+solutions.get(i).optimality+" Accuracy: "+solutions.get(i).accuracy+
                    " Sensivity: "+solutions.get(i).sensivity+
                    "\n Elite FitnessConvergence("+solutions.get(i).convValue+"): "+solutions.get(i).convCounter+
                    "\n Elite Convergence("+this.FconvergenceValue+"): "+eliteConvergenceCounter+
                    "\n Avg Convergence("+this.FconvergenceValue+"): "+this.avgConvergenceCounter);
        }
        System.out.println("elite opt:"+this.eliteOptimality.size()+" avg opt: "+this.avgOptimality.size());
    }
    
    public void clearData(){
        
        fitnessValues.clear();
        bestFitnessValues.clear();
        avgOptimality.clear();
        eliteOptimality.clear();
        totalGenerations=0;
        FconvergenceCounter=0;
        avgConvergenceCounter=0;
        eliteConvergenceCounter=0;
    }
    public int getGenerations(){return totalGenerations;}
    public int getFConvergenceCounter(){return FconvergenceCounter;}
    public int getExecutionCounter(){return executionCounter;}
    public double getFConvergenceValue(){return FconvergenceValue;}
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
    
    public double[]getArrayAvgOptimality()
    {
        double array[]= new double[avgOptimality.size()];
        for(int i=0;i<avgOptimality.size();i++)
        {
            array[i]=avgOptimality.get(i);
        }
        return array;
    }
    
        public double[]getArrayEliteOptimality()
    {
        double array[]= new double[eliteOptimality.size()];
        for(int i=0;i<eliteOptimality.size();i++)
        {
            array[i]=eliteOptimality.get(i);
        }
        return array;
    }
    
    
                public  double calcOptimality(double zOpt,double zMax,double zMin, double z)
            {
                double optimality;
                if(this.myEvaluator.minimumProblem==true)
                {
                    optimality= 1-(z-zMin)/(zMax-zMin);
                }
                else
                {
                    optimality= 1-(zMax-z)/(zMax-zMin);
                }
                return optimality;
            }
        
   private class Solution{
        public int totalGen=0,convCounter=0;
        public double x,y,z,fitness;
        public double optimality,accuracy,convValue,sensivity;       
 
        
            public void calcAccuracy(double xOpt,double xMin,double xMax, double yOpt, double yMin, double yMax)
            {
                accuracy= 1- (Math.sqrt((Math.pow(xOpt-x, 2))+(Math.pow(yOpt-y, 2))))/
                        (Math.sqrt((Math.pow(xMax-xMin, 2))+(Math.pow(yMax-yMin, 2))));
            }
            
            public void setSensivity()
            {
                sensivity=(1-optimality)/(1-accuracy);
            }
    
   }
}

