package Main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luis
 */
public class Function {
    
    //SINGLETON PATTERN
    private static Function singleton=null;
    
    private double MinX;
    private double MinY;
    private double MinZ;
    
    private int fNumber=0; //chose one of the test funciotns from a list. 
    
    private Function()
    {
        
    }
        
        public static Function getInstance()
    {
        if(singleton==null)
        {
            singleton = new Function();
        }
        return singleton;
    }
    
    public double calcFitness(double x,double y)
    {
        double z=calcValue(x,y);
        double dist=  Math.sqrt((Math.pow(x-MinX, 2))+((Math.pow(y-MinY, 2)))+((Math.pow(z-MinZ, 2))));
        if(dist==0)
        {
            // SET TO MAXIMUM FITNESS --> PERFECT RESULT (BEST INDIVIDUAL)
        }
        //ELSE IF  DIST > VARIABLES RANGE --> SET TO WORST FITNESS
        
        double fitness=100/(1+dist) ;//when dist--> infinity fitness-->0. When dist-->0  fitness-->1
        //System.out.println("F("+x+" , "+y+")= "+z);
       // System.out.println("Dist("+x+" , "+y+" , "+z+")= "+dist);
        return fitness;
    }
    
    //CHOOSE THE SPECIFIC EXPRESSIONS FOR EACH FUNCTION
    public double calcValue(double x, double y)
    {
        double z=999;
        //switch usin fNumber case to calculate Z
        switch(fNumber)
        {
            //Ackley`s Function
            case 0:
                //SET MINX AND MINY
                double a,b;
                a=(-0.2)*(Math.sqrt(0.5*(Math.pow(x, 2)+Math.pow(y, 2))));
                b=(0.5)*(Math.cos(2*(Math.PI*x))+Math.cos(2*(Math.PI*y)));
                z=(-20)*Math.exp(a)-Math.exp(b)+20+Math.E; 
                break;
            
            case 1:
                //OHTER FUNCTIONS
            break;
                
        }
        return z;
    }
    
    public void setParameters(int x,int y,int fn)
    {
        MinX=x;
        MinY=y;
        MinZ=calcValue(MinX,MinY);
        //System.out.println("Min point: ("+MinX+" , "+MinY+" , "+MinZ+")");
        fNumber=fn;
    }

    public void setFunctionNumber(int s){fNumber=s;}
    
}
