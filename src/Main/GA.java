package Main;


import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luis
 */
public class GA {
    
    //SINGLETON PATTERN
    private static GA singleton=null;
    
    //ARBRITARY VALUES
    public final int maxLength= 50; // length of the individual`s genes
    private final int maxVars= 2; //number of variables
    
    
    //USER DEFINED VALUES
    private int geneLength;
    private int popSize=10;
    private int mutationRate=10,operatorRate=50, tournamentSize=5;
    private int geneticOperator=1; //  0= Lamarck Inheritance, 1 = Crossover
    private int selectionMethod =1; // 0= tournament, 1= roullete wheel
    private boolean useMutation=true;
    private boolean useElite=true;
 
    
    private Variable[] vars;
    private Variable x,y;
    public Individual eliteIndividual;
    private Function evaluatorFunction= Function.getInstance();
    private Random rand;
    
    private GA()
    {
         rand= new Random();
         x= new Variable(-3, (float) 12.1,4);
         y= new Variable((float)4.1, (float) 5.8,4);
         vars = new Variable[maxVars];
         vars[0]=x;
         vars[1]=y;
         upDateVariables();    
         eliteIndividual= new Individual(0);
    }
    
    public static GA getInstance()
    {
        if(singleton==null)
        {
            singleton = new GA();
        }
        return singleton;
    }
    
    public void upDateVariables()
    {
        geneLength=x.getBitSize()+y.getBitSize();
        
//        System.out.println("Size of string x in bits= "+x.getBitSize());
//        System.out.println("Size of string y in bits= "+y.getBitSize());
//        System.out.println("Size of gene string= "+geneLength);
    }
   

    
    public Population evolve(Population pop)
    {     
        
//             System.out.println("ELITE: "+eliteIndividual.getId()+" "+eliteIndividual.getGenesString()+ " fit: "+eliteIndividual.getFitness());
             switch(geneticOperator)
             {
                 case 0:
                     //Lamarck
                     System.out.println("Lamarck");
                     pop=overwriteOperation(pop);
                 break;

                 case 1:
                     //Classic GA
                     System.out.print("Classic GA ");
                     switch(selectionMethod)
                     {
                         case 0:
                              //tournament
                             System.out.println("Tournament");
                         break;

                         case 1:
                             // roullete wheel
                             System.out.println("Roullete");
                         break;    
                     }
                 break;    
             }

             if(useMutation==true)
             {
                    System.out.println("Mutation");
                    this.mutation(pop);
             }
             else
             {
                  System.out.println("No mutation");
             } 
        //return a population
         evaluate(pop);
        return pop;
    }
   

    
    public void evaluate(Population population)
    {
        System.out.println("Evaluation");
        this.upDateVariables();
        for(int i=0;i<popSize;i++)
        {
            String sX,sY;
            double x,y;
            
            sX=population.getIndividual(i).getGenesString().substring(0, vars[0].getBitSize());
            sY=population.getIndividual(i).getGenesString().substring(vars[0].getBitSize());
            x= vars[0].calcRealValue(sX);
            y= vars[0].calcRealValue(sY);
            
            population.getIndividual(i).setFitness(evaluatorFunction.calcFitness(x, y));
        }
        if(population.getFittest().getFitness()>eliteIndividual.getFitness())
        {
            eliteIndividual=new Individual(population.getFittest());
        }
    }
    /**
     * Randomly pick a set of "tournamentSize" individuals from population,  no repeat. 
     * @param population population to realize the tournament
     * @return get the best individual from the set 
     */
    public Individual tournamentSelection(Population population)
    {
        //get a set of individuals from the population, no repeat
        Population auxPop= new Population(tournamentSize);
        Integer indexes[]= randomNoRepeat(population.getPopulation().length);
        for(int i=0;i<tournamentSize;i++)
        {  
            auxPop.setIndividual(population.getIndividual(indexes[i]), i);
        }
        //choose the best from the auxPop
        return auxPop.getFittest();
    }
    /**
     * Return an array of size elements in a random order with no repeating elements
     * @param size define the array size and the maximum value (size-1).<br>
     * Ex: size =5 , array=[0,4,1,2,3]
     * @return 
     */
    public Integer[] randomNoRepeat(int size)
    {
        Integer array[]= new Integer[size];
        for(int j=0;j<size;j++)
        {
            array[j]=j;
        }
        Collections.shuffle(Arrays.asList(array));
       // System.out.println(Arrays.toString(array));
        return array;
    }
    /**
     * Return a random integer number within the range [min,max]<p>
     * Ex: randomRange(1,7). Possible answers: {1,2,3,4,5,6,7}
     * @param min
     * @param max
     * @return 
     */
    public int randomRange(int min,int max)
    {
        int number= rand.nextInt((max - min) + 1) + min;
        return number;
    }
    
    /**
     * Randomly select up to mutationRate% of population for mutation.<p>
     * Ex: popSize =100, mutatioRate=10; <br>
     * 10 random individuals out of 100 will be mutated
     * @param population 
     */
    public void mutation(Population population)
    {
         //get a random individual from the original population, no repeat
        Integer indexes[]= randomNoRepeat(popSize);
        int max=mutationRate*popSize/100;
         for(int i=0;i<max;i++)
        {
            this.mutate(population.getIndividual(indexes[i]));
            population.getIndividual(indexes[i]).setId("M"+Population.randomString("abcedfghijklmnopqrstuvwxyz", 3));
        }
    }
    /**
     * Mutates up to 10% of and Individual genetic Code.Choose bits at random positions
     * from the genetic code and flip it.<p>
     * Ex: a=[1111100000],flip 1 random bit (bit 3);
     *    a`=[1110100000]
     * @param a 
     */
    private void mutate(Individual a)
    {
      // System.out.println(a.getId()+" "+a.getGenesString()+" aaa");
        int max =a.getGenes().length/10;
        if(a.getGenes().length<=10)
        {
            max=1;
        }
        else
        {
            max=rand.nextInt(max)+1;
        }
//        System.out.println("geneLen: "+a.getGenes().length+" 10% "+a.getGenes().length/10+" max: "+max);
        Integer auxGens[]= randomNoRepeat(a.getGenes().length);
        byte one=1;
        byte zero=0;
        for(int i=0;i<max;i++)
        {
            if(a.getGene(auxGens[i])==0)
            {
                a.setGene(auxGens[i],one);
            }
            else
            {
                a.setGene(auxGens[i], zero);
            }
        }
//        System.out.println("selected bits: "+Arrays.toString(auxGens));
//        System.out.println(a.getId()+" "+a.getGenesString()+" a mutated");
    }
    /**
     * Perform the overwrite function in a given population. The resulting population "newPop" will have 
     * GA.operatorRate % of new individuals and the complement from the provided population.<p>
     * Ex: Ga.popSize=10, Ga.operatorRate=50;<br>
     * The new population will have 5 new individuals (resulting from overwrite function) and 5 from the old population
     * @param population
     * @return newPop
     */
    public Population overwriteOperation(Population population)
    {
//        System.out.print("\nORIGINAL POP\n"+population.printInfo(1));
        
        Integer aux[]=randomNoRepeat(popSize);
        
        int max=operatorRate*popSize/100;
        Individual selected[]= new Individual[max];
        int selectedIndexes[]= new int[max];
//        System.out.println("aux: "+Arrays.toString(aux));
        for(int i=0;i<max;i++)
        {
            selectedIndexes[i]=aux[i];
            int n = rand.nextInt(popSize);
//            System.out.println("\tIndex: "+selectedIndexes[i]+"("+population.getIndividual(selectedIndexes[i]).getId()
//            +") n: "+n+"("+population.getIndividual(n).getId()+")");
            while(n==selectedIndexes[i])//avoid overwrite with itself
            {
//                System.out.println("repeated");
                n=aux[popSize-1-i];
//                System.out.println("\tIndex: "+selectedIndexes[i]+"("+population.getIndividual(selectedIndexes[i]).getId()
//                        +") n: "+n+"("+population.getIndividual(n).getId()+")");
            }
            Individual a= new Individual(population.getIndividual(selectedIndexes[i]));
            Individual b= new Individual(population.getIndividual(n));
                   
            selected[i]=overwrite(a,b);
        }
     
//        System.out.println("\n Winers from overwrite:");
//        for(int i=0; i< selected.length;i++)
//        {
//            System.out.println("\n["+selected[i].getId()+"] "+selected[i].getGenesString()+"Fitness: UNKNOW");
//        }
               
        Population newPop= new Population(popSize);
//        System.out.println("aux: "+Arrays.toString(aux));
                
            
        for(int i=0;i<popSize;i++)
        {
            if(i<max)
            {
                newPop.setIndividual(selected[i], i);
            }
            else
            {
                newPop.setIndividual(population.getIndividual(aux[i]), i);
            }
//            System.out.println("\n "+aux[i]+" ["+newPop.getIndividual(i).getId()+"] "+newPop.getIndividual(i).getGenesString()+"Fitness: "+newPop.getIndividual(i).getFitness());
        }
        if(useElite==true)
        {
            int index= randomRange(max,popSize-1);
            Individual ind=new Individual(eliteIndividual);
            newPop.setIndividual(ind, index);
        }
//        System.out.println("ELITE AFTER OVER "+eliteIndividual.getId()+" "+eliteIndividual.getGenesString()+ " fit: "+eliteIndividual.getFitness());
//         System.out.print("\nNEW POP\n"+newPop.printInfo(1));
        
         return newPop;
    }
    /**
     * Lamarckian Inheritance operation, the individual with higher fitness receives a percentage of the genes
     * from the weaker one. The percentage is calculated based on the proportion between both fitness.<br>
     * Percentage = smallFitness/(smallFitness+bigFitness).<p>. The genes indexes are random
     * Ex: a.genes={000111}, a.fitness=50 and b.genes={111111}, b.fitness=100<br>
     * percentage=50/(50+100)=0.333 , so b will receive one third =2 genes from a, at random positions.<br>
     * If the random indexes are 0 and 2 : b`.genes={010111}.
     * 
     * @param a
     * @param b
     * @return 
     */
    private Individual overwrite(Individual a,Individual b)
    { 
        int max =a.getGenes().length;
        Integer auxGens[]= randomNoRepeat(max);
        int selectedIndex[]; 
        
        Individual small =new Individual(geneLength),big= new Individual(geneLength);
        if(a.getFitness()>b.getFitness())
        {
            big.setGeneString(a.getGenes());
            big.setFitness(a.getFitness());
            small.setGeneString(b.getGenes());
            small.setFitness(b.getFitness());
        }
        else
        {
            big.setGeneString(b.getGenes());
            big.setFitness(b.getFitness());
            small.setGeneString(a.getGenes());
            small.setFitness(a.getFitness());
        }
        
        int number=(int) (max*small.getFitness()/(small.getFitness()+big.getFitness()));

        
//        System.out.println("\n\t\t"+small.getGenesString()+" small: ["+small.getId()+"]"+" fitness: "+small.getFitness());
//        System.out.println("\t\t"+big.getGenesString()+" big: ["+big.getId()+"] "+" fitness: "+big.getFitness());
        
        selectedIndex= new int[number];
        Individual result= new Individual(geneLength);
        byte gen[] = new byte [geneLength];
        System.arraycopy(big.getGenes(), 0, gen, 0, geneLength);
        result.setGeneString(gen);
        int equalCounter[]= new int[number];
        for(int i=0;i<number;i++)
        {
            selectedIndex[i]=auxGens[i];
            if(big.getGene(selectedIndex[i])==small.getGene(selectedIndex[i]))
            {
                equalCounter[i]=1;
            }
            else
            {
                equalCounter[i]=0;
            }

                result.setGene(selectedIndex[i],small.getGene(selectedIndex[i]));
        }      
        result.setId(Population.randomString("abcedfghijklmnopqrstuvwxyz",3)+"BIG");
//        System.out.println("\t\t"+Arrays.toString(selectedIndex)+" Selected Indexes");
//        System.out.println("\t\t"+Arrays.toString(equalCounter)+" Equal Counter");  
//        System.out.println("\t\t"+small.getGenesString()+" small: ["+small.getId()+"]");
//        System.out.println("\t\t"+big.getGenesString()+" big: ["+big.getId()+"] ");
//        System.out.println("\t\t"+result.getGenesString()+" result: ["+result.getId()+"] ");
        return result;
    }
    


    //USER INPUT METHODS
    public void setSelectionMethod(int s){selectionMethod=s;}
    public void setGeneticOperator(int s){geneticOperator=s;}
    public void setMutationEnabled(boolean b){useMutation=b;}
    public void setEliteEnabled(boolean b){useElite=b;}
    public void setmutationRate(int s){mutationRate=s;}
    public void setOperatorRate(int s){operatorRate=s;}
    public void settournamentSize(int s){tournamentSize=s;}
    public void setPopSize(int s){popSize=s;}
    
    public Variable[] getVariables(){return vars;}   
    public Individual getEliteIndividual(){return eliteIndividual;}
    public int getPopSize(){return popSize;}
    public int getGeneLength(){return geneLength;}
    
}
