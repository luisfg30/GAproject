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
public class Variable 
{
    float min,max; //ranges of the domain
    public int precision;
    private int bitSize; // minimum size in bits for binary encoding
    
    public Variable(float r1, float r2, int p)
    {
        setParameters(r1,r2,p);
    }
    
    private void calcVarLength()
    {
        int aux=(int) ((max-min)*(Math.pow(10, precision))) ;
        bitSize=(int) (Math.log10(aux+1)/Math.log10(2) + 1);
    }
    
        public float calcRealValue(String s)
    {
        float value=(float) (min+(Integer.parseInt(s,2))*((max-min)/((Math.pow(2, bitSize))-1)));
                return value;
    }
        
    public void setParameters(float r1, float r2, int p)
    {
        min=r1;
        max=r2;
        precision=p;
        calcVarLength();
    }
    
    public int getBitSize(){return bitSize;}
    //public float getMin(){return min;}
    //public float getMax(){return max;}

    
}
