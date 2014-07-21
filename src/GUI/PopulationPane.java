/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import Main.GA;
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
    private JCheckBox elite;
    private JPanel options;
    private JButton start,stop;
    private boolean continuE =false;
    private JLabel genNumber,popSize,popOptions;
    Font titleFont = new Font("Serif", Font.BOLD, 18);
    private JFormattedTextField genNumberField,popSizeField;
    private OptionsL optionsListener;
    private DecimalFormat df = new DecimalFormat("#000.0000");
    private NumberFormat amountFormat;
    private  GeneticOperatorsPane goPane;
    
    
    private GA myGA= GA.getInstance();
    private Stats myStats = Stats.getInstance();
    private Population initialPop;
    
    

    private int defaultPop=10,defaultGen=100;
    private int totalGenerations=0;
    
    
    public PopulationPane()
    {
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
       
       //BUTTONS
       optionsListener= new OptionsL();
       start = new JButton("START"); 
       start.setBounds(optionsBounds.x+10,optionsBounds.y+230,200,40);
       start.addActionListener(optionsListener);
       options.add(start);
       
       Rectangle startBounds = start.getBounds();
        
       stop= new JButton("STOP-Create New Population");
       stop.setBounds(startBounds.x,startBounds.y+40, 200, 40);
       stop.addActionListener(optionsListener);
       options.add(stop);
       
       elite= new JCheckBox("Elite Individual");
       elite.setBounds(startBounds.x+200, startBounds.y, 140, 30);
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
       popOptions.setBounds(startBounds.x,startBounds.y-70, startBounds.width, 30);
       options.add(popOptions);
       
       genNumber = new JLabel("Generations to run:");
       genNumber.setBounds(startBounds.x, startBounds.y-20, startBounds.width-50, startBounds.height/2);
       options.add(genNumber);
       
       genNumberField =new JFormattedTextField(amountFormat);
       genNumberField.setValue(new Integer(defaultGen));
       genNumberField.addPropertyChangeListener(this);
       genNumberField.setBounds(startBounds.x+150, startBounds.y-20, 50, startBounds.height/2);
       options.add(genNumberField);
       
       popSize= new JLabel("Population Size:");
       popSize.setBounds(startBounds.x, startBounds.y-40, startBounds.width-50, startBounds.height/2);
       options.add(popSize);
       
       popSizeField = new JFormattedTextField(amountFormat);
       popSizeField.setValue(new Integer(defaultPop));
       popSizeField.addPropertyChangeListener(this);
       popSizeField.setBounds(startBounds.x+150, startBounds.y-40, 50, startBounds.height/2);
       options.add(popSizeField);
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
           initialPop= new Population(defaultPop);
           initialPop.startIndividuals();
       }
    }
    
    public void addOutputText(String s){outputArea.append(s);}
    
          private class OptionsL implements ActionListener
        {
             @Override
             public void actionPerformed(ActionEvent ae) 
             {
                  JButton clickedButton=(JButton)(ae.getSource());
                 if(clickedButton==start)
                 {                   
                    int loop = Integer.parseInt(genNumberField.getText());      
                    outputArea.setText("");
                    start.setEnabled(false);
                    if(continuE==false)//first time the program runs
                    {
                        initialPop=new Population(myGA.getPopSize());
                        initialPop.startIndividuals();
                        myGA.evaluate(initialPop);
                        myGA.eliteIndividual=initialPop.getFittest();
                         //PRINT INFORMATION
                         outputArea.append("\nINITIAL POPULATION: "+totalGenerations+"\n");
                         for(int j=0;j<myGA.getPopSize();j++)
                         {
                            outputArea.append("["+initialPop.getIndividual(j).getId()+"] -- "+
                            initialPop.getIndividual(j).getGenesString()+" -- Fitness: "+
                            df.format(initialPop.getIndividual(j).getFitness())+"\n");
                         }
                        
                    }
                    for (int i=0;i<loop;i++)
                    {
                        initialPop = myGA.evolve(initialPop);  
                        
                        //PRINT INFORMATION
                         outputArea.append("\nGENERATION: "+totalGenerations+"\n");
                         for(int j=0;j<myGA.getPopSize();j++)
                         {
                            outputArea.append("["+initialPop.getIndividual(j).getId()+"] -- "+
                            initialPop.getIndividual(j).getGenesString()+" -- Fitness: "+
                            df.format(initialPop.getIndividual(j).getFitness())+"\n");
                         }
   
                        totalGenerations++;
                     }
                         start.setText("CONTINUE");
                         start.setEnabled(true);
                         popSizeField.setEnabled(false);
                    continuE=true;
                 }
                 else if (clickedButton==stop)
                 {
                     outputArea.setText("");
                     continuE=false;
                     totalGenerations=0;
                     start.setText("START");
                     popSizeField.setEnabled(true);
                 }
             }

        }
          
         
}
