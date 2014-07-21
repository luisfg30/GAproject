package Main;




public class Individual {
    private byte[] genes; 
    private double fitness;
    private String id;

    /**
     * Create an Individual with a random gene string, and fitness=0
     * @param geneLength is the size of the random gene string
     */
    public Individual(int geneLength)
    {
        genes=new byte[geneLength];
         for (int i = 0; i < geneLength; i++) 
         {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
         fitness=0;
    }
    /**
     * Create a new instance of Individual and copy the atributes of a given object ind.
     *
     * @param ind the object that will be copied, it must have been initialized before
     */
    public Individual(Individual ind)
    {
        genes=ind.genes;
        fitness=ind.fitness;
        id=ind.id;  
    }
       
    public byte getGene(int index) 
    {
        return genes[index];
    }

    public void setGene(int index, byte value) 
    {
        genes[index] = value;
        //fitness = 0;
    }
    
    public void setGeneString(byte g[])
    {
        genes=g;
    }
    
    public void setFitness(double f)
    {
        fitness=f;
    }
    
        public double getFitness() {
//        if (fitness == 0) {
//            fitness = FitnessCalc.getFitness(this);
//        }
        return fitness;
    }
  
    public byte[] getGenes() 
    {
        return genes;
    }
    
    public String getGenesString()
    {
        String s= new String();
        for(int i=0;i<genes.length;i++)
        {
            s+=genes[i];
        }
        return s;
    }
    
    public void setId(String s)
    {
        id=s;
    }
    
    public String getId(){return id;}

//    public static Individual  copy(Individual ind)
//    {
//        
//        
//    }
    
}
//    private String randomString(String alphabet,int len)
//    {
//         Random rnd = new Random();
//
//           StringBuilder sb = new StringBuilder( len );
//           for( int i = 0; i < len; i++ ) 
//              sb.append( alphabet.charAt( rnd.nextInt(alphabet.length())));
//           String s= sb.toString();
//           return s;
//    }


