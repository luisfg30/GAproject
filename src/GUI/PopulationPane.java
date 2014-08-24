/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import Main.Function;
import Main.GA;
import Main.Individual;
import Main.Population;
import Main.Stats;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author luis
 */
public class PopulationPane extends JPanel implements PropertyChangeListener{
    
    //GUI ATRIBUTES
    private JScrollPane scrollOutput;
    private JTextArea outputArea;
    private JCheckBox elite,print,showFit,showOpt;
    private JPanel options;
    private JButton start,stop;
    private boolean continuE =false,printInfo=true; 
    private JLabel genNumber,popSize,popOptions,eliteInd,convergence;
    Font titleFont = new Font("Serif", Font.BOLD, 18);
    private JFormattedTextField genNumberField,popSizeField,convergenceValue;
    private OptionsL optionsListener;
    private DecimalFormat df = new DecimalFormat("#000.0000000");
    private NumberFormat amountFormat;
    private  GeneticOperatorsPane goPane;
    private Graph g;
    private double eliteValues[];
    
    
    private GA myGA= GA.getInstance();
    private Stats myStats = Stats.getInstance();
    private Population initialPop;
    private Function myEvalFunction = Function.getInstance();
    private ResultsPane myResults;
    

    private int defaultPop=10,defaultGen=100,defaultConvergence=90;
    private long startTime,estimatedTime;
    
    
    
    public PopulationPane(ResultsPane r)
    {
        myResults =r;
        titleFont = new Font("Serif", Font.BOLD, 18);
        startComponents();
        
        this.setLayout(new BorderLayout());
        this.add(scrollOutput, BorderLayout.PAGE_END);
        this.add(options,BorderLayout.CENTER);
        
    }
    
    private void startComponents()
    {
        outputArea= new JTextArea();
        outputArea.setBackground(Color.LIGHT_GRAY);
        outputArea.setEditable(false);
        outputArea.setRows(13);
        
        scrollOutput = new JScrollPane(outputArea);
        
       options = new JPanel();
       options.setBackground(Color.GRAY);
       options.setLayout(null);
       Rectangle optionsBounds= options.getBounds();
       
       goPane= new GeneticOperatorsPane();
       goPane.setBounds(optionsBounds.x, optionsBounds.y, 300, 170);
       options.add(goPane);
        
       g= new Graph(700,350);
       g.setLocation(300, optionsBounds.y+30);
       this.add(g);
       
       showFit = new JCheckBox("Show Fitness PLot");
       showFit.setBackground(options.getBackground());
       showFit.setSelected(true);
       showFit.setBounds(300, optionsBounds.y+385, 200, 20);
                     showFit.addItemListener(new ItemListener() 
        {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange()==ItemEvent.DESELECTED)
                {
                    g.setShowFit(false);
                }
                else
                {
                    g.setShowFit(true);
                }
            }
        });
       this.add(showFit);
       
       showOpt= new JCheckBox("Show Optimality Plot");
       showOpt.setBackground(options.getBackground());
       showOpt.setSelected(true);
       showOpt.setBounds(550, optionsBounds.y+385, 200, 20);
              showOpt.addItemListener(new ItemListener() 
        {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange()==ItemEvent.DESELECTED)
                {
                    g.setShowOpt(false);
                }
                else
                {
                    g.setShowOpt(true);
                }
            }
        });
       this.add(showOpt);
       
       eliteInd= new JLabel("Elite Individual:");
       eliteInd.setBounds(300,optionsBounds.y, 700, 30);
       eliteInd.setFont(titleFont);
       this.add(eliteInd);
       
       //BUTTONS
       optionsListener= new OptionsL();
       start = new JButton("START"); 
       start.setBounds(optionsBounds.x+10,optionsBounds.y+290,200,40);
       start.addActionListener(optionsListener);
       options.add(start);
       
       Rectangle startBounds = start.getBounds();
        
       stop= new JButton("STOP-Create New Population");
       stop.setBounds(startBounds.x,startBounds.y+40, 200, 40);
       stop.addActionListener(optionsListener);
       stop.setEnabled(false);
       options.add(stop);
       
       print= new JCheckBox("Print individual informations");
       print.setBounds(startBounds.x, startBounds.y+90, 200, 40);
       print.setBackground(options.getBackground());
       print.setSelected(true);
       print.addItemListener(new ItemListener() 
        {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange()==ItemEvent.DESELECTED)
                {
                    printInfo=false;
                }
                else
                {
                    printInfo=true;
                }
            }
        });
       options.add(print);
       
       elite= new JCheckBox("Use Elite Individual");
       elite.setBounds(startBounds.x, startBounds.y-100, 140, 30);
       elite.setBackground(options.getBackground());
       elite.setSelected(true);
       elite.addItemListener(new ItemListener() 
        {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange()==ItemEvent.DESELECTED)
                {
                    myGA.setEliteEnabled(false);
                }
                else
                {
                    myGA.setEliteEnabled(true);
                }
            }
        });
       options.add(elite);
      
       
        popOptions=new JLabel ("Population Options");
       popOptions.setFont(titleFont);
       popOptions.setBounds(startBounds.x,startBounds.y-130, startBounds.width, 30);
       options.add(popOptions);
       
       genNumber = new JLabel("Generations to run:");
       genNumber.setBounds(startBounds.x, startBounds.y-50, startBounds.width-50, startBounds.height/2);
       options.add(genNumber);
       
       genNumberField =new JFormattedTextField(amountFormat);
       genNumberField.setValue(new Integer(defaultGen));
       genNumberField.addPropertyChangeListener(this);
       genNumberField.setBounds(startBounds.x+150, startBounds.y-50, 50, startBounds.height/2);
       options.add(genNumberField);
       
       popSize= new JLabel("Population Size:");
       popSize.setBounds(startBounds.x, startBounds.y-70, startBounds.width-50, startBounds.height/2);
       options.add(popSize);
       
       popSizeField = new JFormattedTextField(amountFormat);
       popSizeField.setValue(defaultPop);
       popSizeField.addPropertyChangeListener(this);
       popSizeField.setBounds(startBounds.x+150, startBounds.y-70, 50, startBounds.height/2);
       options.add(popSizeField);
       
       convergence= new JLabel("Convergence Value:");
       convergence.setBounds(startBounds.x, startBounds.y-35, startBounds.width-50, 30);
       options.add(convergence);
       
       convergenceValue= new JFormattedTextField(amountFormat);
       convergenceValue.setValue(defaultConvergence);
       convergenceValue.addPropertyChangeListener(this);
       convergenceValue.setBounds(startBounds.x+150, startBounds.y-30, 50, startBounds.height/2);
       options.add(convergenceValue);
    }
    


    @Override
    public void propertyChange(PropertyChangeEvent pce) 
    {
        Object source = pce.getSource();
       if(source==genNumberField)
       {
           defaultGen = ((Number)genNumberField.getValue()).intValue();
           if(defaultGen<1 )
           {
               defaultGen=100;
               genNumberField.setValue(defaultGen);
           }     
       }
       else if(source==popSizeField)
       {
           defaultPop = ((Number)popSizeField.getValue()).intValue();
           if(defaultPop<1 )
           {
               defaultPop=10;
               popSizeField.setValue(defaultPop);
           }
           myGA.setPopSize(defaultPop);    
           if(continuE==false)
           {
                 initialPop= new Population(defaultPop);
                 initialPop.startIndividuals(); 
                 myGA.evaluate(initialPop);
                 myGA.firstRun=false;
                 myGA.eliteIndividual= new Individual(initialPop.getFittest());
                 eliteValues= myGA.decodeIndividualGenes(myGA.eliteIndividual);
//                  eliteInd.setText("Elite Individual:  ["+myGA.eliteIndividual.getId()+"] X("+df.format(eliteValues[0])+
//                     ") Y(" +df.format(eliteValues[1])+")Fitness = "+df.format(myGA.eliteIndividual.getFitness()));  
           }

       }
       else if(source==convergenceValue)
       {
           defaultConvergence = ((Number)convergenceValue.getValue()).intValue();
           if(defaultConvergence<1 )
           {
               defaultGen=90;
               convergenceValue.setValue(defaultConvergence);
           }     
           myStats.setConvergenceValue(defaultConvergence);
       }
    }
    
    public void addOutputText(String s){outputArea.append(s);}
    
    public void recordExecutionInfo()
    {
        myStats.getArraySolutions().get(myStats.getExecutionCounter()-1).popSize=myGA.getPopSize();
        myStats.getArraySolutions().get(myStats.getExecutionCounter()-1).usingElite=myGA.getEliteEnabled();
        myStats.getArraySolutions().get(myStats.getExecutionCounter()-1).usingMutation=myGA.useMutation;
        myStats.getArraySolutions().get(myStats.getExecutionCounter()-1).mutationRate=myGA.mutationRate;
        myStats.getArraySolutions().get(myStats.getExecutionCounter()-1).operatorRate=myGA.operatorRate;
        myStats.getArraySolutions().get(myStats.getExecutionCounter()-1).setOperator(myGA.geneticOperator);
        myStats.getArraySolutions().get(myStats.getExecutionCounter()-1).geneLenght=myGA.geneLength;
        myStats.getArraySolutions().get(myStats.getExecutionCounter()-1).xCases=myGA.x.precision;
        myStats.getArraySolutions().get(myStats.getExecutionCounter()-1).yCases=myGA.y.precision;
    }
    
    public void updateStats()
    {
     if(myGA.getEliteEnabled()==true)
      {
         myStats.updateFitnessValues(initialPop.getAvgFitness(),myGA.eliteIndividual.getFitness());
      }
      else
      {
        myStats.updateFitnessValues(initialPop.getAvgFitness(),initialPop.getFittest().getFitness()); 
      }    
    }
    
   private class OptionsL implements ActionListener
        {
             @Override
             public void actionPerformed(ActionEvent ae) 
             {
                  JButton clickedButton=(JButton)(ae.getSource());
                 if(clickedButton==start)
                 {
                     stop.setEnabled(true);
                     
                    int loop = Integer.parseInt(genNumberField.getText());   
                    start.setEnabled(false);
                    for (int i=0;i<loop;i++)
                    {
                        if(printInfo==true)
                        {
                                outputArea.append("\nGENERATION: "+myStats.getGenerations()+
                                  " --Avg Fitness: "+initialPop.getAvgFitness()+"--Best Fitness: "+ 
                                        myGA.eliteIndividual.getFitness()+"\n");
                                for(int j=0;j<myGA.getPopSize();j++)
                                {
                                   outputArea.append("["+initialPop.getIndividual(j).getId()+"] -- "+
                                   initialPop.getIndividual(j).getGenesString()+" -- Fitness: "+
                                   df.format(initialPop.getIndividual(j).getFitness())+"\n");    
                                }
                        }
                        startTime = System.nanoTime();    
                            initialPop = myGA.evolve(initialPop);  
                        estimatedTime = System.nanoTime() - startTime;
                        myStats.addTime(estimatedTime);
                        
                         eliteValues= myGA.decodeIndividualGenes(myGA.eliteIndividual);
                         eliteInd.setText("Elite Individual:  ["+myGA.eliteIndividual.getId()+"] X("+df.format(eliteValues[0])+
                         ") Y(" +df.format(eliteValues[1])+")Fitness = "+df.format(myGA.eliteIndividual.getFitness())); 
                         updateStats();   
                     }
                    continuE=true;
                         start.setText("CONTINUE");
                         start.setEnabled(true);
                         popSizeField.setEnabled(false);
//                    System.out.println("ARRAY"+Arrays.toString(myStats.getArrayFitness()));
                         g.addData(myStats.getArrayFitness());
                         g.repaint();
                 }
                 else if (clickedButton==stop)
                 {
                     stop.setEnabled(false);
                     
                     //record the values of the best individual
                           double realValues[]=new double[myGA.maxVars],zValue;
                           realValues=myGA.decodeIndividualGenes(myGA.eliteIndividual);
                           zValue=myEvalFunction.calcValue(realValues[0],realValues[1]);
                           myStats.recordResults(realValues[0],realValues[1], zValue,myGA.eliteIndividual.getFitness());
                           recordExecutionInfo();
                           myResults.updateData();
                           myResults.addExecutionDetails(myStats.getExecutionCounter()-1);
                           myStats.clearData();
                           myGA.eliteIndividual= new Individual(myGA.getGeneLength());
                           
                     
                     outputArea.setText("");
                     continuE=false;
                     start.setText("START");
                     popSizeField.setEnabled(true);
                 }
             }

        }
          
         
}
