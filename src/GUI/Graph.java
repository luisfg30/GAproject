/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

/**
 *
 * @author luis
 */
import Main.Stats;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import javax.swing.JPanel;

 public class Graph extends JPanel
{
     private Stats myStats= Stats.getInstance();
     
     private final int margin=40;
     private int width,height;
     private double dataAvg[],dataBest[],dataOpt[],dataEliteOpt[];
     private double scaleX,scaleY;
     private Point2D origin,xEnd,yEnd;
     Font numbers = new Font("Arial", Font.PLAIN, 10);
     Font labels = new Font("Arial", Font.BOLD, 12);
     private boolean showOptimality=true,showFitness=true;
    
     public Graph(int w, int h)
     {
         dataAvg= new double[0];
         dataBest= new double[0];
         dataOpt= new double[0];
         dataEliteOpt=new double[0];
         width=w;
         height=h;
         this.setSize(width, height);
         this.setBackground(Color.black);

     }
     
     public void setShowOpt(boolean b)
     {
         this.showOptimality=b;
         this.repaint();
     }
     
     public void setShowFit(boolean b)
     {
         this.showFitness=b;
         this.repaint();
     }
             
     public void addData(double data[])
     {
              dataAvg=myStats.getArrayFitness();
              dataBest=myStats.getArrayBestFitness();
              dataOpt=myStats.getArrayAvgOptimality();
              dataEliteOpt=myStats.getArrayEliteOptimality();
     }
     
    @Override
    public void paint(Graphics g)
    {
      super.paint(g);
      
       origin= new Point2D.Double(margin-15,height-margin);
       yEnd= new Point2D.Double(margin-15,margin-20);
       xEnd= new Point2D.Double(width-margin-55,height-margin);
      
      Line2D xAxis= new Line2D.Double(origin,xEnd);
      Line2D yAxis= new Line2D.Double(origin,yEnd);
      
      Graphics2D g2 =  (Graphics2D)g;
      g2.setPaint(Color.white);
      g2.setFont(labels);
      g2.drawString("Generations", (int)width/2-50, (int)xEnd.getY()+30);
      g2.setColor(Color.RED);
      g2.drawString("Avg Fitness", (int)xEnd.getX()+5, (int)yEnd.getY()+30);
      g2.setColor(Color.green);
      g2.drawString("Best Fitness", (int)xEnd.getX()+5, (int)yEnd.getY()+70);
      g2.setColor(Color.blue);
      g2.drawString("Avg Optimality", (int)xEnd.getX()+5, (int)yEnd.getY()+110);
      g2.setColor(Color.yellow);
      g2.drawString("Best Optimality", (int)xEnd.getX()+5, (int)yEnd.getY()+150);
      g2.setColor(Color.GRAY);
      g2.draw(xAxis);
      g2.draw(yAxis); 
      this.drawGrid(g2, 2, 0,dataAvg.length);
      this.drawGrid(g2, 1, 1,100);
      if(showFitness==true)
      {
         this.plotData(g2, Color.red, dataAvg);      
         this.plotData(g2, Color.green, dataBest);
      }
      if(showOptimality==true)
      {
        this.plotData(g2, Color.blue, dataOpt);
        this.plotData(g2, Color.yellow, dataEliteOpt);
      }

    }  
    
    public void plotData(Graphics2D g2,Color c, double dataY[])
    {
         if(dataY.length>0)
         {
            scaleY=(origin.getY()-yEnd.getY())/100; //in coordinates (pixels)
            scaleX=(xEnd.getX()-origin.getX())/dataY.length;
//            System.out.println("origin x: "+origin.getX()+" y: "+origin.getY());
//            System.out.println("xEnd x: "+xEnd.getX()+" y: "+xEnd.getY());
//            System.out.println("yEnd x: "+yEnd.getX()+" y: "+yEnd.getY());
//            System.out.println("scaleX: "+scaleX+" scaleY: "+scaleY);
              Point2D p1= new Point2D.Double(origin.getX(),origin.getY());
              Point2D p2 = new Point2D.Double(p1.getX()+scaleX,origin.getY());
              Line2D line= new Line2D.Double(p1,p2);
              g2.setColor(c);
              g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

              for(int i =0;i<dataY.length;i=i+2)
              {
                  p2.setLocation(p1.getX()+scaleX*2, origin.getY()-scaleY*dataY[i]);
                  line.setLine(p1, p2);
                  g2.draw(line);
//                  System.out.println("i: "+i+" dataY: "+dataY[i]+"\np1 x: "+p1.getX()+" y: "+p1.getY());
//                  System.out.println("p2 x: "+p2.getX()+" y: "+p2.getY());
                  p1.setLocation(p2.getX(), p2.getY());
              }
         } 
    }
    
    public void drawGrid(Graphics2D g2,int gridDivision,int orientation,int maxValue)
    {
        g2.setFont(numbers);
        g2.setColor(Color.GRAY);
        g2.drawString("0", (int)origin.getX()-10, (int)origin.getY()+10);
        int number =(int) Math.pow(2, gridDivision);
        double space;
        int label;
        Point2D pa,pb;
        if(orientation==0)//vertical
        {
            space=((xEnd.getX()-origin.getX())/(2*number));
            label=maxValue/(2*number);
            pa= new Point2D.Double(origin.getX()+space,origin.getY());
            pb= new Point2D.Double(yEnd.getX()+space,yEnd.getY());
            for(int i=0;i<number*2;i++)
            {
                g2.draw(new Line2D.Double(pa,pb));
                g2.drawString(Integer.toString(label*(i+1)), (int)pa.getX(),(int) pa.getY()+15);
                pa.setLocation(pa.getX()+space, pa.getY());
                pb.setLocation(pb.getX()+space, pb.getY());          
            }
        }
        else//horizontal
        {
            space=(origin.getY()-yEnd.getY())/(2*number);
            label=maxValue/(2*number);
            pa= new Point2D.Double(origin.getX(),origin.getY()-space);
            pb= new Point2D.Double(xEnd.getX(),xEnd.getY()-space);
            for(int i=0;i<number*2;i++)
            {
                g2.draw(new Line2D.Double(pa,pb));
                g2.drawString(Integer.toString(label*(i+1)), (int)pa.getX()-20,(int) pa.getY());
                pa.setLocation(pa.getX(), pa.getY()-space);
                pb.setLocation(pb.getX(), pb.getY()-space); 
            }
        }
    }

}
