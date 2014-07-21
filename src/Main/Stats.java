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
public class Stats {
    //SINGLETON PATTERN
    private static Stats singleton=null;
    
    private GA myGA= GA.getInstance();
    // EACH STEP VALUES
     private double totalFitness, avgFitness;
    
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
    
    public double calcAvgFitness(Population population)
    {
        double avg=0,sum=0;
        int max= population.getPopulation().length;
        for(int i=0;i<max;i++)
        {
            sum+=population.getIndividual(i).getFitness();
        }
        avg=sum/max;
        totalFitness=sum;
        avgFitness=avg;
        return avg;
    }
    
    public double getTotalFitness(){return totalFitness;}
}
