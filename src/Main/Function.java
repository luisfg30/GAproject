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
    
    public double MinX,MaxX,OptX;
    public double MinY,MaxY,OptY;
    public double MinZ,MaxZ,OptZ;
    public boolean minimumProblem;
    
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
        double dist=  Math.sqrt((Math.pow(x-OptX, 2))+((Math.pow(y-OptY, 2)))+((Math.pow(z-OptZ, 2))));
       
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

    public void setMinValues(float x, float y)
    {
        MinX=x;
        MinY=y;
        MinZ=calcValue(MinX,MinY);
//        System.out.println("Min point: ("+MinX+" , "+MinY+" , "+MinZ+")");
    }
    
    public void setMaxValues(float X, float Y)
    {
        MaxX=X;
        MaxY=Y;
        MaxZ=calcValue(MaxX,MaxY);
//        System.out.println("Max point: ("+MaxX+" , "+MaxY+" , "+MaxZ+")");
    }
    
    public void setFunctionNumber(int s)
    {
        fNumber=s;
        if(fNumber==0)//define the values for X and Y at the minimum value
        {
            this.setMinValues(0,0);
            OptX=MinX;
            OptY=MinY;
            OptZ=MaxZ;
            minimumProblem=true;
        }
    }
    
    
    
}
