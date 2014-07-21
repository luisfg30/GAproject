/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Main;

import java.util.Random;

/**
 *
 * @author luis
 */
public class Population {
    
    private Individual [] individuals;
    
    private GA myGA= GA.getInstance();
    
    public Population(int size)
    {
        individuals= new Individual[size];
    }
    /**
     * Fill the population with random individuals
     */
    public void startIndividuals()
    {
        int size=individuals.length;
       
        for (int i=0;i<size;i++)
        {
           Individual aux = new Individual(myGA.getGeneLength());
           aux.setId(i+randomString("abcedfghijklmnopqrstuvwxyz",3));
           individuals[i]= aux;
           //System.out.println(population[i].getGenesString()+"----"+i);
        }
        
    }
    
    public String printInfo(int fit)
    {
        String s = "";
        for(int i=0;i<myGA.getPopSize();i++)
        {
            if(fit==1)
            {
                            s=s.concat("["+individuals[i].getId()+"] "+individuals[i].getGenesString()+" Fitness: "
                    +individuals[i].getFitness()+"\n");
            }
            else
            {
                            s=s.concat("["+individuals[i].getId()+"] "+individuals[i].getGenesString()+"\n");
            }

        }
        return s;
    }
    
    public Individual getFittest()
    {
        int size=individuals.length;
        Individual fittest= new Individual(0);
        for(int i=0;i<size;i++)
        {
            if(individuals[i].getFitness()>fittest.getFitness())
                fittest= new Individual(individuals[i]);
        }
        return fittest;
    }
    /**
     * Returns a random string made from alphabet, with len characters.
     * Ex:s=randomstring("abc",5);--> s= cbaacb.
     * Ex2: s=randomString("01",10);-->s= 0001010011
     * @param alphabet
     * @param len
     * @return 
     */
     public static String randomString(String alphabet,int len)
    {
         Random rnd = new Random();

           StringBuilder sb = new StringBuilder( len );
           for( int i = 0; i < len; i++ ) 
              sb.append( alphabet.charAt( rnd.nextInt(alphabet.length())));
           String s= sb.toString();
           return s;
    }
    
    public void setIndividual(Individual ind,int index){individuals[index]=ind;}
  
    public Individual[] getPopulation(){return individuals;}
    public Individual getIndividual(int index){return individuals[index];}
}
